package com.priorityqueue;

import com.common.BNode;
import com.common.BTreePrinter;

/**
 * Created by Wushudeng on 2018/8/26.
 */
public class TopDownSkewHeap<T extends Comparable<? super T>> {
    private static class HeapNode<T extends Comparable<? super T>>{
        private T data;
        HeapNode<T> left, right;
        public HeapNode(T data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }

        public HeapNode(T data, HeapNode<T> left, HeapNode<T> right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }

        public HeapNode() {
        }
    }

    /**
     * This Integer should be changed to T.
     * However, it is necessary to implement comparable interface.
     */
    private HeapNode<T> root;

    /**
     * meld operation of top down heap
     * meld recursively
     * @param h1
     * @param h2
     */
    private HeapNode<T> rmeld(HeapNode<T> h1, HeapNode<T> h2) {
        HeapNode<T> temp = new HeapNode<>();
        // if h1 or h2 is null
        if (h1 == null) {
            return h2;
        }
        if (h2 == null) {
            return h1;
        }
        if (h1.data.compareTo(h2.data)>0) {
            temp = h1;
            h1 = h2;
            h2 = temp;
        }

        HeapNode<T> right = h1.right == null ? null : h1.right;
        h1.right = h1.left == null ? null : h1.left;
        h1.left = rmeld(right, h2);
        return h1;
    }

    /**
     * meld operation of top down heap
     * meld iteratively
     * @param h1
     * @param h2
     * @return
     */
    private HeapNode<Integer> imeld(HeapNode<Integer> h1, HeapNode<Integer> h2) {
        HeapNode<Integer> temp = new HeapNode<>();
        // if h1 or h2 is null
        if (h1 == null) {
            return h2;
        }
        if (h2 == null) {
            return h1;
        }

        if (h1.data > h2.data) {
            temp = h1;
            h1 = h2;
            h2 = temp;
        }

        HeapNode<Integer> x=h1, y=h1;
        do {
            h1 = y.right == null ? null : y.right;
            y.right = y.left;
            if (h1 == null) {
                y.left = h2;
                break;
            } else if (h2 == null) {
                y.left = h1;
                break;
            } else {
                if (h1.data > h2.data) {
                    temp = h1;
                    h1 = h2;
                    h2 = temp;
                }
                y.left = h1;
                y=h1;
            }
        }while (true);
        return x;
    }


    private void insert(T data) {
        if (root == null) {
            root = new HeapNode<T>(data);
            return;
        }
        root = rmeld(root, new HeapNode<T>(data));
//        root = imeld(root, new HeapNode<Integer>(data));
    }

    public T get_min() {return root.data;}

    public T extract_min() {
        if (root == null) {
            return null;
        }
        T data = root.data;
        HeapNode<T> left = root.left;
        HeapNode<T> right = root.right;
        root = rmeld(left, right);
        return data;
    }


    private void print(HeapNode<Integer> root) {
        BNode<Integer> node = node_change(root);
        BTreePrinter.printNode(node);
    }

    private BNode<Integer> node_change(HeapNode<Integer> root) {
        if (root == null) {
            return null;
        }
        BNode<Integer> node = new BNode<Integer>(root.data);
        node.setLeft(node_change(root.left));
        node.setRight(node_change(root.right));
        return node;
    }




    public static void main(String[] args) {
        TopDownSkewHeap heap = new TopDownSkewHeap();
        heap.root = new HeapNode<Integer>(2);
        Integer[] list = {5, 6, 8, 1, 3, 9, 10 ,20, 18, 5, 6, 8, 1, 3, 9, 10 ,20, 18};
//        heap.insert(5);
//        heap.insert(6);
        for (Integer item:list) {
            heap.insert(item);
        }

        BTreePrinter.printNode(heap.node_change(heap.root));

    }

}
