package TerminalChat;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * ChatUser represents a client using or hosting a chat room.
 * 
 * @author Jonathan Keyuk
 *
 */
public class ChatUser {

	private final String USER_NAME;
	private final Socket SOCKET;
	private final Executor EXEC = Executors.newFixedThreadPool(3);

	public ChatUser(String uSER_NAME) {
		this.USER_NAME = uSER_NAME;
		this.SOCKET = new Socket();
	}

	/**
	 * Connects chat user to a chat room at a given IP and port. Once connected
	 * a thread is started to listen for chat, and another thread is started to
	 * send message to chat room.
	 * 
	 * @param ip
	 *            String representing IP to connect to
	 * @param port
	 *            integer representing port to connect to
	 */
	public void connectToChatRoom(String ip, int port) {
		try {
			SOCKET.connect(new InetSocketAddress(ip, port));
			listenForChat();
			startChatting();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Starts a thread to listen for chat messages from the chat room. Messages
	 * are displayed to console.
	 */
	private void listenForChat() {
		Runnable listen = () -> {
			try {
				TerminalIO.listenOverSocket(SOCKET, System.out::println);
			} catch (IOException e) {
				System.out.println("chat has been closed, shutting down.");
				System.exit(0);
			}
		};
		EXEC.execute(listen);
	}

	/**
	 * Starts a thread that waits for messages to be inputed by user. messages
	 * are sent to the chat room.
	 */
	private void startChatting() {
		Runnable talk = () -> {
			try {
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(SOCKET.getOutputStream()));
				Scanner scan = new Scanner(System.in);
				while (true) {
					TerminalIO.sendMessage(USER_NAME + ": " + scan.nextLine().trim(), writer);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		};
		EXEC.execute(talk);
	}
}
