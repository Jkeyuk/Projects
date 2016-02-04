
import java.io.*;
import java.net.*;

class Server{
    private String ip = null;
    private int port;
    
    public Server(String ip, int port) {
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
        
        PrintWriter response =
        new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader request = new BufferedReader(
        new InputStreamReader(clientSocket.getInputStream()));
        
        String data = null;
        while((data = request.readLine()) != null){
            if(data.length() == 0){
                break;
            }
            response.println("<p>" + data + "</p>");
        }
        System.out.println("Connection made to: "+clientSocket.getInetAddress());
        response.close();
        request.close();
        clientSocket.close();
    }
}

public class simpleServer {
    
    public static void main(String[] args) throws Exception {
       Server testServer = new Server("0.0.0.0",5000);
       testServer.start();
    }
    
}
