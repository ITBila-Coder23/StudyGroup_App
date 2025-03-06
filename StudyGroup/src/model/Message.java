package model;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {
    private String content;
    private User sender;
    private Date timestamp;

    public Message(String content, User sender) {
        this.content = content;
        this.sender = sender;
        this.timestamp = new Date();
    }

    public String getContent() {
        return content;
    }

    public User getSender() {
        return sender;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "[" + timestamp + "] " + sender.getUsername() + ": " + content;
    }
}
