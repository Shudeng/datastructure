package com.common;

import com.searchtree.BinarySearchTreeNode;
import java.awt.*;

/**
 * Created by Wushudeng on 2018/10/12.
 */
public class BinarySearchTreePrinter<T extends Comparable<? super T> > extends BinaryTreePrinter{
    public BinarySearchTreePrinter(){
    }
    public BinarySearchTreePrinter(BinarySearchTreeNode<T> node) {
        setSize(1000, 1000);
        root = tranfer_node(node);
        print();
    }

    @Override
    public BinaryTreePrintNode tranfer_node(Object o) {
        BinarySearchTreeNode<Integer> node = (BinarySearchTreeNode<Integer>) o;
        if (node == null)
            return null;
        BinaryTreePrintNode print_node = new BinaryTreePrintNode(node.toString());
        print_node.left = tranfer_node(node.getLeft());
        print_node.right = tranfer_node(node.getRight());
        return print_node;
    }
}
