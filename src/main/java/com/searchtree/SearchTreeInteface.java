package com.searchtree;

/**
 * Created by Wushudeng on 2018/10/12.
 */
public interface SearchTreeInteface<T extends Comparable<? super T>> {
    public void insert(T data);
    public void remove(T data) throws Exception;
    public BinarySearchTreeNode<T> search(T data, BinarySearchTreeNode<T> _hot);

}
