package chatserver;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChatConnection implements Runnable {

    private final Socket CLIENT_SOCKET;
    private final BufferedReader IN;
    private final ConcurrentHashMap<String, Socket> CLIENT_LIST;
    private String userName;

    public ChatConnection(Socket clientSocket, ConcurrentHashMap<String, Socket> clientList)
            throws IOException {
        this.CLIENT_SOCKET = clientSocket;
        this.IN = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        this.CLIENT_LIST = clientList;
    }

    @Override
    public void run() {
        try {
            System.out.println("Connection made to " + CLIENT_SOCKET.getInetAddress().toString());
            getUserID();
            startListening();
        } catch (IOException | InterruptedException ex) {
            System.out.println(CLIENT_SOCKET.getInetAddress().toString() + " has disconnected");
            if (CLIENT_LIST.containsValue(CLIENT_SOCKET)) {
                CLIENT_LIST.remove(userName);
                broadcastMessage(userName + " Has left the chat room");
            }
        }
    }

    private void getUserID() throws IOException {
        userName = IN.readLine();
        CLIENT_LIST.put(userName, CLIENT_SOCKET);
        broadcastMessage(userName + " Has entered the chat room");
    }

    @SuppressWarnings("SleepWhileInLoop")
    private void startListening() throws IOException, InterruptedException {
        while (true) {
            String input;
            while ((input = IN.readLine()).length() != 0) {
                broadcastMessage(userName + ": " + input);
            }
            Thread.sleep(1000);
        }
    }

    private void broadcastMessage(String s) {
        for (Map.Entry<String, Socket> entry : CLIENT_LIST.entrySet()) {
            try {
                Socket sock = entry.getValue();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
                writer.write(s);
                writer.newLine();
                writer.flush();
                writer.write("");
                writer.newLine();
                writer.flush();
            } catch (IOException ex) {
                Logger.getLogger(ChatConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
