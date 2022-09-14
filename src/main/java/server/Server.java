package server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server extends Thread {

    private BufferedReader in;
    private BufferedWriter out;

    private Socket clientSocket;

    private static final List<Server> listClient = new ArrayList<>();

    public Server(Socket socket) {
        this.clientSocket = socket;
        try {

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            listClient.add(this);
            Main.history.printHistory(out);
            start();

        } catch (IOException e) {
        }
    }

    @Override
    public void run() {
        String msg;
        try {
            while (true) {
                msg = in.readLine();
                if (msg.equals("exit")) {
                    System.out.println("disconnect " + clientSocket.getInetAddress());
                    this.downServer();
                    break;
                } else {
                    System.out.println(msg);
                    Main.history.addStory(msg);
                    for (Server sv : listClient) {
                        sv.send(msg);
                    }
                }
                out.flush();
            }
        } catch (IOException ex) {
            downServer();
        }
    }

    private void downServer() {
        try {
            if (!clientSocket.isClosed()) {
                clientSocket.close();
                in.close();
                out.close();
                for (Server vr : listClient) {
                    if (vr.equals(this)) vr.interrupt();
                    listClient.remove(this);
                }
            }
        } catch (IOException ex) {
        }
    }


    private void send(String msg) {
        try {
            out.write(msg + "\n");
            out.flush();
        } catch (IOException e) {
        }
    }
}
