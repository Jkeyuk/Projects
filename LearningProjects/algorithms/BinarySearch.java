package sandbox;

public class BinarySearch {

    public static void main(String[] args) {
        int[] x = {1, 2, 3, 4,5,6,7,8};
        int indexAt = binarySearch(x, 6);
        System.out.println("value at index " + indexAt);
    }

    private static int binarySearch(int[] x, int searchFor) {
        int lowIndex = 0;
        int highIndex = (x.length) - 1;
        int itemIndex = -1;
        while (true) {
            if (highIndex < lowIndex) {
                break;
            }
            int mid = lowIndex + ((highIndex - lowIndex) / 2);
            if (x[mid] < searchFor) {
                lowIndex = mid + 1;
            }
            if (x[mid] > searchFor) {
                highIndex = mid - 1;
            }
            if (x[mid] == searchFor) {
                itemIndex = mid;
                return itemIndex;
            }
        }
        return itemIndex;
    }

}
