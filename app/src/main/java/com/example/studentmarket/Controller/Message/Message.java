package com.example.studentmarket.Controller.Message;

import androidx.annotation.Nullable;

import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.commons.models.IUser;
import com.stfalcon.chatkit.commons.models.MessageContentType;

import java.util.Date;

public class Message implements IMessage, MessageContentType.Image {
    private String id;
    private String text;
    private Author user;
    private Date date;
    private String image;

    public Message(String id, String text, Author user, Date date) {
        this.id = id;
        this.text = text;
        this.user = user;
        this.date = date;
    }

    public Message(String id, Author user, Date date, String image) {
        this.id = id;
        this.user = user;
        this.date = date;
        this.image = image;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setUser(Author user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public Author getUser() {
        return user;
    }

    @Override
    public Date getCreatedAt() {
        return date;
    }

    @Nullable
    @Override
    public String getImageUrl() {
        return image == null ? null : image;
    }
}
