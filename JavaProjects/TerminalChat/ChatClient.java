package TerminalChat;

import java.util.Scanner;

/**
 * Chat Client allows users to chat to each other through the terminal window.
 * 
 * @author Jonathan Keyuk
 *
 */
public class ChatClient {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);

		System.out.println("Please enter a user name.");
		ChatUser user = new ChatUser(scan.nextLine().trim());

		System.out.println("Please enter number based on following option.");
		System.out.println("1. Host a Chat Room");
		System.out.println("2. Connect to a Chat Room");
		System.out.println("3.Exit");

		int option = scan.nextInt();
		scan.nextLine();

		if (option == 1) {
			ChatRoom room = new ChatRoom();
			System.out.println("Enter port to use:");
			int port = scan.nextInt();
			scan.nextLine();
			room.start(port);
			user.connectToChatRoom("127.0.0.1", port);
			System.out.println("Chat Room Started");
		} else if (option == 2) {
			System.out.println("Enter IP address.");
			String ip = scan.nextLine().trim();
			System.out.println("Enter Port number");
			int port = scan.nextInt();
			scan.nextLine();
			user.connectToChatRoom(ip, port);
		} else if (option == 3) {
			scan.close();
			System.exit(0);
		}
	}
}
