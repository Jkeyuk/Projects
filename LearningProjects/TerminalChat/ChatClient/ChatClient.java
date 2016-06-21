package chatclient;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChatClient {

    private final String SERVER_ADDRESS;
    private final int SERVER_PORT;
    private final Executor EXEC = Executors.newFixedThreadPool(3);
    private final Scanner SCANNER = new Scanner(System.in);
    private final Socket SOCKET = new Socket();
    private BufferedWriter writer;

    public ChatClient(String address, int port) {
        this.SERVER_ADDRESS = address;
        this.SERVER_PORT = port;
    }

    public void connect() {
        try {
            SOCKET.connect(new InetSocketAddress(SERVER_ADDRESS, SERVER_PORT));
            writer = new BufferedWriter(new OutputStreamWriter(SOCKET.getOutputStream()));
            initializeUserID();
            startListening();
            startTalking();
        } catch (IOException ex) {
            Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initializeUserID() {
        System.out.println("");
        System.out.println("*****INSTRUCTIONS*****");
        System.out.println("at any time, to shutdown program type in: !shutdown");
        System.out.println("Enter your user name now!!!!!:");
        sendMessage();
        System.out.println("Welcome to the chat room");
        System.out.println("Start Chatting Now");
    }

    @SuppressWarnings("SleepWhileInLoop")
    private void startListening() {
        Runnable listen = () -> {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(SOCKET.getInputStream()));
                String input;
                while (true) {
                    while ((input = reader.readLine()).length() != 0) {
                        System.out.println(input);
                    }
                    Thread.sleep(1000);
                }
            } catch (IOException | InterruptedException ex) {
                System.out.println("Server has shutdown");
                System.exit(0);
            }
        };
        EXEC.execute(listen);
    }

    private void startTalking() {
        Runnable talk = () -> {
            while (true) {
                sendMessage();
            }
        };
        EXEC.execute(talk);
    }

    private void sendMessage() {
        try {
            String input = SCANNER.nextLine();
            checkInput(input);
            writer.write(input);
            writer.newLine();
            writer.flush();
            writer.write("");
            writer.newLine();
            writer.flush();
        } catch (IOException ex) {
            Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void checkInput(String s) {
        if (s.equals("!shutdown")) {
            System.out.println("Program shutting down");
            System.exit(0);
        }
    }
}