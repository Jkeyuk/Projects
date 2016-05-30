package sandbox;

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
