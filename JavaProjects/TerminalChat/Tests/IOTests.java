package TerminalChat.Tests;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import TerminalChat.TerminalIO;

public class IOTests {

	private static Socket clientToServer;
	private static Socket serverToClient;
	private static ServerSocket serverSock;
	private static ExecutorService EXEC = Executors.newFixedThreadPool(100);

	public static void main(String[] args) throws Throwable {
		if (sendMessageTests() && listenOverSocketTests()) {
			System.out.println("Tests Passed");
		} else {
			System.out.println("Fail");
		}
		EXEC.shutdown();
		System.exit(0);
	}

	private static boolean sendMessageTests() throws Throwable {
		setup();
		BufferedReader reader = new BufferedReader(
				new InputStreamReader(serverToClient.getInputStream()));
		BufferedWriter writer = new BufferedWriter(
				new OutputStreamWriter(clientToServer.getOutputStream()));
		/*
		 * 100 Random Strings test
		 */
		for (int i = 0; i < 100; i++) {
			String s = UUID.randomUUID().toString();
			TerminalIO.sendMessage(s, writer);
			if (!reader.readLine().equals(s)) {
				return false;
			}
		}
		shutdown();
		return true;
	}

	private static boolean listenOverSocketTests() throws Throwable {
		setup();
		BufferedWriter writer = new BufferedWriter(
				new OutputStreamWriter(clientToServer.getOutputStream()));
		/*
		 * 100 random strings test
		 */
		ArrayList<String> expected = new ArrayList<>();
		ArrayList<String> result = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			String s = UUID.randomUUID().toString();
			TerminalIO.sendMessage(s, writer);
			expected.add(s);
		}
		// Call method to test in separate thread.
		Runnable r = () -> {
			try {
				TerminalIO.listenOverSocket(serverToClient, x -> result.add(x));
			} catch (IOException e) {
			}
		};
		EXEC.execute(r);
		Thread.sleep(1000);
		// Test if array was filled with correct strings
		if (!result.equals(expected)) {
			return false;
		}
		shutdown();
		return true;
	}

	private static void setup() throws Throwable {
		Callable<Socket> start = () -> {
			try {
				serverSock = new ServerSocket(8080);
				Socket client = serverSock.accept();
				return client;
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}
		};
		Future<Socket> future = EXEC.submit(start);
		clientToServer = new Socket("127.0.0.1", 8080);
		serverToClient = future.get();
	}

	private static void shutdown() {
		try {
			clientToServer.close();
			serverToClient.close();
			serverSock.close();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			serverSock = null;
			clientToServer = null;
			serverToClient = null;
		}
	}
}
