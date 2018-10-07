package com.string;

/**
 * Created by Wushudeng on 2018/10/5.
 */
public class KarpRabin {
    private static int M = 97;
    private static int R = 10;
    //get the digital value of character in the ith position of s.
    private static int digit(String s, int i) {
        return s.charAt(i)-'0';
    }

    // check if pattern equals to text[i:i+pattern.length]
    private static boolean check_equals(String text, String pattern, int i) {
        int j=0;
        for (; j<pattern.length() && pattern.charAt(j)==text.charAt(i); j++, i++);
        return j==pattern.length() ? true : false;
    }

    private static long preparedDm(int m) {
        long Dm = 1;
        for (int i=1; i<m; i++) {
            Dm = (R*Dm)%M;
        }
        return Dm;
    }

    private static long update_hash(long hashcode, String text, int pattern_len, int k, long Dm) {
        hashcode = (hashcode - digit(text, k-1)*Dm) % M;
        hashcode = (hashcode*R+digit(text, k+pattern_len-1))%M;
        return hashcode < 0 ? hashcode+M : hashcode;
    }

    /**
     * find the position of pattern in text, return -1 if pattern does not exist.
     * @param text
     * @param pattern
     * @return
     */
    private static long find(String text, String pattern) {
        int pattern_len = pattern.length(), text_len = text.length();
        if (pattern_len > text_len)
            return -1;
        long Dm = preparedDm(pattern_len);
        long hash_p = 0, hash_t = 0;
        for (int i=0; i<pattern_len; i++) {
            hash_p = ( hash_p*R+digit(pattern, i) ) % M;
            hash_t = ( hash_t*R+digit(text, i) ) % M;
        }

        if (hash_p == hash_t && check_equals(text, pattern, 0)) return 0;

        for (int k=1; k<=text_len-pattern_len;k++) {
            hash_t = update_hash(hash_t, text, pattern_len, k, Dm);
            if (hash_p == hash_t)
                if (check_equals(text, pattern, k))
                    return k;
        }
        return -1;
    }


    public static void main(String[] args) {
        String text = "1231234567", pattern = "12345";
        text = "hellobeijing";
        pattern = "beijing";

//        System.out.println(digit(text, 0));
        System.out.println(find(text, pattern));
    }

}
