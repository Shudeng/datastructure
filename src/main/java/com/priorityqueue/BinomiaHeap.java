package com.priorityqueue;

import com.common.BinomialHeapPrinter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wushudeng on 2018/9/8.
 */
public class BinomiaHeap<T extends Comparable<? super T>> {
    public static class BinomialNode<T extends Comparable<? super T>> implements Comparable<BinomialNode<T>>{
        public T value;
        public BinomialNode<T> child, sibling;
        public int degree;

        public BinomialNode(T value) {
            this.value = value;
            this.child = null;
            this.sibling = null;
            this.degree = 0;
        }

        public int compareTo(BinomialNode<T> o) {
            return 0;
        }
    }

    public BinomialNode<T> head;

    public BinomiaHeap(BinomialNode<T> head) {
        this.head = head;
    }

    public BinomiaHeap() {
        this.head = null;
    }

    public void insert(T value) {
        if (head == null) {
            this.head = new BinomialNode<>(value);
            return;
        }
        BinomiaHeap<T> heap = new BinomiaHeap<>(new BinomialNode<>(value));
        this.union(heap);
    }

    private BinomialNode<T> meld(BinomiaHeap<T> heap) {
        BinomialNode<T> new_head=null, node=null, r1=this.head, r2=heap.head;
        while (r1!=null && r2!=null) {
            if (r1.degree < r2.degree) {
                if (new_head == null) {
                    new_head = r1;
                    node=new_head;
                } else {
                    node.sibling = r1;
                    node = node.sibling;
                }
                r1 = r1.sibling;
            } else {
                if (new_head == null) {
                    new_head = r2;
                    node = new_head;
                } else {
                    node.sibling = r2;
                    node = node.sibling;
                }
                r2 = r2.sibling;
            }
        }
        if (r1 == null) {
            node.sibling=r2;
        } else {
            node.sibling = r1;
        }

        return new_head;
    }
    private BinomialNode<T> link(BinomialNode<T> node, BinomialNode<T> next) {
        if (node.value.compareTo(next.value)<0) {
            next.sibling = node.child;
            node.child = next;
            node.degree++;
            return node;
        } else {
            node.sibling = next.child;
            next.child = node;
            next.degree++;
            return next;
        }
    }

    public void union(BinomiaHeap<T> heap) {

        if (heap.head == null) {
            return;
        }
        if (this.head == null) {
            this.head = heap.head;
            return;
        }
        BinomialNode<T> meld_head = meld(heap);
        BinomialNode<T> node = meld_head, pre=null, next=node.sibling, next_next;
        while (next != null) {
            next = node.sibling;
            if (node.degree == next.degree && (next.sibling == null || next.degree != next.sibling.degree)) {
                next_next = next.sibling;
                node = link(node, next);
                if (pre == null) {
                    meld_head = node;
                    next = next_next;
                    node.sibling = next;
                } else {
                    pre.sibling = node;
                    next = next_next;
                    node.sibling = next;
                }
            } else {
                pre = node;
                node = next;
                next = next.sibling;
            }
        }
        this.head = meld_head;
    }

    public T get_min() {
        if (head == null) {
            return null;
        }
        BinomialNode<T> node = head.sibling;
        T min = head.value;
        while (node != null) {
            if (min.compareTo(node.value) > 0) {
                min = node.value;
            }
            node = node.sibling;
        }
        return min;
    }

    public T extract_min() {
        if (head == null) {
            return null;
        }
        BinomialNode<T> node = head, min=head, min_pre=null, node_pre=null;
        while (node != null) {
            if (min.value.compareTo(node.value)>0) {
                min_pre = node_pre;
                min = node;
            }
            node_pre = node;
            node = node.sibling;
        }
        if (min_pre == null) {
            head = min.sibling;
        } else {
            min_pre.sibling = min.sibling;
        }
        BinomialNode<T> child_node = min.child;
        List<BinomialNode<T>> nodes = new ArrayList<>();
        while (child_node != null) {
            nodes.add(child_node);
            child_node = child_node.sibling;
        }

        BinomiaHeap<T> heap = new BinomiaHeap<>();
        heap.head = nodes.size()>=1 ? nodes.get(nodes.size()-1) : null;
        BinomialNode<T> node1 = heap.head;
        for (int i=nodes.size()-2; i>=0; i--) {
            nodes.get(i).sibling=null;
            node1.sibling = nodes.get(i);
            node1 = node1.sibling;
        }


        union(heap);
        return min.value;
    }

    public static void main(String[] args) {
        BinomiaHeap<Integer> heap = new BinomiaHeap<>();
        Integer[] list = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20,
                21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32,33
        };
        for (Integer item:list) {
            heap.insert(item);
        }
//        System.out.println(heap.extract_min());


        BinomialHeapPrinter printer = new BinomialHeapPrinter(heap);
        BinomialNode<Integer> root = heap.head;
        printer.setSize(1000, 1000);
        printer.paintTree(50, 50, root);
        printer.setVisible(true);

    }
}
