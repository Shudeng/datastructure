package com.common;

import com.searchtree.KDimensionTree;

/**
 * Created by Wushudeng on 2018/10/19.
 */
public class KdTreePrinter extends BinaryTreePrinter {
    public KdTreePrinter(){
    }
    public KdTreePrinter(KDimensionTree.KdTreeNode node) {
        setSize(1000, 1000);
        root = tranfer_node(node);
        print();
    }
    @Override
    public BinaryTreePrintNode tranfer_node(Object o) {
        KDimensionTree.KdTreeNode node = (KDimensionTree.KdTreeNode) o;
        if (node == null)
            return null;
        BinaryTreePrintNode print_node = new BinaryTreePrintNode(node.get_info());
        print_node.left = tranfer_node(node.getLeft());
        print_node.right = tranfer_node(node.getRight());

        return print_node;
    }
}
