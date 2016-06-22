package filetransferserver;

/**
 * Connection object represents a connection to the server, First it initializes
 * the connection by opening the proper input and output streams. Then
 * connection listens over the socket for requests from the client. Each request
 * is handed to the Request Handler.
 */
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

    //connection is built with a socket to the client and a working directory.
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

    //opens input and output streams to the client.
    private void initializeConnection() {
        try {
            input = new DataInputStream(CLIENT_SOCKET.getInputStream());
            output = new DataOutputStream(CLIENT_SOCKET.getOutputStream());
        } catch (IOException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //listens over socket for requests from client, each request sent to handler
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

    /**
     * Request Handler object handles requests from the clients
     */
    private final class RequestHandler {

        /**
         * handles requests by calling appropriate methods in response
         *
         * @param request- The request sent from the client as a string
         */
        private void handleRequest(String request) {
            if (request.equalsIgnoreCase("!showFiles")) {
                File[] listOfFiles = WORKING_DIRECTORY.listFiles();
                sendMessage("----------------------------------------");
                sendMessage("Files in the working directory:");
                displayFiles(listOfFiles);
            } else if (request.startsWith("!openFolder")) {
                prepareFolderToOpen(request);
            } else if (request.startsWith("!delete")) {
                prepareDeletion(request);
            } else if (request.startsWith("!sendFile")) {
                handleFileRequest(request);
            } else if (request.startsWith("!uploading")) {
                recieveFile(request);
            } else {
                sendMessage("Not a valid command");
            }
        }

        /**
         * Sends client the name of each file in a list of files.
         *
         * @param listOfFiles - list of files to display.
         */
        private void displayFiles(File[] listOfFiles) {
            for (File file : listOfFiles) {
                if (file.isDirectory()) {
                    sendMessage(file.getName() + "(Folder)");
                } else {
                    sendMessage(file.getName());
                }
            }
        }

        /**
         * Prepares the list of files requested from client, then calls the
         * display files method for the list.
         *
         * @param request - request from client of which folder to view
         */
        private void prepareFolderToOpen(String request) {
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

        /**
         * Prepares the files to delete requested from the user
         *
         * @param request - request from client of which files to delete
         */
        private void prepareDeletion(String request) {
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

        /**
         * recursively removes files and folders from working directory
         *
         * @param file - file or folder to delete
         */
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

        /**
         * Prepares the file requested from the client to send over network
         *
         * @param request - the request of which file the client wants
         */
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

        /**
         * Sends a file over the network to the client
         *
         * @param file - the file to be sent
         */
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

        /**
         * Receives a file from the client
         *
         * @param message - upload message with name of the file
         */
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

        /**
         * Sends a string over network to the client
         *
         * @param m - message to send to client
         */
        private void sendMessage(String m) {
            try {
                output.writeUTF(m);
                output.flush();
            } catch (IOException ex) {
                Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        /**
         * checks to make sure the request does not escape working directory.
         *
         * @param r - request sent by client
         * @return - if the request is valid and stays in working directory
         */
        private boolean isRequestValid(String r) {
            return !(r.contains("/..") || r.contains("\\.."));
        }
    }
}
