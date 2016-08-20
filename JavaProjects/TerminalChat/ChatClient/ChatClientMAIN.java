package chatclient;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatClientMAIN {

    public static void main(String[] args) {
        String ip = getIP();
        int port = getPort();
        ChatClient client = new ChatClient(ip, port);
        client.connect();
    }

    private static String getIP() {
        String ip = "";
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("");
            System.out.println("Enter IP address of server to connect to:");
            ip = scanner.nextLine();
        } while (!checkIP(ip));
        return ip;
    }

    private static boolean checkIP(String s) {
        Pattern p = Pattern.compile("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}");
        Matcher m = p.matcher(s);
        return m.matches();
    }

    private static int getPort() {
        String input;
        Scanner scan = new Scanner(System.in);
        do {
            System.out.println("");
            System.out.println("Enter port number of server to connect to:");
            input = scan.nextLine();
        } while (!checkInt(input));
        int returnInt = Integer.parseInt(input);
        return returnInt;
    }

    private static boolean checkInt(String i) {
        try {
            int test = Integer.parseInt(i);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
}
