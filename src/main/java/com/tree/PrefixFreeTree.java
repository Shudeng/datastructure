package com.tree;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Wushudeng on 2018/10/9.
 */
public class PrefixFreeTree {
    Character[] character_set;
    BinaryTree<Character> prefix_free_tree = null;
    HashMap<Character, String> coding_table = null;

    public PrefixFreeTree() {}

    public PrefixFreeTree(Character[] character_set) {
        this.character_set = character_set;
        prefix_free_tree = construct_pfc_tree();
        coding_table = construct_coding_table();

    }

    /**
     * construct prefix-free tree for the characters in character set.
     * in order to construct huffman tree (optimal coding tree),
     * you should use priority queue to replace the array list.
     * @return
     */
    public BinaryTree<Character> construct_pfc_tree() {
        ArrayList<BinaryTree<Character>> list = new ArrayList<>();
        for (char c:character_set) {
            list.add(new BinaryTree<Character>(c));
        }
        BinaryTree<Character> left, right;
        for (; 1 < list.size(); ) {
            BinaryTree<Character> tree = new BinaryTree<>('^');
            left = list.remove(list.size()-1);
            right = list.remove(list.size()-1);
            try {
                tree.insert_tree_as_left(tree.get_root(), left.get_root());
                tree.insert_tree_as_right(tree.get_root(), right.get_root());
            } catch (Exception e) {
                e.printStackTrace();
            }
            list.add(tree);
        }
        return list.get(0);
    }

    public HashMap<Character, String> construct_coding_table() {
        String code = "";
        HashMap<Character, String> coding_table = new HashMap<>();
        dfs(prefix_free_tree.get_root(), coding_table, code);
        return coding_table;
    }

    private void dfs(BinaryTree.BinaryNode<Character> node, HashMap<Character, String> coding_table, String code) {
        if (node.is_leaf())
            coding_table.put(node.data, code);
        else {
            if (node.left != null)
                dfs(node.left, coding_table, code+"0");
            if (node.right != null)
                dfs(node.right, coding_table, code+"1");
        }
    }

    public String encode(String message) throws Exception{
        String encode = "";
        for (Character c:message.toCharArray()) {
            if (coding_table.get(c) == null)
                throw new Exception("character set does not contain "+c.toString());
            encode+=coding_table.get(c);
        }
        return encode;
    }

    public String decode(String encode) {
        String decode = "";
        BinaryTree.BinaryNode<Character> root = prefix_free_tree.get_root(), node = root;
        for (Character c: encode.toCharArray()) {
            if (node.is_leaf()) {
                decode+=node.data;
                node = root;
            }
            if (c == '0')
                node = node.left;
            else
                node = node.right;
        }
        decode += node.data;
        return decode;
    }



    public static void main(String[] args) {
        char[] char_set = "abcdefghijklmnopqrstuvwxyz1234567890,.<>?/;:'}{+-) (*&^%$#@!".toCharArray();

        Character[] character_set = new Character[char_set.length];
        for (int i=0; i<char_set.length; character_set[i] = (Character)char_set[i], i++);
        PrefixFreeTree prefixFreeTree = new PrefixFreeTree(character_set);

        String message = "hello prefix-free tree";
        try {
            String encode = prefixFreeTree.encode(message);
            System.out.println(encode);
            String decode = prefixFreeTree.decode(encode);
            System.out.println(decode);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
