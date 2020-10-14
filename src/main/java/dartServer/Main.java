package dartServer;

public class Main {

    // {"type":"authRequest","packet":{"username":"mrjosch","password":"sanoj050499"},"timestamp":"Oct 15, 2020, 12:52:31 AM"}

    public static void main(String[] args) {
        Server server = new Server(9000);
        server.start();
    }
}
