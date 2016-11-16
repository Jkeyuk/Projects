package PortScanner;

import java.net.InetSocketAddress;
import java.net.Socket;

public class PortScanner implements Runnable {

	private final String IP;
	private final int START_PORT;
	private final int END_PORT;

	public PortScanner(String iP, int sTART_PORT, int eND_PORT) {
		super();
		IP = iP;
		START_PORT = sTART_PORT;
		END_PORT = eND_PORT;
	}

	@Override
	public void run() {
		for (int i = START_PORT; i <= END_PORT; i++) {
			if (scan(IP, i)) {
				System.out.println("Port " + i + " is open.");
			}
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
