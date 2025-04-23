package SortingClasses;

import tree.AVLTreeMap;
import tree.Treap;

import java.io.IOException;
import java.util.*;


public class SortComp {

    public static void main(String[] args) throws IOException {
        int arraySize = 10; // change this to test algorithms' speed with different array sizes
        int numRuns = 21;

        double[] TreapTimes = new double[numRuns];
        double[] pqtimes = new double[numRuns];
        double[]  quickSortTimes = new double[numRuns];
        double[] mergeSortTimes = new double[numRuns];
        double[] timSortTimes = new double[numRuns];
        Integer[] unsortedArray = new Integer[arraySize];
        for (int run = 0; run < numRuns; run++) {
            System.out.println("\nRun " + (run + 1));
            IntComp intComp = new IntComp();
            Random myRandom = new Random();


        // this is for generating unqieu random array
            /*
            int seed = 512;

            unsortedArray = myRandom.ints(0, 10)
                    .boxed()
                    .distinct()
                    .limit(10)
                    .toArray(Integer[]::new);
                            */

            // for reverese sorted array

            /*
            int a = 0;
            for (int i = arraySize; i > 0; i--,a++) {
                 unsortedArray[a] = i;
            }
             */


            for (int i = 0; i < arraySize; i++) {
                unsortedArray[i] = i;
            }
            int swap = myRandom.nextInt(unsortedArray.length);
            int swap2 = myRandom.nextInt(unsortedArray.length);
            int temp = unsortedArray[swap];
            unsortedArray[swap] = unsortedArray[swap2];
            unsortedArray[swap2] = temp;

            System.out.println("starting " + run);
            Integer[] TreapArray = unsortedArray.clone();
            Integer[] PriorityArray = unsortedArray.clone();
            Integer[] MergeArray = unsortedArray.clone();
            Integer[] QuickArray = unsortedArray.clone();

            List<Integer> Pqsort = new ArrayList<>(Arrays.asList(unsortedArray));
            List<Integer> Timsort = new ArrayList<>(Arrays.asList(unsortedArray));


            Treap<Integer, Integer> treap = new Treap<>(arraySize);

            AVLTreeMap<Integer, Integer> AVLTreeMap = new AVLTreeMap<>();
            long startTime = System.nanoTime();
            for (int i = 0; i < arraySize; i++) {
                treap.put(TreapArray[i], TreapArray[i]);
            }
            long endTime = System.nanoTime();
            TreapTimes[run] = (endTime - startTime) / 1_000_000.0; // / 1_000_000.0 divides the value by 1 million to give it in milliseconds.
            System.out.println("Treap time: " + TreapTimes[run] + " ms");


            PriorityQueue<Integer> pq = new PriorityQueue<>(arraySize);
            startTime = System.nanoTime();
            for (int i = 0; i < arraySize; i++) {
                pq.add(i);
            }
            endTime = System.nanoTime();
            pqtimes[run] = (endTime - startTime) / 1_000_000.0; // / 1_000_000.0 divides the value by 1 million to give it in milliseconds.
            System.out.println("Pqtimes: " + pqtimes[run] + " ms");

            startTime = System.nanoTime();
            MergeSort.mergesort(MergeArray, intComp);
            endTime = System.nanoTime();
            mergeSortTimes[run] = (endTime - startTime) / 1_000_000.0; // / 1_000_000.0 divides the value by 1 million to give it in milliseconds.
            System.out.println("Mergesort: " + mergeSortTimes[run] + " ms");


            startTime = System.nanoTime();
            QuickSort.quickSort(QuickArray, 0, unsortedArray.length - 1, intComp);
            endTime = System.nanoTime();
            quickSortTimes[run] = (endTime - startTime) / 1_000_000.0; // / 1_000_000.0 divides the value by 1 million to give it in milliseconds.
            System.out.println("quicksort: " + quickSortTimes[run] + " ms");


            startTime = System.nanoTime();
            Collections.sort(Timsort);
            endTime = System.nanoTime();
            timSortTimes[run] = (endTime - startTime) / 1_000_000.0; // / 1_000_000.0 divides the value by 1 million to give it in milliseconds.
            System.out.println("timSort: " + timSortTimes[run] + " ms");
        }
        // --- Print Averages ---
        System.out.println("\nAverage Times:");

        System.out.println("SortingClasses.QuickSort: " + String.format("%.4f", average(quickSortTimes)) + " ms");
        System.out.println("SortingClasses.MergeSort: " + String.format("%.4f", average(mergeSortTimes)) + " ms");
        System.out.println("Treapsort: " + String.format("%.4f", average(TreapTimes)) + " ms");
        System.out.println("Timsort: " + String.format("%.4f", average(timSortTimes)) + " ms");
        System.out.println("pqsort: " + String.format("%.4f", average(pqtimes)) + " ms");

 /*
        System.out.println("Merge Sort");
        printArray(MergeArray);
        System.out.println("Quick Sort");
        printArray(QuickArray);
        System.out.println("Timsort");
        printArray(Timsort);
        System.out.println("Treap sort");
        System.out.println(treap.toString());
        System.out.println("PQ sort");
        System.out.println(pq.toString());

  */



    }

    private static double average(double[] times) {
        double sum = 0;

        if (times.length == 1) {
            sum = times[0];
        }
        else { // ignore first run because it is always much slower than the rest.
            for (int i = 1; i < times.length; i++) {
                sum += times[i];
            }
        }

        return sum / times.length;
    }


    private static void printArray(Integer[] arr) {
        for (int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println();
    }

    private static void printArray(List<Integer> arr) {
        for (int num : arr) {
            System.out.print(num + " ");
        }
        System.out.println();
    }

    private static void swapTwo(Integer[] unsortedArray) {
        int a = (int) (Math.random() * unsortedArray.length);
        int b = (int) (Math.random() * unsortedArray.length);
        int temp = unsortedArray[a];
        unsortedArray[a] = unsortedArray[b];
        unsortedArray[b] = temp;


    }
}

