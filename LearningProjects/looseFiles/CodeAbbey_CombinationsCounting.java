package sandbox;
/*
So here we have an example of Combinations, 
different ways of choosing several elements from the given set (not regarding the order).
For example, if the boy have 4 candies (of different kinds) and should take only 2 of them, 
leaving others to his younger sister, he have the following variants:

A B C D - four sorts of candies

A+B, A+C, A+D, B+C, B+D, C+D - six way to choose a pair of them.
How many combinations of K elements from the set of N exist (assuming all N elements are different)
*/
public class CodeAbbey_CombinationsCounting {

    public static void main(String[] args) {
        System.out.println("number of combinations: " + countCombos(0, 3));
        System.out.println("number of combinations: " + countCombos(2, 4));
        System.out.println("number of combinations: " + countCombos(2, 5));
    }

    private static long countCombos(long setSize, long NumOfElements) {
        long x = factorial(NumOfElements) / (factorial(setSize) * factorial(NumOfElements - setSize));
        return x;
    }

    private static long factorial(long num) {
        long x = 1;
        for (int i = 1; i <= num; i++) {
            x *= i;
        }
        return x;
    }
}
