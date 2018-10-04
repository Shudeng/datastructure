package com.common;
import com.priorityqueue.BottomUpSkewHeap.BottomUpSkewHeapNode;

import java.awt.*;

/**
 * Created by Wushudeng on 2018/9/16.
 */
public class BottomUpSkewHeapPrinter extends BinaryTreePrinter {
    public BottomUpSkewHeapPrinter() throws HeadlessException {
    }
    public BottomUpSkewHeapPrinter(BottomUpSkewHeapNode<Integer> node) {
        setSize(1000, 1000);
        root = tranfer_node(node);
        print();
    }

    @Override
    public BinaryTreePrintNode tranfer_node(Object o) {
        BottomUpSkewHeapNode<Integer> node = (BottomUpSkewHeapNode<Integer>) o;
        if (node == null)
            return null;
        BinaryTreePrintNode print_node = new BinaryTreePrintNode(node.getInfo());
        print_node.left = tranfer_node(node.getLeft());
        print_node.right = tranfer_node(node.getRight());
        return print_node;
    }
}
