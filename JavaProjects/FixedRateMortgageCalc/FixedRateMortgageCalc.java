package FixedRateMortgageCalc;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;

public class FixedRateMortgageCalc {

	public static void main(String[] args) {
		double principal = getDouble(
				"Please enter the full principal amount, example: 200000");
		double iRate = getDouble(
				"Please enter the fixed yearly nominal interest rate, example: 6.5");
		double loanTerm = getDouble(
				"Please enter how many years the loans term will last");
		double monthlyP = fixedRateMortgageCalc(iRate, principal, loanTerm);
		System.out.println("The monthly payments for your loan will be $" + monthlyP);
	}

	private static double getDouble(String m) {
		String i;
		Scanner s = new Scanner(System.in);
		do {
			System.out.println("");
			System.out.println(m);
			i = s.nextLine().trim();
		} while (!checkDouble(i));
		double d = Double.parseDouble(i);
		s.close();
		return d;
	}

	private static boolean checkDouble(String x) {
		try {
			Double.parseDouble(x);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	private static double fixedRateMortgageCalc(double interestRate, double principal,
			double loanTerm) {
		double iRate = (interestRate / 100) / 12;
		double nPayments = loanTerm * 12;
		double p = principal;
		double c = (iRate / (1 - Math.pow(1 + iRate, -nPayments))) * p;

		BigDecimal bd = new BigDecimal(c);
		bd = bd.setScale(2, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
}
