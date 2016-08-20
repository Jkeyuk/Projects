package SandBox;

import java.util.ArrayList;
import java.util.List;

public class CodingBatChallenges {

    public static void main(String[] args) {

    }

    public boolean scoresIncreasing(int[] scores) {
        for (int i = 0; i < scores.length - 1; i++) {
            if (scores[i] > scores[i + 1]) {
                return false;
            }
        }
        return true;
    }

    public boolean scores100(int[] scores) {
        for (int i = 0; i < scores.length - 1; i++) {
            if (scores[i] == 100 && scores[i] == scores[i + 1]) {
                return true;
            }
        }
        return false;
    }

    public boolean scoresClump(int[] scores) {
        for (int i = 0; i < scores.length - 2; i++) {
            if ((scores[i + 2] - scores[i]) <= 2) {
                return true;
            }
        }
        return false;
    }

    public int scoresAverage(int[] scores) {
        int mid = scores.length / 2;
        int firstAverage = average(scores, 0, mid - 1);
        int secondAverage = average(scores, mid, scores.length - 1);
        if (firstAverage > secondAverage) {
            return firstAverage;
        } else {
            return secondAverage;
        }
    }

    //helper function for scoresAverage
    public int average(int[] scores, int start, int end) {
        int ave = 0;
        for (int i = start; i <= end; i++) {
            ave += scores[i];
        }
        ave = ave / ((end - start) + 1);
        return ave;
    }

    public int wordsCount(String[] words, int len) {
        int counter = 0;
        for (String word : words) {
            if (word.length() == len) {
                counter++;
            }
        }
        return counter;
    }

    public String[] wordsFront(String[] words, int n) {
        String[] newArray = new String[n];
        for (int i = 0; i < n; i++) {
            newArray[i] = words[i];
        }
        return newArray;
    }

    public List wordsWithoutList(String[] words, int len) {
        ArrayList<String> newList = new ArrayList<>();
        for (String word : words) {
            if (word.length() != len) {
                newList.add(word);
            }
        }
        return newList;
    }

    public boolean hasOne(int n) {
        do {
            int x = n % 10;
            if (x == 1) {
                return true;
            } else {
                n = n / 10;
            }
        } while (n != 0);
        return false;
    }

    public boolean dividesSelf(int n) {
        int y = n;
        do {
            int x = y % 10;
            if (x == 0 || n % x != 0) {
                return false;
            } else {
                y = y / 10;
            }
        } while (y != 0);
        return true;
    }

    public int[] copyEvens(int[] nums, int count) {
        ArrayList<Integer> x = new ArrayList<>();
        int[] arr = new int[count];
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] % 2 == 0) {
                x.add(nums[i]);
            }
        }
        for (int i = 0; i < count; i++) {
            int y = x.get(i);
            arr[i] = y;
        }
        return arr;
    }

    public int[] copyEndy(int[] nums, int count) {
        ArrayList<Integer> x = new ArrayList<>();
        int[] arr = new int[count];
        for (int i = 0; i < nums.length; i++) {
            if (isEndy(nums[i])) {
                x.add(nums[i]);
            }
        }
        for (int i = 0; i < count; i++) {
            arr[i] = x.get(i);
        }
        return arr;
    }

    //helper function for copyEndy
    public boolean isEndy(int x) {
        return x >= 0 && x <= 10 || x >= 90 && x <= 100;
    }

    public int matchUp(String[] a, String[] b) {
        int counter = 0;
        for (int i = 0; i < a.length; i++) {
            if (a[i].length() > 0 && b[i].length() > 0
                    && a[i].charAt(0) == b[i].charAt(0)) {
                counter++;
            }
        }
        return counter;
    }

    public int scoreUp(String[] key, String[] answers) {
        int score = 0;
        for (int i = 0; i < key.length; i++) {
            if (key[i].equals(answers[i])) {
                score = score + 4;
            } else if (answers[i].equals("?")) {
            } else {
                score = score - 1;
            }
        }
        return score;
    }

    public String[] wordsWithout(String[] words, String target) {
        ArrayList<String> x = new ArrayList<>();
        for (String word : words) {
            if (!word.equals(target)) {
                x.add(word);
            }
        }
        String[] y = new String[x.size()];
        for (int i = 0; i < x.size(); i++) {
            y[i] = x.get(i);
        }
        return y;
    }

    public int scoresSpecial(int[] a, int[] b) {
        int aTest = 0;
        int bTest = 0;
        for (int num : a) {
            if (num % 10 == 0 && num > aTest) {
                aTest = num;
            }
        }
        for (int num : b) {
            if (num % 10 == 0 && num > bTest) {
                bTest = num;
            }
        }
        return aTest + bTest;
    }

    public int sumHeights(int[] heights, int start, int end) {
        int sum = 0;
        int temp;
        for (int i = start; i <= end - 1; i++) {
            temp = heights[i] - heights[i + 1];
            temp = temp * temp;
            temp = (int) Math.sqrt(temp);
            sum = sum + temp;
        }
        return sum;
    }

    public int sumHeights2(int[] heights, int start, int end) {
        int sum = 0;
        int temp;
        for (int i = start; i <= end - 1; i++) {
            temp = heights[i] - heights[i + 1];
            if (temp < 0) {
                temp = temp * 2;
            }
            temp = temp * temp;
            temp = (int) Math.sqrt(temp);
            sum = sum + temp;
        }
        return sum;
    }

    public int bigHeights(int[] heights, int start, int end) {
        int count = 0;
        int temp;
        for (int i = start; i <= end - 1; i++) {
            temp = heights[i] - heights[i + 1];
            if (temp >= 5 || temp <= -5) {
                count++;
            }
        }
        return count;
    }
    
    

}
