package scannerIO;

import java.util.ArrayList;

/**
 * 
 * @author jonathan
 * 
 *         Unit tests for scannerIO methods.
 *
 */
class UnitTests {

	public static void main(String[] args) {
		ArrayList<Boolean> results = new ArrayList<>();

		// scannerIO.In.isDouble tests
		boolean isDoubleTestResult = isDoubleTests();
		results.add(isDoubleTestResult);

		// scannerIO.In.isInt tests
		boolean isIntTestResult = isIntTests();
		results.add(isIntTestResult);

		// scannerIO.In.isIP tests
		boolean isIPTestResult = isIPTests();
		results.add(isIPTestResult);
		
		if (results.contains(false)) {
			System.out.println("FAILED TESTS");
		} else {
			System.out.println("ALL TESTS PASSED");
		}
	}

	private static boolean isDoubleTests() {
		// should return true
		if (scannerIO.In.isDouble("0") == false) {
			return false;
		}
		if (scannerIO.In.isDouble("4d") == false) {
			return false;
		}
		if (scannerIO.In.isDouble("3.6") == false) {
			return false;
		}
		if (scannerIO.In.isDouble("3.65655") == false) {
			return false;
		}
		// should return false
		if (scannerIO.In.isDouble("") == true) {
			return false;
		}
		if (scannerIO.In.isDouble("zfgdgf") == true) {
			return false;
		}
		if (scannerIO.In.isDouble("5-43-5534") == true) {
			return false;
		}
		if (scannerIO.In.isDouble("5.445.4455") == true) {
			return false;
		}
		return true;
	}

	private static boolean isIntTests() {
		// should return true
		if (scannerIO.In.isInt("0") == false) {
			return false;
		}
		if (scannerIO.In.isInt("1223") == false) {
			return false;
		}
		if (scannerIO.In.isInt("-1223") == false) {
			return false;
		}
		// should return false
		if (scannerIO.In.isInt("3.2") == true) {
			return false;
		}
		if (scannerIO.In.isInt("four") == true) {
			return false;
		}
		if (scannerIO.In.isInt("4-5") == true) {
			return false;
		}
		return true;
	}

	private static boolean isIPTests() {
		// should return true
		if (scannerIO.In.isIP("0.0.0.0") == false) {
			return false;
		}
		if (scannerIO.In.isIP("127.0.0.1") == false) {
			return false;
		}
		if (scannerIO.In.isIP("255.255.255.255") == false) {
			return false;
		}
		// should return false
		if (scannerIO.In.isIP("") == true) {
			return false;
		}
		if (scannerIO.In.isIP("dfsdf") == true) {
			return false;
		}
		if (scannerIO.In.isIP("256.55.43.2") == true) {
			return false;
		}
		if (scannerIO.In.isIP("44.256.4.4") == true) {
			return false;
		}
		if (scannerIO.In.isIP("44.44.256.5") == true) {
			return false;
		}
		if (scannerIO.In.isIP("4.5.4.256") == true) {
			return false;
		}
		if (scannerIO.In.isIP("127.0.0.1.56") == true) {
			return false;
		}
		return true;
	}
}
