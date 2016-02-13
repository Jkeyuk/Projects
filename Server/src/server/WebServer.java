/*
extends Server class to handle HTTP requests
 */
package server;

import server.ServerUtilities.ParsedHttpRequest;
import java.util.ArrayList;

public class WebServer extends Server {

    protected String requestMethod;
    protected String requestPath;
    protected ArrayList<String> requestAsArray = null;

    //used constructer from superclass Server
    public WebServer(int port) throws Exception {
        super(port);
    }

    //activates when Http Request is recieved overides Super class Server method
    @Override
    protected void serverResponse() throws Exception {
        //parse request data
        parseRequestData();
        //sendResponseToClient
        sendResponseToClient();
    }

    //analyses HTTP request and parses data
    protected void parseRequestData() throws Exception {
        //create Http Request Data object
        ParsedHttpRequest requestData = new ParsedHttpRequest(clientSocket);
        //set Webserver variables
        this.requestAsArray = requestData.getRequestAsArray();
        this.requestMethod = requestData.getMethod();
        this.requestPath = requestData.getPath();
    }

    //Send HTTP Response back to client
    protected void sendResponseToClient() throws Exception {
    }
}
