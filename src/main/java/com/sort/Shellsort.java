package com.sort;

/**
 * Created by Wushudeng on 2018/10/7.
 */
public class Shellsort<T extends Comparable<? super T>> {
    private void insert_sort(T[] arr, int start, int gap) {
        int cursor = start + gap;
        for ( ; cursor<arr.length; cursor+=gap) {
            T temp = arr[cursor];
            int i = cursor-gap;
            while (i>=0 && arr[i].compareTo(temp) > 0) i-=gap;
            for (int j=cursor; j>i+gap; arr[j]=arr[j-gap], j-=gap);
            arr[i+gap] = temp;
        }
    }

    private void shell_sort(T[] arr, int gap) throws IllegalArgumentException {
        if (gap >= arr.length)
            throw new IllegalArgumentException("gap should less than length of array");

        for (; gap>0 ; gap/=2) {
            for (int i=0; i<gap; i++) {
                insert_sort(arr, i, gap);
            }
        }
    }

    public static void main(String[] args) {
        Integer[] arr = {1, 9, 6, 3, 10, 19, 18, 3, 10, 18, 29, 7, 19, 29, 38, 56, 64, 8,26};
        Shellsort<Integer> shellsort = new Shellsort<>();
//        shellsort.InsertSort(arr, 1, 2);
        shellsort.shell_sort(arr, 10);

        for (int i=0; i< arr.length; i++) {
            System.out.print(arr[i]+"\t");
        }

    }


}
