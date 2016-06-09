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

    private final Socket SOCKET;
    private final String SERVER_ADDRESS;
    private final int SERVER_PORT;
    private final Executor exec = Executors.newFixedThreadPool(3);

    public ChatClient(String address, int port) {
        this.SERVER_ADDRESS = address;
        this.SERVER_PORT = port;
        this.SOCKET = new Socket();
    }

    public void connect() {
        try {
            SOCKET.connect(new InetSocketAddress(SERVER_ADDRESS, SERVER_PORT));
            System.out.println("You are in the chat room, you can start chatting!");
            System.out.println("To shutdown program type in: !shutdown");
            startListening();
            startTalking();
        } catch (IOException ex) {
            Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        exec.execute(listen);
    }

    private void startTalking() {
        Runnable talk = () -> {
            try {
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(SOCKET.getOutputStream()));
                Scanner s = new Scanner(System.in);
                while (true) {
                    String input = s.nextLine();
                    checkInput(input);
                    writer.write(input);
                    writer.newLine();
                    writer.flush();
                    writer.write("");
                    writer.newLine();
                    writer.flush();
                }
            } catch (IOException ex) {
                Logger.getLogger(ChatClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        };
        exec.execute(talk);
    }
    
    private void checkInput(String s){
        if (s.equals("!shutdown")) {
            System.out.println("Program shutting down");
            System.exit(0);
        }
    }
}
