package com.btd.mystyle.data;

import android.util.Log;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by dattien on 3/11/17.
 */

@IgnoreExtraProperties
public class Item implements Serializable {
    private long id;
    private String name;
    private String icon;
    private String title;

    public static ArrayList<Item> items = new ArrayList<>();

    public static ArrayList<Item> getInstance() {
        Log.e("DAT","aaaa"+items.size());
        return items;
    }

    public static void setItemSingleton(ArrayList<Item> itemSingleton) {
        Log.e("DAT","pppppp"+itemSingleton.size());
        items = itemSingleton;
        Log.e("DAT","vvv"+items.size());
    }

    public Item() {
    }

    public Item(long id, String name, String icon, String title) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.title = title;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
