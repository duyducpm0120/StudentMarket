package com.example.studentmarket.Controller.Message;

import java.util.Date;

public class FirebaseMessage {
    private String conversationId;
    private String msg;
    private String authorName;
    private String authorId;
    private Date date;

    public FirebaseMessage() {

    }

    public FirebaseMessage(String conversationId,String msg, String authorName, String authorId, Date date) {
        this.msg = msg;
        this.authorName = authorName;
        this.date = date;
        this.conversationId = conversationId;
        this.authorId = authorId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getconversationId() {
        return this.conversationId;
    }

    public void setconversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }
}
