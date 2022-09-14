package client;

import java.io.*;
import java.net.Socket;

public class Client {
    private Socket socket;
    private BufferedReader in;
    private BufferedWriter out;
    private BufferedReader inputUser;
    private String host;
    private int port;
    private String nickname;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;

        try {

            this.socket = new Socket(host, port);
            inputUser = new BufferedReader(new InputStreamReader(System.in));
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            nickName();
            new ReadMsg().start();
            new WriteMsg().start();

        } catch (IOException e) {
            downService();
        }
    }

    private class ReadMsg extends Thread {
        @Override
        public void run() {
            String msg;
            try {
                while (true) {
                    msg = in.readLine();
                    System.out.println(msg);
                }
            } catch (IOException ex) {
                Client.this.downService();
            }
        }
    }

    private class WriteMsg extends Thread {
        @Override
        public void run() {
            String msg;
            try {
                while (true) {
                    msg = inputUser.readLine();
                    if (msg.equals("exit")) {
                        out.write("exit" + "\n");
                        out.flush();
                        Client.this.downService();
                        break;
                    } else {
                        out.write(nickname + " : " + msg + "\n");
                    }
                    out.flush();
                }
            } catch (IOException ex) {
                Client.this.downService();
            }
        }
    }

    private void nickName() {
        System.out.print("Введите имя: ");
        try {
            nickname = inputUser.readLine();
            out.write("Здравствуйте: " + nickname + "\n");
            out.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void downService() {
        try {
            if (!socket.isClosed()) {
                socket.close();
                in.close();
                out.close();
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
