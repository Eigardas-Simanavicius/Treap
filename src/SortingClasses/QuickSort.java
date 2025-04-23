package SortingClasses;

import java.util.Arrays;
import java.util.Comparator;

public class QuickSort {


    public static <T extends Comparable<? super T>> void quickSort(final T[] data,int low, int high, final Comparator<T> comparator) {

        if(low < high) {
            int middle = partition(data,low,high,comparator);

            quickSort(data, low, middle - 1,comparator);
            quickSort(data, middle + 1, high,comparator);
        }
    }


    static <T extends Comparable<? super T>> int partition(final T[] data, int  low, int  high, final Comparator<T> comparator) {

        T pivot = data[high];
        int i = (low - 1);

        for (int j = low; j < high; j++) {
            if (data[j].compareTo(pivot) <= 0) {
                i++;

                // Swap elements at i and j
                T temp = data[i];
                data[i] = data[j];
                data[j] = temp;
            }
        }
        // Swap the pivot element with the element at i+1
        T temp = data[i + 1];
        data[i + 1] = data[high];
        data[high] = temp;

        // Return the partition index
        return i + 1;
    }

    public static void printArray(Integer[] array) {
        System.out.println(Arrays.toString(array));
    }

    public static void main(String[] args) {
        Integer[] arr = {9, 7, 5, 11, 12, 2, 14, 3, 10, 6};
        IntComp intComp = new IntComp();
        System.out.println("Original array:");
        printArray(arr);

        // Perform SortingClasses.QuickSort
        quickSort(arr, 0, arr.length - 1, intComp);

        System.out.println("Sorted array:");
        printArray(arr);
    }
}
