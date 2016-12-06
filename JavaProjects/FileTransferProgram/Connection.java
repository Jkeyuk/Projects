package FileTransferProgram;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.function.Consumer;

/**
 * Connection class represents a connection between the server and the client.
 * 
 * @author Jonathan Keyuk
 *
 */
public class Connection {

	private DataOutputStream output;
	private DataInputStream input;

	public Connection(Socket sock) {
		try {
			this.output = new DataOutputStream(sock.getOutputStream());
			this.input = new DataInputStream(sock.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sends a given file over the data output stream. User must ensure file
	 * exists.
	 * 
	 * @param file
	 *            file to send
	 * @throws IOException
	 */
	public void sendFile(File file) throws IOException {
		FileInputStream fis = new FileInputStream(file);
		int read;
		byte[] buff = new byte[1024];
		output.writeLong(file.length());
		while ((read = fis.read(buff)) > 0) {
			output.write(buff, 0, read);
		}
		output.flush();
		fis.close();
	}

	/**
	 * Reads data from the data input stream and writes the data to a given
	 * file. If bytes of data are not available to read on attempt, this method
	 * will not attempt to read from the stream. User must ensure the file and
	 * pathname exist.
	 * 
	 * @param file
	 *            the file to write the data to
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void recieveFile(File file) throws IOException, InterruptedException {
		FileOutputStream fos = new FileOutputStream(file);
		Thread.sleep(1200);
		int read;
		if ((read = input.available()) > 0) {
			byte[] buff = new byte[1024];
			long numOfBytes = input.readLong();
			while (numOfBytes > 0) {
				read = input.read(buff);
				numOfBytes -= read;
				fos.write(buff, 0, read);
			}
		}
		fos.close();
	}

	/**
	 * Sends a given string over the data output stream.
	 * 
	 * @param m
	 *            String to send
	 */
	public void sendMessage(String m) {
		try {
			output.writeUTF(m);
			output.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Starts a loop to read messages from the data input stream. messages are
	 * consumed by a given consumer. The loop is terminated when the String
	 * received equals "@END@".
	 * 
	 * @param fun
	 *            method reference or lambda to consume string read from stream.
	 * @throws IOException
	 */
	public void receiveMessages(Consumer<String> fun) throws IOException {
		String line;
		while (!(line = getMessage()).equals("@END@")) {
			fun.accept(line);
		}
	}

	/**
	 * Returns a string read from the data input stream
	 * 
	 * @return - String read from data input stream.
	 * @throws IOException
	 */
	public String getMessage() throws IOException {
		return input.readUTF();
	}
}
