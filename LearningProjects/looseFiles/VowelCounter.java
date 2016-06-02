package sandbox;

import java.util.Scanner;

public class VowelCounter {

    private final String string;

    public VowelCounter(String string) {
        this.string = string;
    }

    public void countVowels() {
        int numOfVowels = 0;
        int numOfA = 0;
        int numOfE = 0;
        int numOfI = 0;
        int numOfO = 0;
        int numOfU = 0;
        for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            if (c == 'a' || c == 'A') {
                numOfVowels++;
                numOfA++;
            } else if (c == 'e' || c == 'E') {
                numOfVowels++;
                numOfE++;
            } else if (c == 'i' || c == 'I') {
                numOfVowels++;
                numOfI++;
            } else if (c == 'o' || c == 'O') {
                numOfVowels++;
                numOfO++;
            } else if (c == 'u' || c == 'U') {
                numOfVowels++;
                numOfU++;
            }
        }
        displayResults(numOfVowels, numOfA, numOfE, numOfI, numOfO, numOfU);
    }

    private void displayResults(int vowels, int a, int e, int i, int o, int u) {
        System.out.println("Total number of vowels: " + vowels);
        System.out.println("Total number of A's: " + a);
        System.out.println("Total number of E's: " + e);
        System.out.println("Total number of I's: " + i);
        System.out.println("Total number of O's: " + o);
        System.out.println("Total number of U's: " + u);
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("Please enter a string of text:");
        String input = s.nextLine();
        VowelCounter v = new VowelCounter(input);
        v.countVowels();
    }
}
