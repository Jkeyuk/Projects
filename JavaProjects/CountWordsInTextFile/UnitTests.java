package CountWordsInTextFile;

public class UnitTests {
	public static void main(String[] args) {
		boolean testResult = true;

		// countWordsInString tests
		if (FileWordCounter.countWordsInString("") != 0) {
			testResult = false;
		}
		if (FileWordCounter.countWordsInString("HelloWorld!") != 1) {
			testResult = false;
		}
		if (FileWordCounter.countWordsInString("Hello World!") != 2) {
			testResult = false;
		}
		if (FileWordCounter.countWordsInString("It's a boy") != 3) {
			testResult = false;
		}
		if (FileWordCounter.countWordsInString("232-222..11") != 3) {
			testResult = false;
		}
		if (FileWordCounter.countWordsInString("  This  string    has  5 words!!.") != 5) {
			testResult = false;
		}
		if (FileWordCounter.countWordsInString("An0th3r L337 T35T...lol!!!^&*HERY") != 5) {
			testResult = false;
		}

		// Results of test
		if (testResult) {
			System.out.println("PASSED!");
		} else {
			System.out.println("FAILED!");
		}
	}
}
