package com.yogusoft;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        Socket socket;

        try (ServerSocket serverSockets = new ServerSocket(8080)) {
            System.out.println("Starting Chat Server");

            while (true) {
                socket = serverSockets.accept();
                Chat newChat = new Chat(socket);

                newChat.start();
            }

        } catch (IOException e) {
            String messageErr = e.getMessage();

            if (messageErr == null) {
                e.printStackTrace();
            } else {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }

    private static class Chat extends Thread {
        private Socket socket;

        public Chat(Socket socket) {
            this.socket = socket;
        }
    }
}
