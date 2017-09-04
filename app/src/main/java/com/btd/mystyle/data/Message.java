package com.btd.mystyle.data;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

/**
 * Created by dattien on 3/11/17.
 */

@IgnoreExtraProperties
public class Message implements Serializable {
    private long id;
    private String title;
    private String description;
    private long date;
    private String userid;
    private long postid;

    public Message() {
    }

    public Message(long id, String title, String description, long date, String userid, long postid) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.userid = userid;
        this.postid = postid;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public long getPostid() {
        return postid;
    }

    public void setPostid(long postid) {
        this.postid = postid;
    }
}
