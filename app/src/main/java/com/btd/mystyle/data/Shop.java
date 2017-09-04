package com.btd.mystyle.data;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by dattien on 3/20/17.
 */

@IgnoreExtraProperties
public class Shop implements Serializable {
    private long id;
    private String name;
    private String icon;
    private String link;
    private int level;
    private boolean isFavorite;

    private static ArrayList<Shop> shops;

    public static ArrayList<Shop> getInstance() {
        return shops;
    }

    public static void setShopSingleton(ArrayList<Shop> shopSingleton) {
        shops = shopSingleton;
    }

    public Shop() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
