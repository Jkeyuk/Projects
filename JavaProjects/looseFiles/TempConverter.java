package sandbox;

import java.util.Scanner;

public class TempConverter {

    private double celsiusToFar(double temp) {
        return ((temp * 9) / 5) + 32;
    }

    private double farToCelsius(double temp) {
        return ((temp - 32) * 5) / 9;
    }

    public static void main(String[] args) {
        TempConverter converter = new TempConverter();
        Scanner s = new Scanner(System.in);
        System.out.println("Please enter your choice number");
        System.out.println("1) convert fahrenheit to celsius");
        System.out.println("2) convert celsius to fahrenheit");
        int choice = s.nextInt();
        System.out.println("Now please enter the temp to convert");
        double temp = s.nextDouble();
        if (choice == 1) {
            System.out.println("The temp in celsius is: " + converter.farToCelsius(temp));
        } else if (choice == 2) {
            System.out.println("The temp in fahrenheit is: " + converter.celsiusToFar(temp));
        }
    }

}
