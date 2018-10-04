package com.priorityqueue;

import com.common.BNode;
import com.common.BTreePrinter;

import java.util.Random;

/**
 * Created by Wushudeng on 2018/9/2.
 */
public class Treap<T extends Comparable<? super T>> {
    public static class TreapNode<T extends Comparable<? super T>> implements Comparable<TreapNode<T>>{
        T key;
        int priority;
        TreapNode<T> left, right;

        public TreapNode(T key) {
            this.key = key;
            Random random = new Random();
            this.priority = random.nextInt(100)+1;
            this.left = null;
            this.right = null;
        }

        public TreapNode(T key, TreapNode<T> left, TreapNode<T> right) {
            this.key = key;
            this.left = left;
            this.right = right;
            Random random = new Random();
            this.priority = random.nextInt();
        }

        public int compareTo(TreapNode<T> o) {
            return key.compareTo(o.key);
        }
    }

    TreapNode<T> root;

    public Treap(TreapNode<T> root) {
        this.root = root;
    }

    public Treap() {
    }

    public TreapNode<T> find(TreapNode<T> node, T key) {
        if (node == null) {
            return null;
        }
        if (node.key.compareTo(key) > 0) {
            /**
             * new key should be inserted to the left side.
             */
            return find(node.left, key);
        } else if (node.key.compareTo(key) < 0) {
            /**
             * new key should be inserted to the right side.
             */
            return find(node.right, key);
        } else {
            return node;
        }
    }

    private TreapNode<T> right_rotate(TreapNode<T> node, TreapNode<T> parent) {
        TreapNode<T> left_chld = node.left;
        TreapNode<T> left_child_right = left_chld != null ? null : left_chld.right;
        left_chld.right = node;
        node.left = left_child_right;
        if (parent != null) {
            if (node == parent.left) {
                parent.left = left_chld;
            } else {
                parent.right = left_chld;
            }
        }

        return left_chld;
    }

    private TreapNode<T> left_rotate(TreapNode<T> node, TreapNode<T> parent) {
        TreapNode<T> right_child = node.right;
        TreapNode<T> right_child_left = right_child == null ? null : right_child.left;
        right_child.left = node;
        node.right = right_child_left;
        if (parent != null) {
            if (node == parent.left) {
                parent.left = right_child;
            } else {
                parent.right = right_child;
            }
        }
        return right_child;
    }


    public TreapNode<T> insert(TreapNode<T> node, TreapNode<T> parent, T key) throws Exception{
        TreapNode<T> new_node = new TreapNode<T>(key);

        if (node == null) {
            return new_node;
        }

        if (node.key.compareTo(key) == 0) {
            throw new Exception("key already exists");
        } else if (node.key.compareTo(key) > 0) {
            node.left = insert(node.left, node, key);
            if (node.priority > node.left.priority) {
                node = right_rotate(node, parent);
            }
        } else {
            node.right = insert(node.right, node, key);
            if (node.priority > node.right.priority) {
                node = left_rotate(node, parent);
            }
        }
        /**
         * change root pointer
         */
        if (parent == null) {
            root = node;
        }

        return node;
    }

    public boolean insert(T key) {
        if (root == null) {
            root = new TreapNode<T>(key);
        } else {
            try {
                insert(root, null, key);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
     }

    private TreapNode<T> delete(TreapNode<T> node, TreapNode<T> parent, T key) throws Exception{
        TreapNode<T> temp;
        if (node == null) {
            throw new Exception("treap is empty");
        }
        if (node.key.compareTo(key) > 0) {
            temp = delete(node.left, node, key);
            node.left = temp;
            return node;
        } else if (node.key.compareTo(key) < 0){
            temp = delete(node.right, node, key);
            node.right = temp;
            return node;
        } else {
            if (node.left == null) {
                temp = node.right;
                return temp;
            } else if (node.right == null) {
                temp = node.left;
                return temp;
            } else if (node.left.priority < node.right.priority) {
                temp = right_rotate(node, parent);
                if (root == node) {
                    root = temp;
                }
                temp.right = delete(temp.right, temp, key);
                return temp;
            } else {
                temp = left_rotate(node, parent);
                if (root == node) {
                    root = temp;
                }
                temp.left = delete(temp.left, temp, key);
                return temp;
            }
        }
    }

    public TreapNode<T> delete(T key) throws Exception {
        return delete(root, null, key);
    }

    /**
     * [node1, x, node2] = split(T, key)
     * @param node
     * @param key
     * @return
     */
    public TreapNode<T>[] split(TreapNode<T> node, T key) {
       TreapNode<T> [] nodes = new TreapNode[3];
       if (node == null) {
           nodes[0] = nodes[1] = nodes[2] = null;
           return nodes;
       }

       if (node.key.compareTo(key) == 0) {
           nodes[0] = node.left;
           nodes[1] = node;
           nodes[2] = node.right;
       } else if (node.key.compareTo(key) < 0) {
           TreapNode<T> [] temp_nodes = split(node.right, key);
           node.right = temp_nodes[0];
           nodes[0] = node;
           nodes[1] = temp_nodes[1];
           nodes[2] = temp_nodes[2];
        } else {
           TreapNode<T>[] temp_nodes = split(node.left, key);
           node.left = temp_nodes[2];
           nodes[0] = temp_nodes[0];
           nodes[1] = temp_nodes[1];
           nodes[2] = node;
       }
       return nodes;
    }

    /**
     * node1 and node2 have no common element.
     * @param node1
     * @param node2
     * @return
     */
    public TreapNode<T> join(TreapNode<T> node1, TreapNode<T> node2) {
        if (node1 == null) {
            return node2;
        }

        if (node2 == null) {
            return node1;
        }

        if (node1.priority < node2.priority) {
            TreapNode<T> right = join(node1.right, node2);
            node1.right = right;
            return node1;
        } else {
            TreapNode<T> left = join(node1, node2.left);
            node2.left = left;
            return node2;
        }
    }

    public static void test(TreapNode<Integer> node, Integer t) {
//        node = new TreapNode<Integer>(t);
        node.key =  100;
        System.out.println(node.key);
    }

    private void print() {
        BNode<String> node = node_change(root);
        BTreePrinter.printNode(node);
    }

    private BNode<String> node_change(TreapNode<T> node) {
        if (node == null) {
            return null;
        }

        BNode<String> b_node = new BNode<String>(node.key.toString()+","+String.valueOf(node.priority));
        b_node.setLeft(node_change(node.left));
        b_node.setRight(node_change(node.right));
        return b_node;
    }

    /**
     * fast set operation using treap
     * @param args
     */

    /**
     * todo
     * set opereations of treap : union, intersection, difference
     * @param
     */


    public static void main(String[] args) {
        Treap<Integer> treap = new Treap<Integer>();
        Integer[] list = {7,4,6,9,11,12,23, 45, 32};
        for (Integer item:list) {
            treap.insert(item);
        }
//        treap.print();

        TreapNode<Integer>[] nodes = treap.split(treap.root,23);

        new Treap<Integer>(nodes[0]).print();
        System.out.println("----------------------------------------");
        new Treap<Integer>(nodes[2]).print();
        TreapNode<Integer> join_node = treap.join(nodes[0], nodes[2]);
        System.out.println("----------------------------------------");
        new Treap<Integer>(join_node).print();

//        treap.print();
//        try {
//            treap.delete(12);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        System.out.println("--------------------------------------");
//        treap.print();
//
//        TreapNode<Integer> node = treap.find(treap.root, 80);
//        System.out.println(node.key);
//        System.out.println(node.priority);
    }


}
