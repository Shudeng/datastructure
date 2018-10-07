package com.sort;

/**
 * Created by Wushudeng on 2018/10/7.
 */
public class InsertSort<T extends Comparable<? super T> > {
    public void insert_sort(T[] arr) {
        T temp;
        for (int i=1; i<arr.length; i++) {
            temp = arr[i];
            int j=i-1;
            // find insertion position.
            for (; j>=0 && temp.compareTo(arr[j])<0; j--);
            // right move
            int m = i;
            for (; m>j+1; arr[m]=arr[m-1], m--);
            arr[j+1] = temp;
        }
    }

    public void insert_sort_use_dichotomy(T[] arr) {
        T temp;
        int insert_position;
        for (int i=1; i<arr.length; i++) {
            temp = arr[i];
            if (temp.compareTo(arr[i-1]) < 0) {
                insert_position = find_insert_index(arr, i-1, temp);
                int m = i;
                for (; m>insert_position; arr[m]=arr[m-1], m--);
                arr[insert_position] = temp;
            }
        }
    }

    private int find_insert_index(T[] arr, int end, T temp) {
        int left = 0, mid = (left+end)/2;
        while (left < end) {
            mid = (left+end)/2;
            if (arr[mid].compareTo(temp) < 0)
                left = mid+1;
            else
                end = mid;
        }
        return left;
    }

    public static void main(String[] args) {
        Integer[] arr = {5, 6, 8, 10, 13, 18, 3, 8};
        InsertSort<Integer> integerInsertSort = new InsertSort<>();

        integerInsertSort.insert_sort_use_dichotomy(arr);
        for (int i=0; i<arr.length; i++) {
            System.out.print(arr[i]+"\t");
        }
    }
}
