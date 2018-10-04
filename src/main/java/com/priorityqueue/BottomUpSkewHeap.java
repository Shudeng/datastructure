package com.priorityqueue;

import com.common.BottomUpSkewHeapPrinter;

import java.util.HashMap;

/**
 * Created by Wushudeng on 2018/9/16.
 */
public class BottomUpSkewHeap<T extends Comparable<? super T>> {
    public static class BottomUpSkewHeapNode<T extends Comparable<? super T>> {
        T value;
        /**
         * up of one node means its parent or
         * if the node is root, its up is the lowest node in the right most path
         * down of one node is the right most path of the node's left child or
         * if the node's left child is null, its down is itself
         */
        BottomUpSkewHeapNode<T> left, right, up, down;

        public BottomUpSkewHeapNode(T value) {
            this.value = value;
            this.left = null;
            this.right = null;
            this.up = this;
            this.down = this;
        }

        public BottomUpSkewHeapNode() {
        }

        public String getInfo() {
            return this.value.toString();
        }

        public T getValue() {
            return value;
        }

        public BottomUpSkewHeapNode<T> getLeft() {
            return left;
        }

        public BottomUpSkewHeapNode<T> getRight() {
            return right;
        }
    }

    private BottomUpSkewHeapNode<T> root;




    public void insert(T data) {
        BottomUpSkewHeapNode<T> node = new BottomUpSkewHeapNode<>(data);
        if (root == null) {
            root = node;
            return;
        }

        root = meld(root, node);
    }

    public BottomUpSkewHeapNode<T> meld(BottomUpSkewHeapNode<T> h1, BottomUpSkewHeapNode<T> h2) {
        if (h1 == null)
            return h2;
        if (h2 == null)
            return h1;
        // let h1 be the node whose lowest node is larger
        BottomUpSkewHeapNode<T> tmp = new BottomUpSkewHeapNode<>();
        if (h1.up.value.compareTo(h2.up.value) < 0) {
            tmp = h1;
            h1 = h2;
            h2 = tmp;
        }
        BottomUpSkewHeapNode<T> h3 = h1.up;
        h1.up = h3.up;
        h3.up = h3;

        while (h1 != h3) {
            // let h1 be the node whose lowest node is larger
            if (h1.up.value.compareTo(h2.up.value) < 0) {
                tmp = h1;
                h1 = h2;
                h2 = tmp;
            }
            BottomUpSkewHeapNode<T> bottom_node = h1.up;
            h1.up = bottom_node.up;
            //Add bottom_noe to the top of h3. By the way, bottom_node has no right child at this moment.
            bottom_node.right = bottom_node.left;
            bottom_node.left = h3;
            bottom_node.up = bottom_node.down;
            bottom_node.down = h3.up;
            h3.up = bottom_node;
            h3 = bottom_node;
        }
        // add h3 to the bottom right of h2
        h2.up.right = h3;
        tmp = h3.up;
        h3.up = h2.up;
        h2.up = tmp;
        return h2;
    }

    public T delete_min() {
        if (root == null)
            return null;
        T data = root.value;
        BottomUpSkewHeapNode<T> left = root.left;
        BottomUpSkewHeapNode<T> right = root.right;
        left.up=left;
        right.up = right;
        root = meld(left, right);
        return data;
    }


    public static void main(String[] args) {
        BottomUpSkewHeap<Integer> heap1 = new BottomUpSkewHeap<>();
        Integer[] list = {1, 13, 3, 15, 5, 17, 19, 7, 9};
        for (Integer item:list) {
            heap1.insert(item);
        }

        BottomUpSkewHeap<Integer> heap2 = new BottomUpSkewHeap<>();
        Integer[] list1 = {2, 14, 4,16, 6,8, 10, 20};
        for (Integer item:list1) {
            heap2.insert(item);
        }

        BottomUpSkewHeapNode<Integer> node = heap1.meld(heap1.root, heap2.root);


        BottomUpSkewHeapPrinter printer = new BottomUpSkewHeapPrinter(node);
    }






}
