import java.io.*;
import java.net.*;
import java.util.*;

class EchoServer{
    private String ip = null;
    private int port;
    
    public EchoServer(String ip, int port) {
        this.ip = ip;
        this.port= port;
    }
    
    public void start() throws Exception {
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress(this.ip, this.port));
        
        Socket clientSocket = serverSocket.accept();
        
        serverResponse(clientSocket);
    }
    
    private void serverResponse(Socket clientSocket) throws Exception  {
        System.out.println("Connection Made" );
        
        ArrayList<String> requestData = new ArrayList<>();
        requestData = parseRequest(clientSocket);
        
        sendRequestDataBack(requestData, clientSocket);
        
        clientSocket.close();
    }
    
    private ArrayList<String> parseRequest(Socket clientSocket) throws Exception {
        BufferedReader request = new BufferedReader(
        new InputStreamReader(clientSocket.getInputStream()));
        ArrayList<String> data = new ArrayList<>();
        String line = null;
        
        while((line = request.readLine()) != null){
            if(line.length() == 0){
                break;
            }
            data.add(line);
        }
        return data;
    }
    
    private void sendRequestDataBack(ArrayList<String> data, Socket clientSocket) throws Exception {
        PrintWriter response =
        new PrintWriter(clientSocket.getOutputStream(), true);
        
        for (String x : data ){
            response.println("<p>" + x + "</p>");
        }
    }
}

public class test {
    
    public static void main(String[] args) throws Exception {
        EchoServer testServer = new EchoServer("0.0.0.0",5000);
        testServer.start();
    }
    
}
