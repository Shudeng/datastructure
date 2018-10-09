package com.sort;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wushudeng on 2018/10/6.
 */
public class MergeSort<T extends Comparable<? super T>> {

    public void merge_sort(T[] arr) {
        ArrayList<T> temp = new ArrayList<>(arr.length);
        for (int i=0; i<arr.length; temp.add(i++, null));
        merge_sort(arr, temp, 0, arr.length-1);
    }

    private void merge_sort(T[] arr, ArrayList<T> temp, int start, int end) {
        if (start == end) return;
        int mid = (start+end)/2, left = start, right=mid+1;
        merge_sort(arr, temp, start, mid);
        merge_sort(arr, temp, mid+1, end);
        int k = left;

        while (left <= mid && right<=end)
            temp.set(k++, arr[left].compareTo(arr[right]) < 0 ? arr[left++] : arr[right++]) ;
        while (left <= mid)
            temp.set(k++, arr[left++]);
        while (right <= end)
            temp.set(k++, arr[right++]);

        for (; start<=end; arr[start]=temp.get(start++));
    }

    public static void main(String[] args) {
        Integer[] arr = {1, 9, 6, 3, 10, 19, 18, 3, 10, 18, 29, 7};
        MergeSort<Integer> mergeSort = new MergeSort<>();

        mergeSort.merge_sort(arr);
        for (int i=0; i< arr.length; i++) {
            System.out.print(arr[i]+", ");
        }

    }
}
