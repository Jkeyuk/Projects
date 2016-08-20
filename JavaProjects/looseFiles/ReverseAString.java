package sandbox;

import java.util.Scanner;

public class ReverseAString {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter a string to reverse");
        String input = scan.nextLine();
        System.out.println("Reversing string now...");
        System.out.println(reverseString(input));
    }

    private static String reverseString(String in) {
        String returnString = "";
        char[] charArray = in.toCharArray();
        for (int i = charArray.length - 1; i >= 0; i--) {
            returnString += charArray[i];
        }
        return returnString;
    }
}
