package portscanner;

import java.util.Scanner;

public class PortScannerMain {

    public static void main(String[] args) {
        String ip = getIP().trim();
        String portRange = getPortRange().trim();
        PortScanner p = new PortScanner(ip, portRange);
        p.startScanning();
    }

    private static String getIP() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("");
        System.out.println("Enter IP address of server to scan:");
        String ip = scanner.nextLine();
        return ip;
    }

    private static String getPortRange() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("");
        System.out.println("Enter ports to scan like this example: 79-89");
        String portRange = scanner.nextLine();
        return portRange;
    }
}
