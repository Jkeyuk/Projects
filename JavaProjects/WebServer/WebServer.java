package webserver;

/**
 * web server to serve files to a web browser
 */
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

class WebServer {

    private final File WORKING_DIRECTORY;
    private final ExecutorService EXEC = Executors.newFixedThreadPool(100);
    private final int PORT;

    /**
     * Server object is built with a working directory and a port number
     *
     * @param WORKING_DIRECTORY - directory to host server files
     * @param port - port to receive connection through
     */
    WebServer(String WORKING_DIRECTORY, int port) {
        this.WORKING_DIRECTORY = new File(WORKING_DIRECTORY);
        this.PORT = port;
    }

    /**
     * start method starts the server on a given port. Each request is handled
     * in a separate thread by a request handler object.
     */
    void start() {
        try {
            ServerSocket server = new ServerSocket(PORT);
            System.out.println("Server started");
            while (true) {
                Socket clientSock = server.accept();
                EXEC.execute(new RequestHandler(WORKING_DIRECTORY, clientSock));
            }
        } catch (IOException ex) {
            Logger.getLogger(WebServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
