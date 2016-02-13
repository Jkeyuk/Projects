/*
This is a class that represents an Http Request that has been pased
to access the data.
 */
package server.ServerUtilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ParsedHttpRequest {

    private final BufferedReader request;
    private String method;
    private String path;
    private ArrayList<String> requestAsArray = null;

    //constructs and set variables
    public ParsedHttpRequest(Socket clientSocket) throws Exception {
        this.request = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
        setObjectVariables(requestAsArray());
    }

    //returns Request Method
    public String getMethod() {
        return method;
    }

    //returns Request Path
    public String getPath() {
        return path;
    }

    //return request data parsed into array
    public ArrayList<String> getRequestAsArray() {
        return this.requestAsArray;
    }

    //Parses Request and returns array of data
    private ArrayList<String> requestAsArray() {
        //variable for reading stream
        ArrayList<String> data = new ArrayList<>();
        String line;
        try {
            //read stream and add data to array
            while ((line = request.readLine()) != null) {
                if (line.length() == 0) {
                    break;
                }
                data.add(line);
            }
        } catch (IOException ex) {
            Logger.getLogger(ParsedHttpRequest.class.getName()).log(Level.SEVERE, null, ex);
            data.add("GET /index.html");
        }
        //return array of data
        return data;
    }

    //parses array of data and sets class variables
    private void setObjectVariables(ArrayList<String> parsedData) {
        if (parsedData.size() > 0) {
            String[] parts = parsedData.get(0).split(" ");
            this.requestAsArray = parsedData;
            this.method = parts[0];
            this.path = parts[1];
        } else {
            this.method = "GET";
            this.path = "/";
        }
    }
}
