package com.common;

import com.priorityqueue.LeftistHeap.LeftistNode;
import com.priorityqueue.LeftistHeap;

/**
 * Created by Wushudeng on 2018/9/16.
 */
public class LeftistHeapPrinter extends BinaryTreePrinter {
    public LeftistHeapPrinter(LeftistNode<Integer> node) {
        setSize(1000, 1000);
        root = tranfer_node(node);
        print();
    }

    public BinaryTreePrintNode tranfer_node(Object node) {
        LeftistHeap.LeftistNode<Integer> node1 = (LeftistHeap.LeftistNode<Integer>) node;
        if (node == null) {
            return null;
        }

        BinaryTreePrintNode printNode = new BinaryTreePrintNode(node1.getInfo());
        printNode.left = tranfer_node(node1.getLeft());
        printNode.right = tranfer_node(node1.getRight());
        return printNode;
    }
}
