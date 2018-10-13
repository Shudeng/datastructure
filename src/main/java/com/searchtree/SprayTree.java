package com.searchtree;

/**
 * Created by Wushudeng on 2018/10/13.
 */
public class SprayTree<T extends Comparable<? super T>> extends AbstractBinarySearchTree<T> {
    /**
     * rotate from left to right pivoted at node
     * @param node
     */
    private BinarySearchTreeNode<T> zig(BinarySearchTreeNode<T> node) {
        BinarySearchTreeNode<T> parent = node.parent, left = node.left;
        node.left = left.right;
        if (left.right != null) left.right.parent = node;

        left.right = node;
        node.parent = left;

        left.parent = parent;
        if (parent != null) {
            if (node == parent.left) parent.left = left;
            else parent.right = left;
        }


        return left;
    }

    /**
     * rotate from right to left pivoted at node
     * @param node
     */
    private BinarySearchTreeNode<T> zag(BinarySearchTreeNode<T> node) {
        BinarySearchTreeNode<T> parent = node.parent, right = node.right;
        node.right = right.left;
        if (right.left != null) right.left.parent = node;

        right.left = node;
        node.parent = right;

        right.parent = parent;
        if (parent != null) {
            if (node == parent.left) parent.left = right;
            else parent.right = right;
        }


        return right;
    }

    /** although node parameter is not needed,
     * put it here just for easily understanding.
     * @param node
     * @param parent
     * @param grandparent
     */
    private void zig_zag(BinarySearchTreeNode<T> node, BinarySearchTreeNode<T> parent, BinarySearchTreeNode<T> grandparent) {
        zig(parent);
        zag(grandparent);
    }

    private void zag_zig(BinarySearchTreeNode<T> node, BinarySearchTreeNode<T> parent, BinarySearchTreeNode<T> grandparent) {
        zag(parent);
        zig(grandparent);
    }

    private void zig_zig(BinarySearchTreeNode<T> node, BinarySearchTreeNode<T> parent, BinarySearchTreeNode<T> grandparent) {
        zig(grandparent);
        zig(parent);
    }

    private void zag_zag(BinarySearchTreeNode<T> node, BinarySearchTreeNode<T> parent, BinarySearchTreeNode<T> grandparent) {
        zag(grandparent);
        zag(parent);
    }

    /**
     * it should guarantee that the parent of node is not null
     * @param node
     */
    private void spray(BinarySearchTreeNode<T> node) {
        // if parent of node is null, that is node is root, so no need to spray.
        if (node.parent == null) return;

        BinarySearchTreeNode<T> parent = node.parent, grandparent = parent.parent;
        while (grandparent != null) {
            if (node == parent.left && parent == grandparent.left)
                zig_zig(node, parent, grandparent);
            else if (node == parent.left && parent == grandparent.right)
                zig_zag(node, parent, grandparent);
            else if (node == parent.right && parent == grandparent.right)
                zag_zag(node, parent, grandparent);
            else
                // node == parent.right && parent == grandparent.left
                zag_zig(node, parent, grandparent);
            parent = node.parent;
            grandparent = parent == null ? null : parent.parent;
        }
        if (parent != null) {
            if (node == parent.left)
                zig(parent);
            else zag(parent);
        }
        root = node;
    }

    @Override
    public void insert(T data) throws Exception {
        if (root == null) {
            root = new BinarySearchTreeNode<>(data);
            return;
        }

        BinarySearchTreeNode<T> node = root, _hot = node;
        while (node != null) {
            if (node.data.compareTo(data) == 0)
                throw new Exception(data+" already exists");
            _hot = node;
            node = data.compareTo(node.data) < 0 ? node.left : node.right;
        }
        BinarySearchTreeNode<T> new_node = new BinarySearchTreeNode<>(data);
        if (_hot.data.compareTo(data) > 0) {
            _hot.left = new_node;
        } else {
            _hot.right = new_node;
        }
        new_node.parent = _hot;
        if (new_node.parent != null) {
            spray(new_node);
        }
    }

    @Override
    public void remove(T data) throws Exception {

    }

    @Override
    public BinarySearchTreeNode<T> search(T data) {
//        BinarySearchTreeNode<T> node = super.search(data, _hot);

        BinarySearchTreeNode<T> node = root, _hot=null;
        while (node != null) {
            if (node.data.compareTo(data) == 0) {
                break;
            }
            _hot = node;
            node = data.compareTo(node.data) < 0 ? node.left : node.right;
        }

        spray(node == null ? _hot : node);
        return root;
    }

    public static void main(String[] args) {
        SprayTree<Integer> sprayTree = new SprayTree<>();
        for (int i=0; i<10; i++) {
            try {
                sprayTree.insert(i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        sprayTree.search(1);
        sprayTree.search(3);
        sprayTree.search(19);
        sprayTree.print();
    }
}