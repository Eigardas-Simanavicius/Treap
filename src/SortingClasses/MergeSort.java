package SortingClasses;

import java.util.Comparator;

public class MergeSort {

    public static <T extends Comparable<? super T>> void mergesort(final T[] input,final Comparator<T> comparator) {

        mergeSort(input, input.length,comparator);

    }
    public static <T extends Comparable<? super T>> void mergeSort(T[] a, int n,final Comparator<T> comparator) {
        if (n < 2) {
            return;
        }
        int mid = n / 2;

        @SuppressWarnings("unchecked")
        T[] l = (T[]) new Comparable[mid];
        T[] r = (T[]) new Comparable[n-mid];

        for (int i = 0; i < mid; i++) {
            l[i] = a[i];
        }
        for (int i = mid; i < n; i++) {
            r[i - mid] = a[i];
        }
        mergeSort(l, mid,comparator);
        mergeSort(r, n - mid,comparator);

        merge(a, l, r, mid, n - mid,comparator);
    }



    public static  <T extends Comparable<? super T>> void merge( T[] a, T[] l, T[] r, int left, int right,final Comparator<T> comparator) {
        int i = 0, j = 0, k = 0;
        while (i < left && j < right) {
            if (l[i].compareTo(r[j]) == -1) {
                a[k++] = l[i++];
            }
            else {
                a[k++] = r[j++];
            }
        }
        while (i < left) {
            a[k++] = l[i++];
        }
        while (j < right) {
            a[k++] = r[j++];
        }
    }
}
