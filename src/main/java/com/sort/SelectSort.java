package com.sort;

/**
 * Created by Wushudeng on 2018/10/7.
 */
public class SelectSort<T extends Comparable<? super T>> {
    private void selectSort(T[] arr) {
        for (int i=0; i<arr.length; i++) {
            T min = arr[i];
            int min_index = i;
            for (int j=i; j<arr.length; j++) {
                if (min.compareTo(arr[j]) > 0) {
                    min = arr[j];
                    min_index = j;
                }
            }
            swap(arr, i, min_index);
        }
    }

    private void swap(T[] arr, int i, int j) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        Integer [] arr = {1, 5, 3, 2, 9, 10, 18, 13, 17, 20};
        new SelectSort<Integer>().selectSort(arr);
        for (int i=0; i<arr.length; i++) {
            System.out.print(arr[i]+"\t");
        }
    }


}
