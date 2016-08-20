package portscanner;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;

public class PortScanner implements Runnable {

    private final String IP;
    private final String PORT_RANGE;

    public PortScanner(String IP, String PORT_RANGE) {
        this.IP = IP;
        this.PORT_RANGE = PORT_RANGE;
    }

    private void startScanning() {
        ArrayList<Integer> openPorts = new ArrayList<>();
        String[] portRange = PORT_RANGE.split("-");
        int startPort = Integer.parseInt(portRange[0]);
        int endPort = Integer.parseInt(portRange[1]);
        for (int i = startPort; i <= endPort; i++) {
            if (scan(IP, i)) {
                openPorts.add(i);
            }
        }
        for (Integer x : openPorts) {
            System.out.println("Port " + x + " is open.");
        }
    }

    private boolean scan(String ipAddress, int port) {
        try (Socket s = new Socket()) {
            s.connect(new InetSocketAddress(ipAddress, port), 500);
            return s.isConnected();
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public void run() {
        startScanning();
    }
}
