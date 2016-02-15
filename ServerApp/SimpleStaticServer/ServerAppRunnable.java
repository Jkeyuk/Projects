
import java.util.logging.Level;
import java.util.logging.Logger;
import server.StaticFileWebServer;
//implements app as a runnable for a thread
public class ServerAppRunnable implements Runnable{

    @Override
    public void run() {
        try {
            //create server with port and working directory
            StaticFileWebServer testServer2 = new StaticFileWebServer(5000,
                    "add directory here");
            //start listening for requests
            testServer2.start();
        } catch (Exception ex) {
            Logger.getLogger(ServerAppRunnable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
