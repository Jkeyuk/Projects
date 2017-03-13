package TerminalChat;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.function.Consumer;

/**
 * Methods for sending and receiving data.
 * 
 * @author Jonathan Keyuk
 *
 */
public class TerminalIO {

	/**
	 * Sends a given message over a given buffered writer. String cannot be null
	 * or empty.
	 * 
	 * @param m
	 *            message to send
	 * @param w
	 *            writer to send message over
	 * @throws IOException
	 */
	public static void sendMessage(String m, BufferedWriter w) throws IOException {
		if (m != null && m.trim().length() > 0) {
			w.write(m);
			w.newLine();
			w.flush();
		}
	}

	/**
	 * Listens over a given socket for a string, the string is then passed to
	 * the given function.
	 * 
	 * @param s
	 *            Socket to listen for incoming string.
	 * @param fun
	 *            function call with incoming string as parameter.
	 * @throws IOException
	 */
	public static void listenOverSocket(Socket s, Consumer<String> fun)
			throws IOException {
		BufferedReader input = new BufferedReader(
				new InputStreamReader(s.getInputStream()));
		while (true) {
			fun.accept(input.readLine());
		}
	}
}
