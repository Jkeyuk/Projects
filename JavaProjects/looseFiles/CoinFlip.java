package sandbox;

public class CoinFlip {

    public static void main(String[] args) {
        int heads = 0;
        int tails = 0;
        for (int i = 0; i < 10; i++) {
            double x = Math.random() * 10;
            if (x > 5) {
                System.out.println("heads");
                heads++;
            } else {
                System.out.println("tails");
                tails++;
            }
        }
        System.out.println("Number of Heads = " + heads);
        System.out.println("Number of Tails = " + tails);
    }
}
