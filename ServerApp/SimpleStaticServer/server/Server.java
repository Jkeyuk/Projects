/*
Server Super Class
 */
package server;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private final ServerSocket serverSocket;
    protected Socket clientSocket = null;

    //constructor
    public Server(int port) throws Exception {
        this.serverSocket = new ServerSocket(port);
    }

    //starts server
    public void start() throws Exception {

        while (true) {
            //open socket and listen for requests
            clientSocket = serverSocket.accept();
            //server response to HTTP requests
            serverResponse();
            //close socket       
            clientSocket.close();
        }
    }

    //response from server
    protected void serverResponse() throws Exception {

    }

}
