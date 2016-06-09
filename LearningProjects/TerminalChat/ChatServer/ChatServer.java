package chatserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChatServer {

    private final Executor EXEC = Executors.newFixedThreadPool(100);
    private final ConcurrentHashMap<String, Socket> CLIENT_LIST = new ConcurrentHashMap<>();

    public void startServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(0);
            System.out.println("Server started on port: " + serverSocket.getLocalPort());
            while (true) {
                Socket clientSock = serverSocket.accept();
                EXEC.execute(new ChatConnection(clientSock, CLIENT_LIST));
            }
        } catch (IOException ex) {
            Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
