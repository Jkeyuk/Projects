//creates a runnable object to run app in its own thread
package server;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerAppRunnable implements Runnable {

    private final int port;
    private final String directory;

    public ServerAppRunnable(int port, String directory) {
        this.port = port;
        this.directory = directory;
    }

    @Override
    public void run() {
        try {
            //create server with port and working directory
            StaticFileWebServer testServer2 = new StaticFileWebServer(port, directory);
            //start listening for requests
            testServer2.start();
        } catch (Exception ex) {
            Logger.getLogger(ServerAppRunnable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
