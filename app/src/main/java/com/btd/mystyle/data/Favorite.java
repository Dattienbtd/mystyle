package com.btd.mystyle.data;

import android.util.Log;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;

/**
 * Created by dattien on 4/23/17.
 */

@IgnoreExtraProperties
public class Favorite {
    private String id;
    private String userid;
    private long shopid;
    private int level;

    public static ArrayList<Favorite> favorites = new ArrayList<>();

    public static ArrayList<Favorite> getInstance() {
        return favorites;
    }

    public static void setFavoriteSingleton(ArrayList<Favorite> itemSingleton) {
        favorites = itemSingleton;
    }

    public Favorite() {
    }

    public Favorite(String id, String userid, long shopid, int level) {
        this.id = id;
        this.userid = userid;
        this.shopid = shopid;
        this.level = level;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public long getShopid() {
        return shopid;
    }

    public void setShopid(long shopid) {
        this.shopid = shopid;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
