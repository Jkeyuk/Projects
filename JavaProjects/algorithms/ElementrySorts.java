package sandbox;

public final class ElementrySorts {

    private ElementrySorts() {
    }

    /*takes the smallest number and places in the first index, second smallest
    ***to second index and so on*/
    public static void selectionSort(int[] x) {
        int arraySize = x.length;
        for (int j = 0; j < arraySize; j++) {
            int min = x[j];
            int mindex = j;
            for (int i = j + 1; i < arraySize; i++) {
                if (min > x[i]) {
                    min = x[i];
                    mindex = i;
                }
            }
            swap(x, j, mindex);
        }
    }

    /*implements insertion sort, sorting each index in its proper place  
      taking into account the indexes just sorted*/
    public static void insertionSort(int[] x) {
        int arraySize = x.length;
        for (int i = 0; i < arraySize; i++) {
            for (int j = i; j > 0; j--) {
                if (x[j] < x[j - 1]) {
                    swap(x, j, j - 1);
                }
            }
        }
    }

    /*implements shell sort*/
    public static void shellSort(int[] x) {
        int arraySize = x.length;
        int h = 1;
        while (h < (arraySize / 3)) {
            h = (3 * h) + 1;
        }
        while (h > 0) {
            for (int i = h; i < arraySize; i++) {
                for (int j = i; j >= h; j -= h) {
                    if (x[j] < x[j - h]) {
                        swap(x, j, j - h);
                    }
                }
            }
            h = (h - 1) / 3;
        }
    }

    //helper function to swap values between indexes
    //params(array of ints,index to swap,index to swap with)
    private static void swap(int[] x, int swap, int swapWith) {
        int temp = x[swap];
        x[swap] = x[swapWith];
        x[swapWith] = temp;
    }

}
