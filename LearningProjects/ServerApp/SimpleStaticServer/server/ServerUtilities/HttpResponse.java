package server.ServerUtilities;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class HttpResponse {

    private final PrintWriter responseWriter;
    private final ArrayList<String> responseHeaders;
    //constructor
    public HttpResponse(Socket clientSocket) throws IOException {
        //connects response to client socket
        this.responseWriter
                = new PrintWriter(clientSocket.getOutputStream(), true);
        //initialize headers
        this.responseHeaders = new ArrayList<>();
    }
    //add headers to Http response
    public void addHeader(String header) {
        responseHeaders.add(header);
    }
    //sends Http response to client
    public void sendResponse(int statusCode, String statusMessage,
            String responseBody) {
        //send status line
        responseWriter.println("HTTP/1.1 " + statusCode + " " + statusMessage);
        //send headers
        for (String string : responseHeaders) {
            responseWriter.println(string);
        }
        //empty line indicates end of headers
        responseWriter.println();
        //send response body
        responseWriter.println(responseBody);
    }

}
