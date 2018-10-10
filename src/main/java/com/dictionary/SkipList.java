package com.dictionary;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

/**
 * Created by Wushudeng on 2018/10/10.
 */
public class SkipList<K extends Comparable<? super  K>, V> implements Dictionary<K, V>{

    private static class QuadListNode<K extends Comparable<? super  K>, V> {
        QuadListNode<K, V> pred = null, succ=null, above=null, below=null;
        V value=null;
        K key = null;

        public QuadListNode() {
            // construct header and tailer.
        }

        public QuadListNode(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private static class QuadList<K extends Comparable<? super K>, V> {
        QuadListNode<K, V> header = new QuadListNode<>(), tailer = new QuadListNode<>();
        int size = 0, level=0;
        QuadList<K, V> above = null, below = null;

        public QuadList() {
            header.succ = tailer;
            header.pred = null;
            tailer.pred = header;
            tailer.succ = null;
            header.below = header.above = tailer.above = tailer.below = null;
        }

        private QuadListNode<K, V> first() {
            return header.succ;
        }

        private QuadListNode<K, V> last() {
            return tailer.pred;
        }

        public QuadListNode<K, V> insert_as_succ_above(K key, V value, QuadListNode<K, V> pred, QuadListNode<K, V> below) {
            QuadListNode<K, V> new_node = new QuadListNode<>(key, value);
            QuadListNode<K, V> next = pred.succ;

            new_node.pred = pred;
            new_node.succ = next;

            new_node.below = below;
            new_node.above = below != null ? below.above : null;

            next.pred = new_node;
            pred.succ = new_node;


            if (below != null) below.above = new_node;
            size++;
            return new_node;
        }

        public boolean empty(){return size==0;}

        public int size() {return size;}

        public QuadList<K, V> above_level() {
            return above;
        }

        public QuadList<K, V> below_level() {
            return below;
        }
    }

    private int size;
    private QuadList<K, V> highest = null, lowest=null;

    /**
     * the highest level
     * @return
     */
    private QuadList<K, V> highest_level() {
        return highest;
    }

    /**
     * the lowest level
     * @return
     */
    private QuadList<K, V> lowest_level() {
        return lowest;
    }

    /**
     * add a new quadlist above the highest level.
     */
    private void add_quadlist() {
        QuadList<K, V> quadList = new QuadList<>();
        if (highest == null) {
            quadList.level = 0;
            highest = quadList;
            lowest = quadList;
            return;
        }

        quadList.level = highest.level+1;
        highest.above = quadList;
        quadList.below = highest;
        highest = quadList;
    }

    private boolean remove_quadlist() {
        if (highest == null)
            return false;

        if (highest == lowest) {
            highest = lowest = null;
            return true;
        }
        highest = highest.below;
        highest.above = null;
        return true;
    }

    /**
     * @param key
     * @return
     * 1. node is the QuadListNode whose key is the search key if the key exists.
     * 2. otherwise, node is the QuadListNode whose key is the last node whose key is less than the search key.
     * 3. if no QuadListNode belongs to above two cases, node is the header of the lowest quadlist.
     */
    private QuadListNode<K, V> skipSearch(K key) {
        if (is_empty()) return highest_level().header;

        QuadListNode<K,V> node = new QuadListNode<>();
        QuadList<K, V> quadList = highest_level();
        node = quadList.first();
        for (;;) {
            for (;node.succ != null && node.key.compareTo(key)<=0; node = node.succ);
            node = node.pred;
            if (node.pred!= null && node.key.compareTo(key) == 0) return node;

            if ((quadList = quadList.below_level()) == null)
                return node;

            // if node is the header of quadlist, let node be the first ndoe of the next level.
            // otherwise, let node be the node below it.
            node = node.pred == null ? quadList.first() : node.below;
        }
    }


    @Override
    public int size() {
        return 0;
    }

    @Override
    public V get(K key) {
        if (is_empty()) return null;
        QuadListNode<K, V> node = skipSearch(key);

        return node.pred != null && node.key.compareTo(key)==0 ? node.value: null;
    }

    @Override
    public boolean put(K key, V value) {
        if (is_empty()) add_quadlist();
        QuadList<K, V> qlist = highest_level();
        QuadListNode<K, V> node = skipSearch(key);
        if (node.key != null && node.key.compareTo(key) == 0) {
            //change the value of key if key already exists.
            for (; node.below != null; node.value = value, node = node.below);
            return true;
        }

        qlist = lowest_level();
        QuadListNode<K, V> new_node = qlist.insert_as_succ_above(key, value, node, null);
        while ((new Random()).nextInt(2) == 0) {
            //find the quadNode before node.succ whose above is not null.
            node = new_node;
            for (; node.pred != null && node.above == null; node=node.pred);
            //if node become the header of quadlist
            if (node.pred == null) {
                if (qlist == highest_level())
                    add_quadlist();
            }

            qlist = qlist.above_level();
            new_node = qlist.insert_as_succ_above(key, value, node.above == null ? qlist.header : node.above, new_node);
        }
        size++;
        return false;
    }

    @Override
    public boolean is_empty() {
        return size == 0;
    }

    @Override
    public boolean contains(K key) {
        return get(key) != null;
    }

    @Override
    public boolean remove(K key) {
        QuadListNode<K,V> node = skipSearch(key);
        if (node.key.compareTo(key) != 0)
            return false;
        for (; node != null ; node = node.below) {
            if (node.pred == null && node.succ == null)
                remove_quadlist();
            else {
                //remove node from its quadlist
                node.pred.succ = node.succ;
                node.succ.pred = node.pred;
            }
        }
        size--;
        return true;
    }

    public static void main(String[] args) {
        SkipList<Integer, Integer> skipList = new SkipList<>();
        for (int i=0; i<10000; i++) {
            skipList.put(i, i);
        }

//        SkipList<Integer, String> skipList = new SkipList<>();
//        String[] strings = {"hello", "beijing", "nice", "to", "meet", "you"};
//        int i=0;

//        for (String s:strings) {
//            skipList.put(i++, s);
//        }
//
//        for (int j=0; j<i; j++) {
//            System.out.println(skipList.get(j));
//        }
//
//        skipList.remove(5);
//
//        for (int j=0; j<i; j++) {
//            System.out.println(skipList.get(j));
//        }
    }



}
