package PortScanner;

import java.util.regex.Pattern;

public class PortScannerMain {

	public static void main(String[] args) {
		if (args.length != 3 || !isIP(args[0]) || !isInt(args[1]) || !isInt(args[2])) {
			System.out.println("Proper Usage:");
			System.out.println(
					"c:\\java PortScannerMain [IP] [Starting port] [Ending port]");
			System.out.println("Example:");
			System.out.println("c:\\jave PortScannerMain 127.0.0.1 70 100");
			System.exit(1);
		} else {
			PortScanner scanner = new PortScanner(args[0], Integer.parseInt(args[1]),
					Integer.parseInt(args[2]));
			System.out.println("Scanning...");
			scanner.run();
			System.out.println("Finished");
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
		String ipExpression = zeroTo255 + "\\." + zeroTo255 + "\\." + zeroTo255 + "\\."
				+ zeroTo255;
		return Pattern.matches(ipExpression, ip);
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
}
