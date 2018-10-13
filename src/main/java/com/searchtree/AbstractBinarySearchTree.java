package com.searchtree;

import com.common.BinarySearchTreePrinter;

/**
 * Created by Wushudeng on 2018/10/12.
 */
public abstract class AbstractBinarySearchTree <T extends Comparable<? super T>>{
    BinarySearchTreeNode<T> root = null;
    abstract public void insert(T data) throws Exception;
    abstract public void remove(T data) throws Exception;

    public BinarySearchTreeNode<T> search(T data) {
        BinarySearchTreeNode<T> node = root;

        while (node!= null && node.data.compareTo(data) != 0) {
            node = node.data.compareTo(data) < 0 ? node.right : node.left;
        }
        // node is null or the searched node.
        return node;
    }

    public void print() {
        BinarySearchTreePrinter<T> printer = new BinarySearchTreePrinter<>(root);
    }
}
