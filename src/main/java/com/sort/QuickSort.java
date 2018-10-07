package com.sort;

/**
 * Created by Wushudeng on 2018/10/6.
 */
public class QuickSort<T extends Comparable<? super T>> {

    /**
     * too too complicated. not simple enough.
     * @param arr
     * @param start
     * @param end
     * @return
     */
    private int partition(T[] arr, int start, int end) {
        int pivot_index = select_pivot(arr, start, end);
        swap(arr, start, pivot_index);
        T pivot = arr[start];

        int left=start+1, right=end, cursor=start;
        while (left<=right) {
            while (right > left && arr[right].compareTo(pivot) >= 0) right--;
            if (right>cursor) {
                arr[cursor] = arr[right];
                cursor = right;
            } else {
                break;
            }

            while (left < right && arr[left].compareTo(pivot) <= 0) left++;

            if (left < cursor) {
                arr[cursor] = arr[left];
                cursor = left;
            } else {
                break;
            }
        }

        arr[cursor] = pivot;

        return cursor;
    }

    /**
     * L | R | unsearched.
     * lo(start), mid, hi.
     * @param arr
     * @param start
     * @param end
     * @return
     */
    private int partition_1(T arr[], int start, int end) {
        T pivot = arr[start];
        int hi = start, mid=start;

        for (; hi <= end; hi++) {
            if (arr[hi].compareTo(pivot) >= 0)
                continue;
            if (mid == hi-1)
                arr[mid++] = arr[hi];
            else {
                arr[mid++] = arr[hi];
                arr[hi] = arr[mid];
            }
        }
        arr[mid] = pivot;
        return mid;
    }

    private int  select_pivot(T[] arr, int start, int end) {
        T t1 = arr[start], t2=arr[(start+end)/2], t3 = arr[end];
        // return the second large element of above there value.

        if (t1.compareTo(t2)<0 && t2.compareTo(t3)<0)
            return (start+end)/2;
        else if (t1.compareTo(t3) < 0 && t3.compareTo(t2) < 0)
            return end;
        else
            return start;
    }

    private void swap(T[] arr, int i, int j) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }


    public void quicksort(T[] arr, int start, int end) {
        if (start >= end) return;
//        int cursor = partition(arr, start, end);
        int cursor = partition_1(arr, start, end);
        quicksort(arr, start, cursor-1);
        quicksort(arr, cursor+1, end);
    }

    /**
     * select kth large element, order is from 0.
     * @param arr
     * @param k
     * @param start
     * @param end
     * @return
     */
    private T k_selection(T[] arr, int k, int start, int end) {
        int cursor = partition_1(arr, start, end);
        if (cursor == k)
            return arr[cursor];
        if (cursor > k)
            return k_selection(arr, k, start, cursor-1);
        return k_selection(arr, k, cursor+1, end);
    }

    public static void main(String[] args) {
        Integer[] arr = {5, 6, 8, 10, 13, 18, 3, 8};
        QuickSort<Integer> quickSort = new QuickSort<>();

        System.out.println(quickSort.k_selection(arr,3, 0, arr.length-1));
        System.exit(0);
        quickSort.partition_1(arr, 0, arr.length-1);
        for (int i=0; i<arr.length; i++) {
            System.out.print(arr[i]+"\t");
        }

        System.out.println();
        quickSort.quicksort(arr, 0, arr.length-1);
        for (int i=0; i<arr.length; i++) {
            System.out.print(arr[i]+"\t");
        }
    }
}
