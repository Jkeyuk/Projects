package fileTransferProgram;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class FtpIO {

	public static void sendLineOverSock(String string, Socket sock) {
		if (string == null || sock == null) return;
		try {
			DataOutputStream out = new DataOutputStream(sock.getOutputStream());
			out.writeUTF(string);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getLineFromSock(Socket sock) {
		if (sock == null) return null;
		try {
			DataInputStream in = new DataInputStream(sock.getInputStream());
			return in.readUTF();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String[] parseRequest(String request) {
		if (request == null || request.trim().isEmpty()) return null;
		String[] data = request.trim().split("\\s+");
		if (data.length != 2) return null;
		return data;
	}

	public static void closeConnection(Socket connection) {
		try {
			if (connection != null) connection.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
