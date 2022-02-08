package com.yogusoft.chatService;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {
    private static final long serialVersionUID = -3230242013206376350L;

    private String username;
    private String message;
    private Date dateMessage;

    public Message(String username, String message, Date dateMessage) {
        this.username = username;
        this.message = message;
        this.dateMessage = dateMessage;
    }
}
