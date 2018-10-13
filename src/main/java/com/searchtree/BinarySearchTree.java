package com.searchtree;

/**
 * Created by Wushudeng on 2018/10/12.
 */
public class BinarySearchTree<T extends Comparable<? super T>> extends AbstractBinarySearchTree<T>{
    private BinarySearchTreeNode<T> successor(BinarySearchTreeNode<T> node, BinarySearchTreeNode<T> succ_parent) {
        succ_parent = node;
        BinarySearchTreeNode<T> n = node.right;
        for (;n.left != null; succ_parent = n, n=n.left);
        return n;
    }

    @Override
    public void insert(T data){
        if (root == null) {
            root = new BinarySearchTreeNode<>(data);
            return;
        }

        try {
            root = insert(root,data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private BinarySearchTreeNode<T> insert(BinarySearchTreeNode<T> node, T data) throws Exception{
        if (node == null) return new BinarySearchTreeNode<>(data);
        if (node.data.compareTo(data) == 0) throw new Exception("this data alredy exist.");
        if (node.data.compareTo(data) < 0)
            node.right = insert(node.right, data);
        else
            node.left = insert(node.left, data);
        return node;
    }
    @Override
    public void remove(T data) throws Exception {
        root = remove(root, data);
    }


    private BinarySearchTreeNode<T> remove(BinarySearchTreeNode<T> node, T data) throws Exception {
        if (node == null) {
            //if node == null, it means no this data exists in the tree.
            throw new Exception(data+" does not exist in tree");
        }

        if (data.compareTo(node.data) < 0)
        {
            node.left = remove(node.left, data);
            return node;
        }

        else if (data.compareTo(node.data) > 0)
        {
            node.right = remove(node.right, data);
            return node;
        }

        if (node.left == null)
            return node.right;
        if (node.right == null)
            return node.left;

        BinarySearchTreeNode<T> succ, succ_parent=new BinarySearchTreeNode<>();
        succ = successor(node, succ_parent);
        BinarySearchTreeNode<T> succ_right = succ.right;
        node.data = succ.data;
        if (succ_parent == node)
            succ_parent.right = succ_right;
        else succ_parent.left = succ_right;
        return node;
    }

    /**
     *
     * @param data
     * @param _hot be parent of the searched node, or the last of the searched path.
     * @return
     */
    @Override
    public BinarySearchTreeNode<T> search(T data, BinarySearchTreeNode<T> _hot) {
        BinarySearchTreeNode<T> node = root;

        while (node!= null && node.data.compareTo(data) != 0) {
            _hot = node;
            node = node.data.compareTo(data) < 0 ? node.right : node.left;
        }
        // node is null or the searched node.
        return node;
    }

    public static void main(String[] args) {
        BinarySearchTree<Integer> binarySearchTree = new BinarySearchTree<>();
        Integer[] nums = {8,6,9,10,2,18,15,20};
        for (Integer num : nums)
        {
            binarySearchTree.insert(num);
        }

        binarySearchTree.print();
    }


}
