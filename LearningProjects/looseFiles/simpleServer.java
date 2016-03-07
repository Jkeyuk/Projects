
import java.io.*;
import java.net.*;
import java.util.*;

class EchoServer {

    private final int port;

    //constructor
    public EchoServer(int port) {
        this.port = port;
    }

    //starts server
    public void start() throws Exception {
        //create server socket
        ServerSocket serverSocket = new ServerSocket();
        //bind server socket to given port
        serverSocket.bind(new InetSocketAddress(this.port));
        //open socket and listen for requests
        Socket clientSocket = serverSocket.accept();
        //server response to requests
        serverResponse(clientSocket);
    }

    //response from server
    private void serverResponse(Socket clientSocket) throws Exception {
        System.out.println("Connection Made");
        //send request data back to client    
        sendRequestDataBack(returnParsedRequest(clientSocket), clientSocket);
        //close socket       
        clientSocket.close();
    }

    //returns array of parsed request data
    private ArrayList<String> returnParsedRequest(Socket clientSocket) throws Exception {
        //connect input stream to reader
        BufferedReader request = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
        //variable for reading stream
        ArrayList<String> data = new ArrayList<>();
        String line;

        //read stream and add data to array
        while ((line = request.readLine()) != null) {
            if (line.length() == 0) {
                break;
            }
            data.add(line);
        }
        //return array of data
        return data;
    }

    //get method from http request
    private String getHttpMethod(ArrayList<String> parsedData) {
        String method = parsedData.get(0);
        String[] parts = method.split("/");
        method = parts[0].trim();

        return method;
    }

    //send request headers back
    private void sendRequestDataBack(ArrayList<String> data, Socket clientSocket) throws Exception {
        //conect output stream to writer
        PrintWriter response
                = new PrintWriter(clientSocket.getOutputStream(), true);
        //write to output stream
        for (String x : data) {
            response.println("<p>" + x + "</p>");
        }
    }
}

public class Server {

    public static void main(String[] args) throws Exception {
        //create echo server object 
        EchoServer testServer = new EchoServer(5000);
        //start server
        testServer.start();
    }

}
