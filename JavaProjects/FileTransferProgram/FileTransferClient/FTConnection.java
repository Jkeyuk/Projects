package FileTransferProgram.FileTransferClient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;

/**
 * FTConnection represents a connection to the file transfer server.
 */
final class FTConnection {

	private final Socket SOCKET = new Socket();
	private DataOutputStream output;
	private DataInputStream input;

	public FTConnection(String SERVER_ADDRESS, int SERVER_PORT) {
		try {
			SOCKET.connect(new InetSocketAddress(SERVER_ADDRESS, SERVER_PORT));
			output = new DataOutputStream(SOCKET.getOutputStream());
			input = new DataInputStream(SOCKET.getInputStream());
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Reads bytes from given file and sends those bytes over data output
	 * stream.
	 * 
	 * @param file
	 *            - file to send over stream
	 */
	public void sendFile(File file) {
		try (FileInputStream fis = new FileInputStream(file)) {
			output.writeLong(file.length());
			int read;
			byte[] buff = new byte[1024];
			while ((read = fis.read(buff)) > 0) {
				output.write(buff, 0, read);
			}
			output.flush();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Receives bytes from data input stream and writes bytes to file.
	 * 
	 * @param fileName
	 *            - String representing name of the file to write bytes to.
	 */
	public void recieveFile(String fileName) {
		File file = new File(fileName);
		try (FileOutputStream fos = new FileOutputStream(file)) {
			file.createNewFile();
			int read;
			byte[] buff = new byte[1024];
			long numOfBytes = input.readLong();
			while (numOfBytes > 0) {
				read = input.read(buff);
				numOfBytes -= read;
				fos.write(buff, 0, read);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Sends String over data output stream.
	 * 
	 * @param m
	 *            - string to send
	 */
	public void sendMessage(String m) {
		try {
			output.writeUTF(m);
			output.flush();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Returns array of Strings received from input data stream.
	 */
	public ArrayList<String> receiveMessage() {
		ArrayList<String> data = new ArrayList<>();
		try {
			String incoming;
			while ((incoming = input.readUTF()) != null) {
				if (incoming.equals("@END@")) {
					break;
				}
				data.add(incoming);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}

	/**
	 * Closes connection to socket
	 */
	public void close() {
		try {
			SOCKET.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
