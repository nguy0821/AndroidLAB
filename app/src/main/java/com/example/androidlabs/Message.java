package com.example.androidlabs;

public class Message {
    public String message;
    public boolean isSent;
    public long messageID;

    public Message(String message, boolean isSent , long id) {
        this.message = message;
        this.isSent = isSent;
        this.messageID= id;
    }

    public Message() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSent() {
        return isSent;
    }

    public void setSent(boolean send) {
        isSent = send;
    }

    public long getMessageID() {
        return messageID;
    }

    public void setMessageID(long messageID) {
        this.messageID = messageID;
    }
}
