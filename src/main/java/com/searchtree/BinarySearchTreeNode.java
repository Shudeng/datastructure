package com.searchtree;

/**
 * Created by Wushudeng on 2018/10/12.
 */
public class BinarySearchTreeNode<T extends Comparable<? super T>> implements Comparable{
    T data = null;
    BinarySearchTreeNode<T> left=null, right=null, parent = null;
    int height = 1;

    public BinarySearchTreeNode(T data) {
        this.data = data;
    }

    public BinarySearchTreeNode() {
    }

    @Override
    public String toString() {
        return data.toString();
    }

    public BinarySearchTreeNode<T> getLeft() {
        return left;
    }

    public BinarySearchTreeNode<T> getRight() {
        return right;
    }

    @Override
    public int compareTo(Object o) {
        return this.compareTo((T)o);
    }
}
