package com.common;

import com.tree.BinaryTree;

/**
 * Created by Wushudeng on 2018/10/9.
 */
public class PrefixFreeTreePrinter extends BinaryTreePrinter{
    public PrefixFreeTreePrinter(){
    }
    public PrefixFreeTreePrinter(BinaryTree.BinaryNode<Character> node) {
        setSize(1000, 1000);
        root = tranfer_node(node);
        print();
    }

    @Override
    public BinaryTreePrintNode tranfer_node(Object o) {
        BinaryTree.BinaryNode<Character> node = (BinaryTree.BinaryNode<Character>)o;
        if (node == null)
            return null;
        BinaryTreePrintNode print_node = new BinaryTreePrintNode(node.getData().toString());
        print_node.left = tranfer_node(node.getLeft());
        print_node.right = tranfer_node(node.getRight());
        return print_node;
    }
}
