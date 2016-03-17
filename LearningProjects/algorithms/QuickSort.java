package sandbox;

import java.util.Arrays;

public class QuickSort {

    public static void main(String[] args) {
        int[] test = {9,7,6,4,3};
        quickSort(test);
        System.out.println(Arrays.toString(test));
    }

    private static void quickSort(int[] x) {
        int left = 0;
        int right = (x.length) - 1;
        sort(x, left, right);

    }

    private static void sort(int[] x, int left, int right) {
        if (right - left > 0) {
            int pivot = x[right];
            int partition = partition(x, left, right, pivot);
            sort(x, left, partition - 1);
            sort(x, partition + 1, right);
        }
    }

    private static int partition(int[] x, int left, int right, int pivot) {
        int leftPointer = left;
        int rightPointer = right - 1;

        while (true) {
            while (x[leftPointer] < pivot) {
                leftPointer++;
            }
            while (rightPointer > 0 && x[rightPointer] > pivot) {
                rightPointer--;
            }
            if (leftPointer >= rightPointer) {
                break;
            } else {
                swap(x, leftPointer, rightPointer);
            }
        }
        swap(x, leftPointer, right);
        return leftPointer;
    }

    private static void swap(int[] x, int swap, int swapWith) {
        int temp = x[swap];
        x[swap] = x[swapWith];
        x[swapWith] = temp;
    }
}
