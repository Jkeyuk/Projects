package passwordgenerator;

import java.util.Scanner;

public class PassWordGenerator {

    private final String[] characters = {"a", "b", "c", "d", "e", "f",
        "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
        "t", "u", "v", "w", "x", "y", "z"};
    private final String[] specialChars = {"!", "@", "#", "$", "%"};
    private final int passLength;

    public PassWordGenerator(int passLength) {
        this.passLength = passLength;
    }

    public String generatePassword() {
        String password = "";
        for (int i = 0; i < passLength; i++) {
            int randomCharIndex = randomGen(26);
            if (i == 0 || i == 1) {
                password += characters[randomCharIndex].toUpperCase();
            } else if (i == passLength - 1) {
                int randomSpecialIndex = randomGen(5);
                password += specialChars[randomSpecialIndex];
            } else {
                password += characters[randomCharIndex];
            }
        }
        return password;
    }

    private int randomGen(int upperLimit) {
        int y = (int) (Math.random() * upperLimit);
        return y;
    }

    private static boolean checkInt(String x) {
        try {
            int y = Integer.parseInt(x);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void main(String[] args) {
        String userInput;
        Scanner s = new Scanner(System.in);
        do {
            System.out.println("how long would you like your password to be");
            userInput = s.nextLine().trim();
        } while (!checkInt(userInput));
        int input = Integer.parseInt(userInput);
        PassWordGenerator generator = new PassWordGenerator(input);
        System.out.println("Your new random password is: " + generator.generatePassword());
    }
}
