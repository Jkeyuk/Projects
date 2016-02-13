
import java.util.logging.Level;
import java.util.logging.Logger;
import server.StaticFileWebServer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jonke_000
 */
public class ServerAppRunnable implements Runnable{

    @Override
    public void run() {
        try {
            //create server with port and working directory
            StaticFileWebServer testServer2 = new StaticFileWebServer(5000,
                    "C:\\Users\\jonke_000\\Documents\\Server\\test");
            //start listening for requests
            testServer2.start();
        } catch (Exception ex) {
            Logger.getLogger(ServerAppRunnable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
