package PortScanner;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PortScannerMain {

	public static void main(String[] args) {
		if (args.length != 3 || !checkIP(args[0]) || !checkInt(args[1]) || !checkInt(args[2])) {
			System.out.println("Proper Usage:");
			System.out.println("c:\\java PortScannerMain [IP] [Starting port] [Ending port]");
			System.out.println("Example:");
			System.out.println("c:\\jave PortScannerMain 127.0.0.1 70 100");
			System.exit(1);
		} else {
			PortScanner scanner = new PortScanner(args[0], Integer.parseInt(args[1]), Integer.parseInt(args[2]));
			System.out.println("Scanning...");
			scanner.run();
			System.out.println("Finished");
		}
	}

	private static boolean checkIP(String s) {
		Pattern p = Pattern.compile("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}");
		Matcher m = p.matcher(s);
		return m.matches();
	}

	private static boolean checkInt(String i) {
		try {
			Integer.parseInt(i);
			return true;
		} catch (NumberFormatException ex) {
			return false;
		}
	}
}
