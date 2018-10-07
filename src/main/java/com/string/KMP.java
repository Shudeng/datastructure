package com.string;

/**
 * Created by Wushudeng on 2018/10/5.
 */
public class KMP {
    private static int[] build_next(String pattern) {
        int len = pattern.length();
        int[] next = new int[len];
        next[0] = -1;
        for (int i=0; i<len-1; i++) {
            int j = next[i];
            while (true) {
                // next_i<0 which is also next_i=-1, means that iteration has come to the first character of pattern.
                // in the next array, only next[0] can be negative value, whose value is -1.
                if (j<0 || pattern.charAt(i) == pattern.charAt(j)) {
                    next[i+1]=j+1;
                    break;
                } else {
                    j = next[j];
                }
            }
        }
        return next;
    }

    private static int [] build_next_improve(String pattern) {
        int len = pattern.length();
        int[] next = new int[len];
        next[0] = -1;
        // similarly, let k=2i-j, the time cost of build_next is O(m).
        // m is the length of pattern.
        for (int i=0; i<len-1; i++) {
            int j = next[i];
            while (true) {
                // next_i<0 which is also next_i=-1, means that iteration has come to the first character of pattern.
                // in the next array, only next[0] can be negative value, whose value is -1.
                if (j<0 || pattern.charAt(i) == pattern.charAt(j)) {
                    // here is the difference from the above build_next method.
                    next[i+1] = pattern.charAt(i+1) == pattern.charAt(j+1) ? next[j+1] : j+1;
                    break;
                } else {
                    j = next[j];
                }
            }
        }
        return next;
    }

    private static int kmp(String text, String pattern) {
        int[] next = build_next_improve(pattern);
//        int[] next = build_next(pattern);
        int len = pattern.length();

        // let k = 2i-j, k>0 and k is in increasing order. Initially k = 0 and finally k <= 2(text.length-1)-(-1)= 2(text.length)-1,
        // so the time cost is O(n).
        for (int i=0, j=0; i<text.length();) {
            if (j<0 || pattern.charAt(j) == text.charAt(i)) {
                j++;
                i++;
            } else {
                j=next[j];
            }
            if (j == len) {
                return i-len;
            }
        }
        // -1 means there is no pattern string in text.
        return -1;
    }



    public static void main(String[] args) {
        String text = "000100001";
        String pattern = "00001";
        int pos = kmp(text, pattern);
        System.out.println(pos);

    }
}
