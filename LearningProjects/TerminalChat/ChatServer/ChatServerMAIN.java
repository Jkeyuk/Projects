package chatserver;

import java.util.Scanner;

public class ChatServerMAIN {

    public static void main(String[] args) {
        startServer();
        listenForShutdown();
    }

    private static void startServer() {
        Runnable serverStart = () -> {
            ChatServer cSer = new ChatServer();
            cSer.startServer();
        };
        new Thread(serverStart).start();
    }

    private static void listenForShutdown() {
        System.out.println("To shutdown server type in: !shutdown");
        Runnable waitForShutdown = () -> {
            Scanner s = new Scanner(System.in);
            while (true) {
                String input = s.nextLine();
                if (input.equals("!shutdown")) {
                    System.exit(0);
                }
            }
        };
        new Thread(waitForShutdown).start();
    }
}
