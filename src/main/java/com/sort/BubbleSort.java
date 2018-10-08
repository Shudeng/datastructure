package com.sort;

/**
 * Created by Wushudeng on 2018/10/7.
 */
public class BubbleSort<T extends Comparable<? super T>> {
    public void bubble_sort(T[] arr) {
        for (int i=0; i<arr.length; i++) {
            boolean flag = true;
            for (int j = i; j + 1 < arr.length; j++) {
                if (arr[j].compareTo(arr[j + 1]) > 0) {
                    swap(arr, j, j + 1);
                    flag = false;
                }

            }
            if (flag)
                break;
        }
    }

    private void swap(T[] arr, int i, int j) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        Integer [] arr = {1, 5, 3, 2, 9, 10, 18, 13, 17, 20};
        new BubbleSort<Integer>().bubble_sort(arr);
        for (int i=0; i<arr.length; i++) {
            System.out.print(arr[i]+"\t");
        }
    }
}
