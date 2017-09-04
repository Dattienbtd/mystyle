package com.btd.mystyle.data;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by dattien on 4/24/17.
 */

@IgnoreExtraProperties
public class Follow {
    private String userid;
    private String friendid;
    private int level;

    public Follow(String userid, String friendid, int level) {
        this.userid = userid;
        this.friendid = friendid;
        this.level = level;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getFriendid() {
        return friendid;
    }

    public void setFriendid(String friendid) {
        this.friendid = friendid;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
