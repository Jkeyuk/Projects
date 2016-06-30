package webserver;

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

    private void sendCharResponse(int code, String status, String data) throws IOException {
        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(CLIENT_SOCKET.getOutputStream()));
        writer.write("HTTP/1.1 " + code + " " + status + " \r\n");
        writer.write("\r\n");
        writer.write(data);
        writer.flush();
    }

    @SuppressWarnings("ConvertToTryWithResources")
    private void sendFileResponse(int code, String status, File file) throws IOException {
        OutputStream out = new DataOutputStream(CLIENT_SOCKET.getOutputStream());
        String line = "HTTP/1.1 " + code + " " + status + "\r\n";
        out.write(line.getBytes(Charset.forName("UTF-8")));
        line = "Content-Type:" + Files.probeContentType(file.toPath()) + ";\r\n";
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
}
