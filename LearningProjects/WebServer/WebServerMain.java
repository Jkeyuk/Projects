package webserver;

 class WebServerMain {

    public static void main(String[] args) {
        WebServer server = new WebServer("C:\\Users\\jonke_000\\Documents\\GitHub\\Jkeyuk.github.io", 8080);
        server.start();
    }

}
