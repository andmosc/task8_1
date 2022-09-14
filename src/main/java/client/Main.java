package client;

public class Main {
    public static String localhost = "localhost";
    public static int port = 8080;

    public static void main(String[] args) {
        new Client(localhost,port);
    }
}
