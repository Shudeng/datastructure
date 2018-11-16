package com.priorityqueue;

import com.common.BinaryTreePrinter;
import com.common.LeftistHeapPrinter;

/**
 * Created by Wushudeng on 2018/9/14.
 */
public class LeftistHeap<T extends Comparable<? super T>> {
    public static class LeftistNode<T extends Comparable<? super T>> implements Comparable<LeftistNode<T>> {
        T key;
        LeftistNode<T> left, right;
        // null path length
        int npl;

        public LeftistNode(T key) {
            this.key = key;
            left = null;
            right = null;
            npl = 0;
        }

        public LeftistNode(T key, LeftistNode<T> left, LeftistNode<T> right) {
            this.key = key;
            this.left = left;
            this.right = right;
            npl = right.npl+1;
        }

        public LeftistNode() {
            npl=0;
        }

        @Override
        public int compareTo(LeftistNode<T> o) {
            return (key.compareTo(o.key));
        }

        public String getInfo() {
            return this.key+","+this.npl;
        }

        public LeftistNode<T> getLeft() {
            return left;
        }

        public void setLeft(LeftistNode<T> left) {
            this.left = left;
        }

        public LeftistNode<T> getRight() {
            return right;
        }

        public void setRight(LeftistNode<T> right) {
            this.right = right;
        }
    }

    public LeftistNode<T> root;

    public LeftistHeap(LeftistNode<T> root) {
        this.root = root;
    }

    public LeftistHeap() {
        root = null;
    }

    public LeftistNode<T> meld(LeftistNode<T> heap1, LeftistNode<T> heap2) {
        if (heap1 == null)
            return heap2;
        if (heap2 == null)
            return heap1;
        LeftistNode<T> tmp;
        if (heap1.key.compareTo(heap2.key) > 0) {
            tmp = heap1;
            heap1 = heap2;
            heap2 = tmp;
        }

        heap1.right = meld(heap1.right, heap2);
        if (heap1.left == null || heap1.left.npl < heap1.right.npl) {
            tmp = heap1.left;
            heap1.left = heap1.right;
            heap1.right = tmp;
        }
        heap1.npl = heap1.right == null ? 1 : heap1.right.npl+1;

        return heap1;
    }

    public void insert(T x) {
        LeftistNode<T> node = new LeftistNode<>(x);
        if (root == null) {
            root = node;
            return;
        }

        root = meld(root, node);
    }

    public LeftistNode<T> delete_min() {
        if (root == null)
            return null;
        LeftistNode<T> left = root.left;
        LeftistNode<T> right = root.right;
        root = meld(left, right);
        return root;
    }

    public T get_min() {
        if (root == null) {
            return null;
        }
        return root.key;
    }




    public static void main(String[] args) {
        LeftistHeap<Integer> heap = new LeftistHeap<>();
        Integer[] list = {1,2,5,6,7,3,4,8};
        for (Integer item:list) {
            heap.insert(item);
        }
        heap.delete_min();
        BinaryTreePrinter printer = new LeftistHeapPrinter(heap.root);
        printer.setVisible(true);

        System.out.println(heap.get_min());
    }
}
