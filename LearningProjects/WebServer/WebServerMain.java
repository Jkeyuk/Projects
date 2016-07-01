package webserver;

/**
 * This web server serves up files to clients on a web browser
 */
import java.io.File;
import java.util.Scanner;

class WebServerMain {

    /**
     * Application first prompts user for working directory, then prompts user
     * for port number. then starts the server with the given directory and
     * port.
     *
     * @param args - not used
     */
    public static void main(String[] args) {
        String directory = getWorkingDirectory();
        int port = getPort();
        WebServer server = new WebServer(directory, port);
        server.start();
    }

    private static String getWorkingDirectory() {
        String directory = "";
        Scanner scan = new Scanner(System.in);
        do {
            System.out.println("Please enter path to the working directory for the server");
            directory = scan.nextLine();
        } while (!new File(directory).isDirectory());
        return directory;
    }

    private static int getPort() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter the port to start server on");
        int port = scan.nextInt();
        return port;
    }
}
