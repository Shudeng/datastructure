package com.tree;

import com.common.PrefixFreeTreePrinter;

/**
 * Created by Wushudeng on 2018/10/9.
 */
public class OptimalCodingTree extends PrefixFreeTree {
    public OptimalCodingTree(Character[] character_set) {
        this.character_set = character_set;
        this.prefix_free_tree = construct_pfc_tree();
        coding_table = construct_coding_table();

        int i = 0;
    }

    public BinaryTree<Character> construct_pfc_tree() {
        int leaf_num = character_set.length;
        IntegerReference i = new IntegerReference(0);
        BinaryTree.BinaryNode<Character> root = construct_true_complete_binary_tree(leaf_num, i);


        return new BinaryTree<Character>(root);
    }

    private static class IntegerReference {
        int i;

        public IntegerReference(int i) {
            this.i = i;
        }
        public void inc() {i++;}
        public void dec() {i--;}
        public int getValue() {return i;}
    }

    public BinaryTree.BinaryNode<Character> construct_true_complete_binary_tree(int leaf_num, IntegerReference i) {
        if (leaf_num == 1) {
            int value = i.getValue();
            i.inc();
            return new BinaryTree.BinaryNode<Character>(character_set[value]);
        }
        else {
            BinaryTree.BinaryNode<Character> root = new BinaryTree.BinaryNode<>('^');
            root.left = construct_true_complete_binary_tree((leaf_num+1)/2, i);
            root.right = construct_true_complete_binary_tree(leaf_num/2, i);
            return root;
        }
    }


    public static void main(String[] args) {
        char[] char_set = "abcdefghijklmnopqrstuvwxyz1234567890,.<>?/;:'}{+-) (*&^%$#@!".toCharArray();

        Character[] character_set = new Character[char_set.length];
        for (int i=0; i<char_set.length; character_set[i] = (Character)char_set[i], i++);
        OptimalCodingTree optimalCodingTree = new OptimalCodingTree(character_set);

        PrefixFreeTreePrinter printer = new PrefixFreeTreePrinter(optimalCodingTree.prefix_free_tree.get_root());

        String message = "hello optimal-coding tree";
        try {
            String encode = optimalCodingTree.encode(message);
            System.out.println(encode);
            String decode = optimalCodingTree.decode(encode);
            System.out.println(decode);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
