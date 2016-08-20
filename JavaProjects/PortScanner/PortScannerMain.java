package portscanner;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PortScannerMain {

    public static void main(String[] args) {
        String ip = getIP().trim();
        String portRange = getPortRange().trim();
        String[] ranges = splitRanges(portRange);
        PortScanner p1 = new PortScanner(ip, ranges[0]);
        PortScanner p2 = new PortScanner(ip, ranges[1]);
        System.out.println("Scanning now..this will take half a second per port....");
        new Thread(p1).start();
        new Thread(p2).start();
    }

    private static String getIP() {
        String ip = "";
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("");
            System.out.println("Enter IP address of server to scan:");
            ip = scanner.nextLine();
        } while (!checkIP(ip));
        return ip;
    }

    private static String getPortRange() {
        String portRange = "";
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("");
            System.out.println("Enter ports to scan like this example: 79-89");
            portRange = scanner.nextLine();
        } while (!checkRange(portRange));
        return portRange;
    }

    private static String[] splitRanges(String pRange) {
        String[] portRange = pRange.split("-");
        Integer startPort = Integer.parseInt(portRange[0]);
        Integer endPort = Integer.parseInt(portRange[1]);
        Integer midPort = startPort + ((endPort - startPort) / 2);
        Integer midPlusOne = midPort + 1;
        String midPortAsString = midPort.toString();
        String midPlusOneAsString = midPlusOne.toString();
        String[] splitRanges = new String[2];
        splitRanges[0] = portRange[0] + "-" + midPortAsString;
        splitRanges[1] = midPlusOneAsString + "-" + portRange[1];
        return splitRanges;
    }

    private static boolean checkIP(String s) {
        Pattern p = Pattern.compile("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}");
        Matcher m = p.matcher(s);
        return m.matches();
    }

    private static boolean checkRange(String s) {
        Pattern p = Pattern.compile("\\d+-\\d+");
        Matcher m = p.matcher(s);
        return m.matches();
    }
}
