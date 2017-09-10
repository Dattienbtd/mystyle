package com.btd.mystyle.data;

import com.btd.mystyle.data.source.local.AppSettings;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

/**
 * Created by dattien on 2/4/17.
 */
@IgnoreExtraProperties
public class User implements Serializable {
    public static User user;
    private String id;
    private String email;
    private String username;
    private String image;
    private String brithday;
    private String provider;
    private String description;

    public User() {
    }

    public User(String id, String email, String username, String image, String brithday, String provider, String description) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.image = image;
        this.brithday = brithday;
        this.provider = provider;
        this.description = description;
    }

    public static User getInstance() {
        return user;
    }

    public static void setUserSingleton(User userSingleton) {
        user = userSingleton;
        AppSettings.getInstance().setUserId(userSingleton.getId());
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBrithday() {
        return brithday;
    }

    public void setBrithday(String brithday) {
        this.brithday = brithday;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getId() {
        if (id == null) {
            return AppSettings.getInstance().getUserId();
        }
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
