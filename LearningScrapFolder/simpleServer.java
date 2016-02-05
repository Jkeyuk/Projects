import java.io.*;
import java.net.*;
import java.util.*;

class EchoServer{
    private String ip = null;
    private int port;
    //constructor
    public EchoServer(String ip, int port) {
        this.ip = ip;
        this.port= port;
    }
    //starts server
    public void start() throws Exception {
        //create server socket
        ServerSocket serverSocket = new ServerSocket();
        //bind server socket to given ip and port
        serverSocket.bind(new InetSocketAddress(this.ip, this.port));
        //open socket and listen for requests
        Socket clientSocket = serverSocket.accept();
        //server response to requests
        serverResponse(clientSocket);
    }
    //response from server
    private void serverResponse(Socket clientSocket) throws Exception  {
        System.out.println("Connection Made" );
        //create array list and fill with request data
        ArrayList<String> requestData = new ArrayList<>();
        requestData = parseRequest(clientSocket);
        //send request data back to client
        sendRequestDataBack(requestData, clientSocket);
        //close socket
        clientSocket.close();
    }
    //parse request headers
    private ArrayList<String> parseRequest(Socket clientSocket) throws Exception {
        //connect input stream to reader
        BufferedReader request = new BufferedReader(
        new InputStreamReader(clientSocket.getInputStream()));
        //variable for reading stream
        ArrayList<String> data = new ArrayList<>();
        String line = null;
        //read stream and add data to array
        while((line = request.readLine()) != null){
            if(line.length() == 0){
                break;
            }
            data.add(line);
        }
        //return array of data
        return data;
    }
    //send request headers back
    private void sendRequestDataBack(ArrayList<String> data, Socket clientSocket) throws Exception {
        //conect output stream to writer
        PrintWriter response =
        new PrintWriter(clientSocket.getOutputStream(), true);
        //write to output stream
        for (String x : data ){
            response.println("<p>" + x + "</p>");
        }
    }
}

public class test {
    
    public static void main(String[] args) throws Exception {
        //create echo server object 
        EchoServer testServer = new EchoServer("0.0.0.0",5000);
        //start server
        testServer.start();
    }
    
}
