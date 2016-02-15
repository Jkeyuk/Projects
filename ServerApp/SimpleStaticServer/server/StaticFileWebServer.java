package server;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.ServerUtilities.HttpResponse;

public class StaticFileWebServer extends WebServer {

    //working directory for files to serve
    private final String workingDirectory;

    //constructor for server
    public StaticFileWebServer(int port, String workingDirectory) throws Exception {
        super(port);
        this.workingDirectory = workingDirectory;
    }

    //activates when Http request is recieved
    @Override
    protected void sendResponseToClient() throws Exception {
        //check request method
        checkAndHandleRequest();
    }

    //checks request and responds appropriatly
    private void checkAndHandleRequest() throws IOException {
        //set default path to website
        if (requestPath.trim().equals("/")) {
            requestPath = "/index.html";
        }
        //check request method
        if ((requestMethod.trim()).equals("GET")) {
            //send file 
            sendFile();
        }
    }

    //sends file to client
    private void sendFile() throws IOException {
        //get data from file
        String data = returnFileData(workingDirectory, requestPath);
        //create Http Response
        HttpResponse response = new HttpResponse(clientSocket);
        //set content type header
        response.addHeader("Content-Type:" + returnMimeType());
        //send data to client
        response.sendResponse(200, "OK", data);
     
    }

    private String returnFileData(String directory, String path) {
        String fileData = "";
        //create file to scan
        File file = new File(directory + path);
        //scan file content and add to data
        try (Scanner reader = new Scanner(file)) {
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                fileData += line + "\n";
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(StaticFileWebServer.class.getName()).log(Level.SEVERE, null, ex);
            requestPath = "/index.html";
            fileData ="<p>Wrong Pathname</p>";
        }
        //return file data
        return fileData;
    }

    private String returnMimeType() {
        String mime = null;
        String[] parts = requestPath.split("\\.");
        switch (parts[1]) {
            case "html":
            case "css":
                mime = "text/" + parts[1];
                break;
            case "ico":
                mime = "image/x-icon";
                break;
            case "js":
                mime = "application/javascript";
                break;
            default:
                break;
        }
        return mime;
    }
}
