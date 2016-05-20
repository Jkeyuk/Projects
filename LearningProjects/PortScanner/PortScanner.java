package portscanner;

import java.net.InetSocketAddress;
import java.net.Socket;

public class PortScanner {

    private final String IP;
    private final String PORT_RANGE;

    public PortScanner(String IP, String PORT_RANGE) {
        this.IP = IP;
        this.PORT_RANGE = PORT_RANGE;
    }

    public void startScanning() {
        String[] portRange = PORT_RANGE.split("-");
        int startPort = Integer.parseInt(portRange[0]);
        int endPort = Integer.parseInt(portRange[1]);
        for (int i = startPort; i <= endPort; i++) {
            System.out.println("is port " + i + " open? " + scan(IP, i));
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
}
