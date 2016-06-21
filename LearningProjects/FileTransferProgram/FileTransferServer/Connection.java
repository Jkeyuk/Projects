package filetransferserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

final class Connection implements Runnable {

    private final Socket CLIENT_SOCKET;
    private final File WORKING_DIRECTORY;
    private final RequestHandler HANDLER = new RequestHandler();
    private DataInputStream input;
    private DataOutputStream output;

    public Connection(Socket client, String directory) {
        this.CLIENT_SOCKET = client;
        this.WORKING_DIRECTORY = new File(directory);
    }

    @Override
    public void run() {
        System.out.println("Connection made to " + CLIENT_SOCKET.getInetAddress().toString());
        initializeConnection();
        startListening();
    }

    private void initializeConnection() {
        try {
            input = new DataInputStream(CLIENT_SOCKET.getInputStream());
            output = new DataOutputStream(CLIENT_SOCKET.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void startListening() {
        try {
            while (true) {
                String incoming;
                while ((incoming = input.readUTF()) != null) {
                    HANDLER.handleRequest(incoming.trim());
                }
            }
        } catch (IOException ex) {
            System.out.println(CLIENT_SOCKET.getInetAddress().toString() + " has discconected");
        }
    }

    private final class RequestHandler {

        private synchronized void handleRequest(String request) {
            if (request.equalsIgnoreCase("!showFiles")) {
                File[] listOfFiles = WORKING_DIRECTORY.listFiles();
                sendMessage("----------------------------------------");
                sendMessage("Files in the working directory:");
                displayFiles(listOfFiles);
            } else if (request.startsWith("!openFolder")) {
                openFolder(request);
            } else if (request.startsWith("!delete")) {
                delete(request);
            } else if (request.startsWith("!sendFile")) {
                handleFileRequest(request);
            } else if (request.startsWith("!uploading")) {
                recieveFile(request);
            } else {
                sendMessage("Not a valid command");
            }
        }

        private void displayFiles(File[] listOfFiles) {
            for (File file : listOfFiles) {
                if (file.isDirectory()) {
                    sendMessage(file.getName() + "(Folder)");
                } else {
                    sendMessage(file.getName());
                }
            }
        }

        private void openFolder(String request) {
            String[] array = request.split(" ");
            if (array.length > 1) {
                File folder = new File(WORKING_DIRECTORY.getAbsolutePath()
                        + File.separator + array[1]);
                if (folder.isDirectory() && isRequestValid(array[1])) {
                    File[] fileList = folder.listFiles();
                    sendMessage("----------------------------------------");
                    sendMessage("Files in the Folder: " + array[1]);
                    displayFiles(fileList);
                } else {
                    sendMessage("That folder does not exsist, check folder name");
                }
            } else {
                sendMessage("You did not specify the folder to open");
            }
        }

        private void delete(String request) {
            String[] array = request.split(" ");
            if (array.length > 1) {
                File file = new File(WORKING_DIRECTORY.getAbsolutePath()
                        + File.separator + array[1]);
                if (file.exists() && isRequestValid(array[1])) {
                    removeFile(file);
                } else {
                    sendMessage("Cannot delete");
                }
            } else {
                sendMessage("You did not specify what to delete");
            }
        }

        private void removeFile(File file) {
            if (file.isDirectory()) {
                File[] list = file.listFiles();
                for (File f : list) {
                    removeFile(f);
                }
                file.delete();
            } else {
                file.delete();
            }
            sendMessage(file.getName() + " removed");
        }

        private void handleFileRequest(String request) {
            String[] array = request.split(" ");
            if (array.length > 1) {
                File file = new File(WORKING_DIRECTORY.getAbsolutePath()
                        + File.separator + array[1]);
                if (file.isFile() && isRequestValid(array[1])) {
                    sendMessage("!sendingFile " + file.getName());
                    sendFile(file);
                } else {
                    sendMessage("Check file name and try again");
                }
            } else {
                sendMessage("You did not specify what file to send");
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
            } catch (IOException ex) {
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        private void recieveFile(String message) {
            String[] array = message.split(" ");
            if (array.length > 1) {
                File file = new File(WORKING_DIRECTORY.getAbsolutePath()
                        + File.separator + array[1]);
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
                    Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        private void sendMessage(String m) {
            try {
                output.writeUTF(m);
                output.flush();
            } catch (IOException ex) {
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        private boolean isRequestValid(String r) {
            return !(r.contains("/..") || r.contains("\\.."));
        }
    }
}
