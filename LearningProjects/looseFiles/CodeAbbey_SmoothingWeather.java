package sandbox;
/*
Observing this, Merlin decided to make his data more smooth. 
To achieve this he only needs every value to be substituted by the average of it and its two neighbors. 
For example, if he have the sequence of 5 values like this:

3 5 6 4 5
Then the second (i.e. 5) should be substituted by (3 + 5 + 6) / 3 = 4.66666666667,
the third (i.e. 6) should be substituted by (5 + 6 + 4) / 3 = 5,
the fourth (i.e. 4) should be substituted by (6 + 4 + 5) / 3 = 5.
By agreement, the first and the last values will remain unchanged.
*/
public class CodeAbbey_SmoothingWeather {

    public static void main(String[] args) {

        double[] x = {32.6, 31.2, 35.2, 37.4, 44.9, 42.1, 44.1};
        smoothingTheWeather(x);
    }

    private static void smoothingTheWeather(double[] array) {
        for (int i = 0; i < array.length; i++) {
            if (i > 0 && i < array.length - 1) {
                double avg = (array[i - 1] + array[i] + array[i + 1]) / 3;
                System.out.print(avg + " ");
            } else {
                System.out.print(array[i] + " ");
            }
        }
    }
}
