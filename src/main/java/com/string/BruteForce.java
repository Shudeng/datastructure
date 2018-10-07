package com.string;

/**
 * Created by Wushudeng on 2018/10/5.
 */
public class BruteForce {
    private static int find(String text, String pattern) {
        for (int i=0; i<text.length(); i++) {
            int j=0, m=i;
            for (; j<pattern.length() && text.charAt(m)==pattern.charAt(j); j++,m++);
            if (j == pattern.length())
                return i;
        }
        // retrun -1 if pattern does not exist in text.
        return -1;
    }

    public static void main(String[] args) {
        String text = "Hello Beijing", pattern = "Beijing";
        System.out.println(find(text, pattern));
    }
}
