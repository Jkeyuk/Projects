package TerminalChat.Tests;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.UUID;

import TerminalChat.ChatRoom;

public class ChatRoomTests {

	private static ChatRoom server;
	private static Socket client1;
	private static Socket client2;
	private static Socket client3;

	public static void main(String[] args) throws Throwable {
		if (listenAndSendTest()) {
			System.out.println("test passed");
		} else {
			System.out.println("fail");
		}
		System.exit(0);
	}

	/*
	 * This will test if sendChatToUsers and ListenForChat methods are working
	 * for the ChatRoom class.
	 */
	private static boolean listenAndSendTest() throws Throwable {
		// Setup
		server = new ChatRoom();
		server.start(8080);
		client1 = new Socket("127.0.0.1", 8080);
		client2 = new Socket("127.0.0.1", 8080);
		client3 = new Socket("127.0.0.1", 8080);
		BufferedReader reader1 = new BufferedReader(
				new InputStreamReader(client1.getInputStream()));
		BufferedWriter writer1 = new BufferedWriter(
				new OutputStreamWriter(client1.getOutputStream()));
		BufferedReader reader2 = new BufferedReader(
				new InputStreamReader(client2.getInputStream()));
		BufferedWriter writer2 = new BufferedWriter(
				new OutputStreamWriter(client2.getOutputStream()));
		BufferedReader reader3 = new BufferedReader(
				new InputStreamReader(client3.getInputStream()));
		BufferedWriter writer3 = new BufferedWriter(
				new OutputStreamWriter(client3.getOutputStream()));
		/*
		 * Send 100 random Strings to the server, the server should send each
		 * one back to each client connected. If pass, the server is listening
		 * and sending messages.
		 */
		for (int i = 0; i < 100; i++) {
			String s = UUID.randomUUID().toString();
			writer1.write(s);
			writer1.newLine();
			writer1.flush();
			if (!reader1.readLine().equals(s)) {
				return false;
			}
			if (!reader2.readLine().equals(s)) {
				return false;
			}
			if (!reader3.readLine().equals(s)) {
				return false;
			}

			writer2.write(s);
			writer2.newLine();
			writer2.flush();
			if (!reader1.readLine().equals(s)) {
				return false;
			}
			if (!reader2.readLine().equals(s)) {
				return false;
			}
			if (!reader3.readLine().equals(s)) {
				return false;
			}

			writer3.write(s);
			writer3.newLine();
			writer3.flush();
			if (!reader1.readLine().equals(s)) {
				return false;
			}
			if (!reader2.readLine().equals(s)) {
				return false;
			}
			if (!reader3.readLine().equals(s)) {
				return false;
			}
		}
		writer1.close();
		writer2.close();
		writer3.close();
		reader1.close();
		reader2.close();
		reader3.close();
		client1.close();
		client2.close();
		client3.close();
		return true;
	}
}