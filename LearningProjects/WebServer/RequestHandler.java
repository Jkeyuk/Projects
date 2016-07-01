package webserver;

/**
 * Request Handler object is responsible for handling the request from the
 * client, this class implements runnable and is run in its own thread.
 */
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

class RequestHandler implements Runnable {

    private final File WORKING_DIRECTORY;
    private final Socket CLIENT_SOCKET;
    private String requestMethod;
    private String requestedResource;

    /**
     * Request Handler object is given the servers working directory and the
     * socket the client is connected from.
     *
     * @param WORKING_DIRECTORY - directory to serve server files from
     * @param CLIENT_SOCKET - socket connecting to the client browser
     */
    RequestHandler(File WORKING_DIRECTORY, Socket CLIENT_SOCKET) {
        this.WORKING_DIRECTORY = WORKING_DIRECTORY;
        this.CLIENT_SOCKET = CLIENT_SOCKET;
    }

    @Override
    public void run() {
        try {
            recieveRequest();
            handleResponse();
            CLIENT_SOCKET.close();
        } catch (IOException ex) {
            Logger.getLogger(RequestHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * receive request method opens the input stream from the client socket and
     * gives the data to the parse request method.
     *
     * @throws IOException
     */
    private void recieveRequest() throws IOException {
        ArrayList<String> requestData = new ArrayList<>();
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(CLIENT_SOCKET.getInputStream()));
        String input;
        while ((input = reader.readLine()).length() != 0) {
            requestData.add(input);
        }
        parseRequest(requestData);
    }

    /**
     * Parse Request method receives data from the request and parses the
     * request method, and requested resource.
     *
     * @param requestData - data received from client socket
     */
    private void parseRequest(ArrayList<String> requestData) {
        if (requestData.size() > 0) {
            String[] data = requestData.get(0).split(" ");
            requestMethod = data[0];
            if (data[1].equalsIgnoreCase("/")) {
                requestedResource = "/index.html";
            } else {
                requestedResource = data[1];
            }
        }
    }

    /**
     * handle response method checks the request method and prepares the file to
     * be sent to the client. If requested resource does not exist then an error
     * is sent back to the client.
     *
     * @throws IOException
     */
    private void handleResponse() throws IOException {
        if (requestMethod.equalsIgnoreCase("GET")) {
            File file = new File(WORKING_DIRECTORY + File.separator + requestedResource);
            if (file.isFile()) {
                sendFileResponse(200, "OK", file);
            } else {
                sendCharResponse(404, "Not Found", "<h1>File not found</h1>");
            }
        } else {
            sendCharResponse(405, "Method Not Allowed", "<h1>Method Not Allowed</h1>");
        }
    }

    /**
     * Send Char Response method is responsible for sending character data to
     * the client browser
     *
     * @param code - Http numerical status code to send to client
     * @param status - Http status message to send to client
     * @param data - the body of the data to send to the client
     * @throws IOException
     */
    private void sendCharResponse(int code, String status, String data) throws IOException {
        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(CLIENT_SOCKET.getOutputStream()));
        writer.write("HTTP/1.1 " + code + " " + status + " \r\n");
        writer.write("\r\n");
        writer.write(data);
        writer.flush();
    }

    /**
     * Send File Response method is responsible for sending file data to the
     * client browser. the data sent to the client is in the form of bytes. this
     * method was needed to send headers and file data to the client in the same
     * format as not to confuse the browser to what it is receiving.
     *
     * @param code - Http numerical status code to send
     * @param status - Http status message to send
     * @param file - file to send to client
     * @throws IOException
     */
    @SuppressWarnings("ConvertToTryWithResources")
    private void sendFileResponse(int code, String status, File file) throws IOException {
        OutputStream out = new DataOutputStream(CLIENT_SOCKET.getOutputStream());
        String line = "HTTP/1.1 " + code + " " + status + "\r\n";
        out.write(line.getBytes(Charset.forName("UTF-8")));
        line = "Content-Type:" + getMimeType(file) + ";\r\n";
        out.write(line.getBytes(Charset.forName("UTF-8")));
        line = "Content-Length: " + file.length() + "\r\n";
        out.write(line.getBytes(Charset.forName("UTF-8")));
        line = "\r\n";
        out.write(line.getBytes(Charset.forName("UTF-8")));

        FileInputStream fis = new FileInputStream(file);
        int read;
        byte[] buff = new byte[8192];
        while ((read = fis.read(buff)) > 0) {
            out.write(buff, 0, read);
        }
        fis.close();

        out.flush();
        out.close();
    }

    /**
     * Get Mime Type method is responsible for detecting and returning the name
     * of the Mime type for the file being sent to the browser
     *
     * @param file - file being sent to browser
     * @return - returns string of the name of the mime type for the file
     */
    @SuppressWarnings("ConvertToTryWithResources")
    private String getMimeType(File file) {
        String mimeType = "";
        try {
            mimeType = Files.probeContentType(file.toPath());
            if (mimeType == null) {
                BufferedInputStream in = new BufferedInputStream(Files.newInputStream(file.toPath()));
                mimeType = URLConnection.guessContentTypeFromStream(in);
                in.close();
            }
            if (mimeType == null) {
                mimeType = URLConnection.guessContentTypeFromName(file.getName());
            }
        } catch (IOException ex) {
            Logger.getLogger(RequestHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mimeType;
    }
}
