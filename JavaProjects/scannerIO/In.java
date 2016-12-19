package scannerIO;

import java.io.File;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * 
 * @author jonathan
 * 
 *         A set of static methods to receive and validate inputs from a user.
 *
 */
public class In {

	/**
	 * Prints a given message to System.out and uses a given Scanner to scan the
	 * next line of input. If scanned input is valid path to a existing
	 * directory, a file object representing the directory from this path is
	 * returned. Otherwise message keeps looping until user enters a valid
	 * directory path.
	 * 
	 * @param message
	 *            - string to display to user.
	 * @param scanner
	 *            - scanner to receive input from user.
	 * @return file object representing the directory is returned.
	 */
	public static File getDirectory(String message, Scanner scanner) {
		return new File(getStringIf(message, scanner, x -> new File(x).isDirectory()));
	}

	/**
	 * Prints a given message to System.out and uses a given Scanner to scan the
	 * next line of input. If the scanned line is a valid double, the double is
	 * returned. Otherwise the message will keep looping.
	 * 
	 * @param message
	 *            - string to display to user.
	 * @param scanner
	 *            - scanner to receive integer from user.
	 * @return inputed double returned
	 */
	public static double getDouble(String message, Scanner scanner) {
		return Double.parseDouble(getStringIf(message, scanner, x -> isDouble(x)));
	}

	/**
	 * Prints a given message to System.out and uses a given Scanner to scan the
	 * next line of input. If scanned input is valid file path to a existing
	 * file, a file object representing the file from this path is returned.
	 * Otherwise message keeps looping until user enters a valid file path.
	 * 
	 * @param message
	 *            - string to display to user.
	 * @param scanner
	 *            - scanner to receive input from user.
	 * @return file object representing the file is returned.
	 */
	public static File getFile(String message, Scanner scanner) {
		return new File(getStringIf(message, scanner, x -> new File(x).isFile()));
	}

	/**
	 * Prints a given message to System.out and uses a given Scanner to scan the
	 * next line of input. If the scanned line is a valid integer, the integer
	 * is returned. Otherwise the message will keep looping.
	 * 
	 * @param message
	 *            - string to display to user.
	 * @param scanner
	 *            - scanner to receive integer from user.
	 * @return inputed integer returned
	 */
	public static int getInt(String message, Scanner scanner) {
		return Integer.parseInt(getStringIf(message, scanner, x -> isInt(x)));
	}

	/**
	 * Prints a given message to System.out and uses a given Scanner to scan the
	 * next line of input. If the scanned line is a valid IP address, the string
	 * is returned. otherwise keeps looping with message.
	 * 
	 * @param message
	 *            - string to display to user.
	 * @param scanner
	 *            - scanner to receive input from user.
	 * @return valid IP address as string.
	 */
	public static String getIP(String message, Scanner scanner) {
		return getStringIf(message, scanner, x -> isIP(x));
	}

	/**
	 * prompts user with a given message and scans input for string. inputed
	 * string is returned.
	 * 
	 * @param message
	 *            - message to prompt to user
	 * @param scanner
	 *            - scanner to scan input
	 * @return - string inputed from user.
	 */
	public static String getString(String message, Scanner scanner) {
		System.out.println(message);
		return scanner.nextLine().trim();
	}

	/**
	 * Prints a given message to System.out until the user inputs a valid input
	 * which passes a given predicate.
	 * 
	 * @param message
	 *            - string to display to user.
	 * @param scanner
	 *            - scanner to receive input string from user.
	 * @param predicate
	 *            - method reference or lambda to consume the input string and
	 *            return true.
	 * @return the input string scanned by given scanner.
	 */
	public static String getStringIf(String message, Scanner scanner, Predicate<String> predicate) {
		String input;
		do {
			input = getString(message, scanner);
		} while (!predicate.test(input));
		return input;
	}

	/**
	 * Returns true if the given string can be parsed into a double, without
	 * throwing a number format exception
	 * 
	 * @param doublee
	 *            - string representing double to test
	 * @return Returns true if given string can be parsed into double.
	 */
	public static boolean isDouble(String doublee) {
		try {
			Double.parseDouble(doublee);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * Returns true if the given string can be parsed into a integer, without
	 * throwing a number format exception.
	 * 
	 * @param integer
	 *            - String representing integer to test.
	 * @return Returns true if the given string can be parsed into a integer.
	 */
	public static boolean isInt(String integer) {
		try {
			Integer.parseInt(integer);
			return true;
		} catch (NumberFormatException ex) {
			return false;
		}
	}

	/**
	 * Returns true if the given string consists of 4 numbers between 0 and 255
	 * that is separated by dots. example 127.0.0.1
	 * 
	 * @param ip
	 *            -string to test
	 * @return true if number is valid IPv4 address.
	 */
	public static boolean isIP(String ip) {
		String zeroTo255 = "([0-1]?[0-9]?[0-9]|2[0-4][0-9]|25[0-5])";
		String ipExpression = zeroTo255 + "\\." + zeroTo255 + "\\." + zeroTo255 + "\\." + zeroTo255;
		return Pattern.matches(ipExpression, ip);
	}
}
