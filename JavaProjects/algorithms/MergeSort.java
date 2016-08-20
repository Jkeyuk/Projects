package sandbox;

import java.util.Arrays;

public class MergeSort {

    public static void main(String[] args) {
        int[] test = {9,8,7,6,5,4,3};
        mergeSort(test);
        System.out.println(Arrays.toString(test));
    }

    private static void mergeSort(int[] x) {
        int lowIndex = 0;
        int highIndex = (x.length) - 1;
        sort(x, lowIndex, highIndex);
    }

    private static void sort(int[] x, int lo, int high) {
        if (lo < high) {
            int midIndex = (lo + high) / 2;
            sort(x, lo, midIndex);
            sort(x, midIndex + 1, high);
            merge(x, lo, midIndex, high);
        }
    }

    private static void merge(int[] x, int lo, int mid, int high) {
        int[] temp = new int[x.length];
        int lowIndex;
        int midIndex;
        int i;
        for (i = lo, lowIndex = lo, midIndex = mid+1; lowIndex <= mid && midIndex <= high; i++) {
            if (x[lowIndex] <= x[midIndex]) {
                temp[i] = x[lowIndex];
                lowIndex++;
            } else {
                temp[i] = x[midIndex];
                midIndex++;
            }
        }
        while (lowIndex <= mid) {
            temp[i] = x[lowIndex];
            i++;
            lowIndex++;
        }
        while (midIndex <= high) {
            temp[i] = x[midIndex];
            i++;
            midIndex++;
        }
        for (i = lo; i <= high; i++) {
            x[i] = temp[i];
        }
    }
}
