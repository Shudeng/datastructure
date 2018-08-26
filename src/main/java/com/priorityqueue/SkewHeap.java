package com.priorityqueue;

import com.common.BNode;
import com.common.BTreePrinter;

/**
 * Created by Wushudeng on 2018/8/26.
 */
public class SkewHeap {
    private static class HeapNode<T> implements Cloneable{
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

        public HeapNode<T> clone() {
            return new HeapNode<T>(
                    this.data,
                    //deep clone
                    //this.left == null ? null : this.left.clone(),
                    this.left,
                    //deep clone
                    //this.right == null ? null : this.right.clone(),
                    this.right
            );
        }
    }

    /**
     * This Integer should be changed to T.
     * However, it is necessary to implement comparable interface.
     */
    private HeapNode<Integer> root;

    /**
     * meld recursively
     * @param h1
     * @param h2
     */
    private HeapNode<Integer> rmeld(HeapNode<Integer> h1, HeapNode<Integer> h2) {
        HeapNode<Integer> temp;
        // if h1 or h2 is null
        if (h1 == null) {
            return h2;
        }
        if (h2 == null) {
            return h1;
        }

        if (h1.data > h2.data) {
            temp = h1.clone();
            h1 = h2.clone();
            h2 = temp.clone();
        }

        HeapNode<Integer> right = h1.right == null ? null : h1.right.clone();
        h1.right = h1.left == null ? null : h1.left.clone();
        h1.left = rmeld(right, h2);
        return h1;
    }

    /**
     * meld iteratively
     * @param h1
     * @param h2
     * @return
     */
    private HeapNode<Integer> imeld(HeapNode<Integer> h1, HeapNode<Integer> h2) {
        HeapNode<Integer> temp;
        // if h1 or h2 is null
        if (h1 == null) {
            return h2;
        }
        if (h2 == null) {
            return h1;
        }

        if (h1.data > h2.data) {
            temp = h1.clone();
            h1 = h2.clone();
            h2 = temp.clone();
        }

        HeapNode<Integer> x=h1, y=h1;
        do {
            h1 = y.right == null ? null : y.right.clone();
            y.right = y.left;
            if (h1 == null) {
                y.left = h2;
                break;
            } else if (h2 == null) {
                y.left = h1;
                break;
            } else {
                if (h1.data > h2.data) {
                    temp = h1.clone();
                    h1 = h2.clone();
                    h2 = temp.clone();
                }
                y.left = h1;
                y=h1;
            }
        }while (true);
        return x;
    }


    private void insert(Integer data) {
        if (root == null) {
            root = new HeapNode<Integer>(data);
            return;
        }
        root = rmeld(root, new HeapNode<Integer>(data));
//        root = imeld(root, new HeapNode<Integer>(data));
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
        SkewHeap heap = new SkewHeap();
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
