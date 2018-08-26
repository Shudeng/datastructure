package com.common;

/**
 * Created by Wushudeng on 2018/8/26.
 */
public class BNode<T extends Comparable<?>> {
    BNode<T> left, right;
    T data;

    public BNode(T data) {
        this.data = data;
    }

    public BNode( T data, BNode<T> left, BNode<T> right) {
        this.left = left;
        this.right = right;
        this.data = data;
    }

    public BNode<T> getLeft() {
        return left;
    }

    public BNode<T> getRight() {
        return right;
    }

    public T getData() {
        return data;
    }

    public void setLeft(BNode<T> left) {
        this.left = left;
    }

    public void setRight(BNode<T> right) {
        this.right = right;
    }

    public void setData(T data) {
        this.data = data;
    }
}
