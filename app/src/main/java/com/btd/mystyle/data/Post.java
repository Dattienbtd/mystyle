package com.btd.mystyle.data;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ServerValue;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by dattien on 2/19/17.
 */

@IgnoreExtraProperties
public class Post implements Serializable {
    private String id;
    private String title;
    private String description;
    private User user;
    private String userid;
    private String image;
    private ArrayList<Item> listItem;
    private ArrayList<Shop> listShop;
    private long date;
    private boolean status;

    public Post() {
    }

    public Post(String id, String title, String description,String userid,
                User user, String image,
                ArrayList<Item> listItem, ArrayList<Shop> listShop,
                long date, boolean status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.user = user;
        this.image = image;
        this.listItem = listItem;
        this.listShop = listShop;
        this.date = date;
        this.status = status;
        this.userid = userid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public ArrayList<Item> getListItem() {
        return listItem;
    }

    public ArrayList<Shop> getListShop() {
        return listShop;
    }

    public void setListItem(ArrayList<Item> listItem) {
        this.listItem = listItem;
    }

    public void setListShop(ArrayList<Shop> listShop) {
        this.listShop = listShop;
    }

    @Exclude
    public long getDateLong() {
        return date;
    }

    public java.util.Map<String, String> getDate() {
        return ServerValue.TIMESTAMP;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
