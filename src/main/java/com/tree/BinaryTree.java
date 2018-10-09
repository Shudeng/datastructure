package com.tree;

/**
 * Created by Wushudeng on 2018/10/9.
 */
public class BinaryTree<T> {
    private BinaryNode<T> root = null;
    public static class BinaryNode<T> {
        T data;
        BinaryNode<T> left=null, right=null;

        public BinaryNode(T data) {
            this.data = data;
        }

        public boolean is_leaf() {
            return this.left == null && this.right == null;
        }

        public T getData() {
            return data;
        }

        public BinaryNode<T> getLeft() {
            return left;
        }

        public BinaryNode<T> getRight() {
            return right;
        }
    }

    public BinaryTree(T e) {
        root = new BinaryNode<>(e);
    }

    public BinaryTree(BinaryNode<T> node) {
        root = node;
    }

    public BinaryNode<T> get_root() {return root;}

    public boolean is_empty() {return root == null;}

    public void insert_element_as_left(BinaryNode<T> t, T e) throws Exception{
        if (t.left != null) throw new Exception("left child already exists.");
        else t.left = new BinaryNode<>(e);
    }

    public void insert_element_as_right(BinaryNode<T> t, T e) throws Exception {
        if (t.right != null) throw new Exception("right child already exists.");
        else t.right = new BinaryNode<>(e);
    }

    public void insert_tree_as_left(BinaryNode<T> t, BinaryNode<T> bt) throws Exception{
        if (t.left != null) throw new Exception("left child already exists.");
        else t.left = bt;
    }

    public void insert_tree_as_right(BinaryNode<T> t, BinaryNode<T> bt) throws Exception{
        if (t.right != null) throw new Exception("left child already exists.");
        else t.right = bt;
    }

    public void insert_as_root(T e) throws Exception {
        if (root != null) throw new Exception("root is not null.");
        else root = new BinaryNode<>(e);
    }

    /**
     * remove child tree whose root is node from this tree.
     * @param node
     */
    public void remove(BinaryNode<T> node) {}

    /**
     * secede the tree whose root is node from this tree, and return the child tree.
     * @param node
     * @return
     */
    public BinaryTree<T> secede(BinaryNode<T> node){return null;}

}
