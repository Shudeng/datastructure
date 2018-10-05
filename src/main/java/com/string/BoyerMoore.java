package com.string;

import java.util.HashMap;

/**
 * Created by Wushudeng on 2018/10/4.
 */
public class BoyerMoore {
    public BoyerMoore() {

    }

    /**
     * bad code algorithm
     * @param pattern
     * @return the right most positions of Character in pattern
     */
    private static HashMap<Character, Integer> get_bs(String pattern) {
        HashMap<Character, Integer> bs_table = new HashMap<>();
        char[] chars = pattern.toCharArray();
        for (int i=0; i<chars.length; i++) {
            bs_table.put(chars[i], i);
        }
        return bs_table;
    }

    /**
     * the cost time of this method is O(n^2)
     * @param pattern
     * @return
     */
    private static int[] build_ss_violently(String pattern) {
        int[] ss = new int[pattern.length()];
        for (int i=0; i<ss.length; i++) {
            // pattern_i decrease from the last of pattern to left.
            int j = i, pattern_match_i=pattern.length()-1;
            for (;j>=0 && pattern.charAt(j) == pattern.charAt(pattern_match_i);j--, pattern_match_i--);
            ss[i] = i-j;
        }
        return ss;
    }

    /**
     * the cost time of this method is linear, O(n).
     * to prove this algorithm is O(n) in time cost, either j or lo decrease by one during each inner iteration and outer iteration.
     * ******* this algorithm is quite complicated, much time should be spent to understand it ******
     * ******* you may know what it want to do, but thinking out the method may be challenging.
     * @param pattern
     * @return
     */
    private static int[] build_ss_quickly(String pattern) {
        int[] ss = new int[pattern.length()];
        int len = pattern.length();
        // the match string length of the last character is the pattern itself, whose length is len.
        ss[len-1] = len;
        // starting from the last second character, calculate the ss value of each position.
        int lo = len-2, hi=lo;
        for (int j=lo; j>=0; j--) {
            // use the existing search result.
            // hi-j is the path length which is already searched.
            // len-1 -(hi-j) is the position of certain character whose length away from the last is the same as hi-j.
            if (lo<j && ss[len-hi+j-1] <= j-lo) {
                ss[j] = ss[len-hi+j-1];
            } else {
                hi = j;
                lo = Math.min(hi, lo);
                while ( (0<=lo) && (pattern.charAt(lo) == pattern.charAt(len-1-(hi-lo))) )
                    lo--;
                ss[j] = hi-lo;
            }
        }
        return ss;
    }

    /**
     * the value of gs[i] means the offset you should move to left if the bad code occur in position i.
     * @param ss
     * @return
     */
    public static int[] build_gs(int[] ss) {
        int[] gs = new int[ss.length];
        int len = ss.length;
        // by default, the offset is length of ss.
        for (int i=0; i<gs.length; i++) {
            gs[i] = ss.length;
        }
        for (int i=0, j=len-1; j>=0; j--) {
            if (j+1 == ss[j]) {
                while (i<len-1-j) {
                    gs[i++] = len-1-j;
                }
            }
        }

        // gs[i] would be overrided.
        // 画家算法
        // i is in increasing order, so the gs[i] is getting smaller.
        for (int i=0; i<len-1; i++) {
            gs[len-1-ss[i]] = len-1-i;
        }
        return gs;
    }


    /**
     * @param text
     * @param pattern
     * @return the positions of the first matched position.
     */
    public static int bm_use_bc(String text, String pattern) {
        /**
         * pattern_i is the matching position of pattern which match from the last to front.
         * test_match_i is the matching position of text.
         * test_match_j is the position at which the pattern's last character compare.
         */
        int pattern_i=pattern.length()-1, text_match_i=pattern_i, text_match_j=pattern_i;
        HashMap<Character, Integer> bs_table = get_bs(pattern);

        while (text_match_j<text.length()) {
            for (;pattern_i >=0 && text_match_i>=0 && text.charAt(text_match_i) == pattern.charAt(pattern_i); text_match_i--, pattern_i--);
            if (pattern_i==-1) {
                return text_match_i+1;
            }
            // now the char at position text_match_i is the bade code.
            int index = bs_table.get(text.charAt(text_match_i)) != null ? bs_table.get(text.charAt(text_match_i)) : -1,
                    offset;

            // if the bad code exist in pattern after the pattern_i position.
            // why? to suit the good suffix algorithm or some other reasons? I don't figure it out.
            if (index > pattern_i) {
                offset = 1;
            } else {
                offset = pattern.length() - 1 - index;
            }
            pattern_i = pattern.length()-1;
            text_match_j = text_match_j+offset;
            text_match_i = text_match_j;
        }

        // can not find pattern in text
        return -1;
    }

    private static HashMap<Character, Integer> build_bc(String pattern) {
        HashMap<Character, Integer> hashMap = new HashMap<>();
        //hashmap record the last position of one character.
        for (int i=0; i<pattern.length(); i++) {
            hashMap.put(pattern.charAt(i), i);
        }
        return hashMap;
    }

    public static int bm_use_gs_and_bs(String text, String pattern) {
        int len = pattern.length();
        int pattern_i, text_match_i=len-1, text_match_j=len-1;
        HashMap<Character, Integer> bc = build_bc(pattern);
        int [] gs = build_gs(build_ss_quickly(pattern));
        int bc_offset, gs_offset;
        while (text_match_j < text.length()) {
            pattern_i = len-1;
            for (;pattern_i>=0 && pattern.charAt(pattern_i)==text.charAt(text_match_i); pattern_i--, text_match_i--);
            if (pattern_i==-1)
                return text_match_i+1;
            bc_offset = pattern_i-bc.get(pattern.charAt(pattern_i));
            gs_offset = gs[pattern_i];
            int offset = bc_offset > gs_offset ? bc_offset : gs_offset;
            text_match_i = text_match_j = text_match_j+offset;
        }
        return -1;
    }

    public static void main(String[] args) {
        String text = "nihaobeijingnihaohello";
        String pattern = "beijing";
        System.out.println(bm_use_bc(text, pattern));
        System.out.println(bm_use_gs_and_bs(text, pattern));
    }


}
