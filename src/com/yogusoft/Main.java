package com.yogusoft;

import com.yogusoft.chatService.Message;
import com.yogusoft.chatService.MessagesList;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.List;

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
        private final Socket socket;

        public Chat(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            System.out.println("------------------------------------------");
            System.out.println("Connection received from " + socket.getInetAddress());

            try (ObjectInputStream objInputStream = new ObjectInputStream(socket.getInputStream());
                 ObjectOutputStream objOutputStream = new ObjectOutputStream(socket.getOutputStream())) {

                List<Message> messageList = MessagesList.getMessagesList();
                objOutputStream.writeObject(messageList);

                String username = (String) objInputStream.readObject();

                int lastMessageIndex = -1;
                String message = "";
                while(!message.equals("bye")){
                    List<Message> recentMessages = MessagesList.getRecentMessages(lastMessageIndex);
                    objOutputStream.writeObject(recentMessages);

                    message = (String) objInputStream.readObject();
                    boolean isMessage = message.startsWith("message:");

                    if(isMessage){
                        long currentTimeMillis = System.currentTimeMillis();
                        Date actualDate = new Date(currentTimeMillis);

                        message = message.substring("message:".length());

                        Message newMessage = new Message(username, message, actualDate);

                        MessagesList.addMessage(newMessage);
                        objOutputStream.writeObject(newMessage);

                        lastMessageIndex = (int) objInputStream.readObject();
                    } else {
                        if (message.equals("bye")){
                            objOutputStream.writeObject("goodbye :-)");
                        } else {
                            objOutputStream.writeObject("Error, you don't send a message");
                        }
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                String messageErr = e.getMessage();

                if (messageErr == null) {
                    e.printStackTrace();
                } else {
                    System.err.println("Error: " + e.getMessage());
                }
            }

            System.out.println("End Chat - " + socket.getInetAddress());
            System.out.println("------------------------------------------");
        }
    }
}
