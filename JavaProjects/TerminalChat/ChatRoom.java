package TerminalChat;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * ChatRoom represents a server which ChatUsers will connect to, to chat with
 * each other.
 * 
 * @author Jonathan Keyuk
 *
 */
public class ChatRoom {

	private final Executor EXEC = Executors.newFixedThreadPool(100);
	private final List<Socket> CLIENT_LIST = Collections.synchronizedList(new ArrayList<>());

	/**
	 * waits for connections over the server socket for users to connect to.
	 * Once a user is connected, user is added to the room, and a thread is
	 * opened to await user messages.
	 */
	public void start(int port) {
		Runnable start = () -> {
			try {
				ServerSocket serverSock = new ServerSocket(port);
				while (true) {
					Socket client = serverSock.accept();
					CLIENT_LIST.add(client);
					listenForChat(client);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		};
		EXEC.execute(start);
	}

	/**
	 * Opens a thread to waits for messages over a given socket. The received
	 * message is Broadcasted to all users in the room.
	 * 
	 * @param client:
	 *            socket to await messages on.
	 */
	private void listenForChat(Socket client) {
		Runnable listen = () -> {
			try {
				TerminalIO.listenOverSocket(client, m -> sendChatToUsers(m));
			} catch (IOException e) {
				removeClient(client);
			}
		};
		EXEC.execute(listen);
	}

	/**
	 * Sends a given string to all users of the chat room.
	 * 
	 * @param message
	 *            String to send to users.
	 */
	private void sendChatToUsers(String message) {
		for (Socket sock : CLIENT_LIST) {
			try {
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
				TerminalIO.sendMessage(message, writer);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Closes a given socket, and removes user of given socket from client list.
	 * 
	 * @param s
	 *            socket to close
	 */
	private void removeClient(Socket s) {
		try {
			s.close();
			CLIENT_LIST.remove(s);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
