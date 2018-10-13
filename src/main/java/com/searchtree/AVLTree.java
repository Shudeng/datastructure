package com.searchtree;

/**
 * Created by Wushudeng on 2018/10/13.
 */
public class AVLTree<T extends Comparable<? super T>> extends AbstractBinarySearchTree<T> {

    /**
     * return the node on the path from inserted node to path whose height does not change.
     * call such node height-unchanged node.
     * @param node
     * @return
     */
    private BinarySearchTreeNode<T> updateHeight(BinarySearchTreeNode<T> node) {
        int height;
        for (; node.parent != null;) {
            node=node.parent;
            height = Math.max(node.left ==null ? 0 : node.left.height, node.right==null ? 0:node.right.height)+1;
            if (node.height == height)
                return node;
//            node.height = Math.max(node.left ==null ? 0 : node.left.height, node.right==null ? 0:node.right.height);
            node.height = height;
        }

        return null;
    }

    /**
     * 3+4 connection, 3 means there nodes which are node, parent and grandparent.
     * @param left
     * @param temp_root
     * @param right
     */
    private BinarySearchTreeNode<T> connect34(BinarySearchTreeNode<T> left, BinarySearchTreeNode<T> temp_root, BinarySearchTreeNode<T> right,
                           BinarySearchTreeNode<T> t1, BinarySearchTreeNode<T> t2, BinarySearchTreeNode<T> t3, BinarySearchTreeNode<T> t4) {
        temp_root.left = left;
        left.parent = temp_root;
        temp_root.right = right;
        right.parent = temp_root;
        left.left = t1;
        if (t1 != null) t1.parent = left;
        left.right = t2;
        if (t2 != null) t2.parent = left;
        right.left = t3;
        if (t3 != null) t3.parent = right;
        right.right = t4;
        if (t4 != null) t4.parent = right;

        //update height of there nodes
        // is there easy way to update height?
        left.height = Math.max(left.left == null ? 0 : left.left.height, left.right==null ? 0 : left.right.height)+1;
        right.height = Math.max(right.left == null ? 0 : right.left.height, right.right == null ? 0 : right.right.height)+1;
        temp_root.height = Math.max(temp_root.left == null ? 0 : temp_root.left.height, root.right == null ? 0 : temp_root.right.height)+1;

        return temp_root;
    }

    private void rebalance(BinarySearchTreeNode<T> node, BinarySearchTreeNode<T> parent, BinarySearchTreeNode<T> grandparent) {
        if (grandparent == null) return;
        BinarySearchTreeNode<T> parent_of_grandparent = grandparent.parent, adjust_root;
        //there are 4 kinds
        //if parent is the left child of grandparent
        if (grandparent.left == parent) {
            // if node is the left child of parent
            if (node == parent.left) {
                //t1 = node.left;t2 = node.right;t3 = parent.right;t4 = grandparent.right;
                adjust_root = connect34(node, parent, grandparent, node.left, node.right, parent.right, grandparent.right);

            }
            // if node is the right child of parent
            else {
                //t1 = parent.left;t2 = node.left;t3 = node.right;t4 = grandparent.right;
                adjust_root = connect34(parent, node, grandparent, parent.left, node.left, node.right, grandparent.right);
            }
        } else {
            if (node == parent.left) {
                //t1 = grandparent.left; t2 = node.left; t3 = node.right; t4 = parent.right;
                adjust_root= connect34(grandparent, node, parent, grandparent.left, node.left, node.right, parent.right);
            } else {
                //t1 = grandparent.left; t2 = parent.left; t3 = node.left; t4 = node.right;
                adjust_root= connect34(grandparent, parent, node, grandparent.left, parent.left, node.left, node.right);
            }
        }
        if (parent_of_grandparent != null) {
            if (grandparent == parent_of_grandparent.left)
                parent_of_grandparent.left = adjust_root;
            else parent_of_grandparent.right = adjust_root;
            adjust_root.parent = parent_of_grandparent;
            // update height after rebalance.
            updateHeight(adjust_root);

        } else {
            root = parent;
            root.parent = null;
        }

    }

    /**
     * find unbalanced node.
     * on the path from inserted node to root, if the height of one node does not increase,
     * the nodes above it will not increase in height as well.
     * So the nodes above such node will not be the unbalaned node.
     * @param node
     * @return the grandchild of unbalanced node.
     */
    private BinarySearchTreeNode<T> find_unbalance_node(BinarySearchTreeNode<T> node, BinarySearchTreeNode<T> height_unchanged_node) {
        BinarySearchTreeNode<T> grandparent = node.parent.parent;
        int balance_factor;
        while (grandparent != height_unchanged_node) {
//        while (grandparent != null) {
            balance_factor = Math.abs((grandparent.left == null ? 0 : grandparent.left.height) - (grandparent.right == null ? 0 : grandparent.right.height));
            if (balance_factor > 1) return node;
            node = node.parent;
            grandparent = grandparent.parent;
        }
        return null;
    }

    @Override
    public void insert(T data) throws Exception {
        if (root == null) {
            root = new BinarySearchTreeNode<>(data);
            return;
        }

        BinarySearchTreeNode<T> node = root, _hot = root;
        while (node != null) {
            if (node.data.compareTo(data) == 0)
                throw new Exception(data+" already exists");
            _hot = node;
            node = node.data.compareTo(data) > 0 ? node.left : node.right;
        }

        BinarySearchTreeNode<T> new_node = new BinarySearchTreeNode<>(data);
        if (_hot.data.compareTo(data) > 0) {
            //add new node to left of _hot
            _hot.left = new_node;
            new_node.parent = _hot;
        } else {
            _hot.right = new_node;
            new_node.parent = _hot;
        }

        BinarySearchTreeNode<T> height_unchanged_node = updateHeight(new_node);
//         find the first node whose balance factor is not in [-1, 1].
        BinarySearchTreeNode<T> grandchild_of_unbalanced = find_unbalance_node(new_node, height_unchanged_node);
        if (grandchild_of_unbalanced != null) {
            //_hot is parent of new node, parent of _hot is grandparent of new node.
            rebalance(grandchild_of_unbalanced, grandchild_of_unbalanced.parent, grandchild_of_unbalanced.parent.parent);
            // below command is not needed. Beacause the height of nodes above unbalanced node has not been changed.
//            updateHeight(grandchild_of_unbalanced_node.parent.parent);
        }
    }


    @Override
    public void remove(T data) throws Exception {

    }

    public static void main(String[] args) {
        AVLTree<Integer> avlTree = new AVLTree<>();
        for (int i = 0; i<100; i++) {
            try {
                avlTree.insert(i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        avlTree.print();
    }
}
