package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Main {
    private static final int SERVER_PORT = 8080;
    private static Socket clientSocket;
    private static ServerSocket server;

    public static History history;


    public static void main(String[] args) {
        history = new History();
        try {
            try {
                server = new ServerSocket(SERVER_PORT);
                System.out.println("start server");
                while (true) {
                    clientSocket = server.accept();
                    System.out.println("new connect: " + clientSocket.getInetAddress() + " " + clientSocket.getPort());
                    new Server(clientSocket);
                }
            } finally {
                server.close();
                clientSocket.close();
            }
        } catch (IOException e) {
       }
    }

}
