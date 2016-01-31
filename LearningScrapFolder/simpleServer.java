
import java.io.*;
import java.net.*;

public class simpleServer {

    public static void main(String[] args) throws Exception {
       ServerSocket serverSocket = new ServerSocket(8000);
       Socket clientSocket = serverSocket.accept();
       
        PrintWriter response = 
                new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader request = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
        
                response.println("hi");
                response.close();
                request.close();
    }
    
}
