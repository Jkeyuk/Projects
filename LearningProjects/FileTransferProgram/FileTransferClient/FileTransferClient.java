package filetransferclient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

final class FileTransferClient {

    private final String SERVER_ADDRESS;
    private final int SERVER_PORT;
    private final ExecutorService EXEC = Executors.newFixedThreadPool(2);
    private final Scanner SCANNER = new Scanner(System.in);
    private final Socket SOCKET = new Socket();
    private DataOutputStream output;
    private DataInputStream input;

    public FileTransferClient(String SERVER_ADDRESS, int SERVER_PORT) {
        this.SERVER_ADDRESS = SERVER_ADDRESS;
        this.SERVER_PORT = SERVER_PORT;
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("Enter ip of server");
        String address = s.nextLine().trim();
        System.out.println("Enter port");
        int port = s.nextInt();
        FileTransferClient client = new FileTransferClient(address, port);
        client.connect();
    }

    private void connect() {
        try {
            SOCKET.connect(new InetSocketAddress(SERVER_ADDRESS, SERVER_PORT));
            output = new DataOutputStream(SOCKET.getOutputStream());
            input = new DataInputStream(SOCKET.getInputStream());
            System.out.println("Connected To Server!!!");
            startListening();
            startTalking();
        } catch (IOException ex) {
            Logger.getLogger(FileTransferClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void startTalking() {
        Runnable talk = () -> {
            while (true) {
                checkUserInput();
            }
        };
        EXEC.execute(talk);
    }

    private void startListening() {
        Runnable listen = () -> {
            try {
                String incomming;
                while (true) {
                    while ((incomming = input.readUTF()) != null) {
                        if (incomming.length() <= 0) {
                            break;
                        } else if (incomming.startsWith("!sendingFile")) {
                            recieveFile(incomming);
                        } else {
                            System.out.println(incomming);
                        }
                    }
                }
            } catch (IOException ex) {
                System.out.println("server shutdown...");
                System.exit(0);
            }
        };
        EXEC.execute(listen);
    }

    private void recieveFile(String message) {
        String[] array = message.split(" ");
        if (array.length > 1) {
            File file = new File(array[1]);
            try (FileOutputStream fos = new FileOutputStream(file)) {
                file.createNewFile();
                int read;
                byte[] buff = new byte[1024];
                long numOfBytes = input.readLong();
                while (numOfBytes > 0) {
                    read = input.read(buff);
                    numOfBytes -= read;
                    fos.write(buff, 0, read);
                }
                System.out.println("File Recieved");
            } catch (IOException ex) {
                Logger.getLogger(FileTransferClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void handleUpload(String message) {
        String[] array = message.split(" ");
        if (array.length > 1) {
            File file = new File(array[1]);
            if (file.isFile()) {
                sendMessage("!uploading " + file.getName());
                sendFile(file);
            } else {
                System.out.println("this is not a proper file, check pathname and try again");
            }
        } else {
            System.out.println("you did not specify which file to send");
        }
    }

    private void sendFile(File file) {
        try (FileInputStream fis = new FileInputStream(file)) {
            output.writeLong(file.length());
            int read;
            byte[] buff = new byte[1024];
            while ((read = fis.read(buff)) > 0) {
                output.write(buff, 0, read);
            }
            output.flush();
            System.out.println("File Sent");
        } catch (IOException ex) {
            Logger.getLogger(FileTransferClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void checkUserInput() {
        String message = SCANNER.nextLine();
        if (message.equalsIgnoreCase("!shutdown")) {
            System.exit(0);
        } else if (message.startsWith("!fileUpload")) {
            handleUpload(message);
        } else {
            sendMessage(message);
        }
    }

    private void sendMessage(String m) {
        try {
            output.writeUTF(m);
            output.flush();
        } catch (IOException ex) {
            Logger.getLogger(FileTransferClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
