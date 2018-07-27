package fileTransferProgram;

import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;

public class FTclient {

	private String workingDir;

	public FTclient(String workingDir) {
		if (workingDir == null || workingDir.trim().isEmpty())
			throw new IllegalArgumentException("workingDir cannot be null or empty");
		this.workingDir = workingDir;
	}

	public void start(String ip, int port) {
		if (ip == null || ip.trim().isEmpty())
			throw new IllegalArgumentException("ip cannot be null or empty");
		if (port < 0 || port > 65535)
			throw new IllegalArgumentException("port must be in range 0 to 65535 inclusive");
		Scanner scan = new Scanner(System.in);
		while (true) {
			System.out.println("Enter request line or STOP to end");
			String input = scan.nextLine().trim();
			if (input.equalsIgnoreCase("STOP")) break;
			processRequest(input.split("\\s+"), ip, port);
		}
		scan.close();
	}

	private void processRequest(String[] request, String ip, int port) {
		if (request.length >= 2) {
			String method = request[0], resource = request[1];
			if (method.equalsIgnoreCase("PUT")) {
				if (new File(request[1]).exists()) {
					doPut(request, ip, port);
				} else {
					System.out.println("Cannot find file");
				}
			} else if (method.equalsIgnoreCase("GET") || method.equalsIgnoreCase("SHOW")
					|| method.equalsIgnoreCase("REMOVE")) {
				doRequest(ip, port, method, resource);
			} else {
				System.out.println("Invalid Method");
			}
		} else {
			System.out.println("Malformed request");
		}
	}

	private void doPut(String[] request, String ip, int port) {
		Path pathInServer = Paths.get(request[1]).getFileName();
		if (request.length > 2) pathInServer = Paths.get(request[2]).resolve(pathInServer);
		Socket sock = sendRequest(ip, port, request[0], pathInServer.toString());
		if (sock != null) {
			try {
				Files.copy(Paths.get(request[1]), sock.getOutputStream());
				sock.shutdownOutput();
				System.out.println(FtpIO.getLineFromSock(sock));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		FtpIO.closeConnection(sock);
	}

	private void doRequest(String ip, int port, String method, String resource) {
		Socket sock = sendRequest(ip, port, method, resource);
		String responseLine = FtpIO.getLineFromSock(sock);
		if (responseLine != null) {
			if (responseLine.equals("OK")) {
				if (method.equalsIgnoreCase("GET")) recieveFile(resource, sock);
				if (method.equalsIgnoreCase("SHOW"))
					System.out.println(FtpIO.getLineFromSock(sock));
			}
			System.out.println(responseLine);
		}
		FtpIO.closeConnection(sock);
	}

	private Socket sendRequest(String ip, int port, String method, String resource) {
		try {
			Socket sock = new Socket(ip, port);
			FtpIO.sendLineOverSock(method + " " + resource, sock);
			return sock;
		} catch (IOException e) {
			System.out.println("Could not reach server.");
			return null;
		}
	}

	private void recieveFile(String resource, Socket sock) {
		Path resourceName = Paths.get(resource).getFileName();
		Path finalPath = Paths.get(workingDir).resolve(resourceName);
		try {
			Files.copy(sock.getInputStream(), finalPath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
