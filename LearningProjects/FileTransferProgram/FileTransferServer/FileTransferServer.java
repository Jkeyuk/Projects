package filetransferserver;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

final class FileTransferServer {

    private final ExecutorService EXEC = Executors.newFixedThreadPool(100);
    private final Scanner SCAN = new Scanner(System.in);
    private ServerSocket serverSocket;
    private String workingDirectory;

    public static void main(String[] args) {
        FileTransferServer server = new FileTransferServer();
        server.setWorkingDirectory();
        server.startServer();
        server.listenForShutdown();
    }

    private void setWorkingDirectory() {
        String input;
        do {
            System.out.println("Plese enter the working directory for the server");
            input = SCAN.nextLine().trim();
        } while (!new File(input).isDirectory());
        workingDirectory = input;
    }

    private void startServer() {
        Runnable serverStart = () -> {
            try {
                serverSocket = new ServerSocket(0);
                System.out.println("Server started on port: " + serverSocket.getLocalPort());
                while (true) {
                    Socket clientSock = serverSocket.accept();
                    EXEC.execute(new Connection(clientSock, workingDirectory));
                }
            } catch (IOException ex) {
                Logger.getLogger(FileTransferServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        };
        EXEC.execute(serverStart);
    }

    private void listenForShutdown() {
        Runnable shutdownListener = () -> {
            System.out.println("To shutdown server type in: !shutdown");
            while (true) {
                String input = SCAN.nextLine();
                if (input.equalsIgnoreCase("!shutdown")) {
                    System.exit(0);
                }
            }
        };
        EXEC.execute(shutdownListener);
    }
}
