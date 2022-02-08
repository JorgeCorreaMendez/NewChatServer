package com.yogusoft.chatService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MessagesList {
    private static List<Message> messagesList = Collections.synchronizedList(new ArrayList<>());

    public static synchronized void addMessage(Message newMessage) {
        messagesList.add(newMessage);
    }

    public static List<Message> getRecentMessages(int lastMessageIndex) {
        List<Message> recentMessages = new ArrayList<>();

        if(!messagesList.isEmpty() && lastMessageIndex != -1){
            for(int i = lastMessageIndex; i < (messagesList.size() - 1); i++){
                recentMessages.add(messagesList.get(i));
            }
        }

        return recentMessages;
    }

    public static List<Message> getMessagesList() {
        return messagesList;
    }
}
