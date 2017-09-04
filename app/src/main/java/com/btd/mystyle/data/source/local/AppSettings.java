package com.btd.mystyle.data.source.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.securepreferences.SecurePreferences;

import java.util.InputMismatchException;

/**
 * Created by dattien on 2/18/17.
 */

public class AppSettings {

    private static AppSettings appSettings;
    private static final String SOFT_KEY = "mat-khau-cho-shared-preferences";

    private SharedPreferences prefs;

    // Keys
    private static final String KEY_FIRST_RUN = "first_run";
    private static final String KEY_USER_ID = "user_id";


    // Objects
    private boolean isFirstRun;
    private String userId;


    public static AppSettings getInstance() {
        if (appSettings == null) {
            throw new InputMismatchException("Please setting AppSettings.init()"
                    + " in onCreate from Application class");
        }
        return appSettings;
    }

    public static void init(Context context) {
        if (appSettings == null) {
            appSettings = new AppSettings(context);
        }
    }

    private AppSettings(Context context) {
        //String password = GenerateSecretKey.with(context).setSoftSalt(SOFT_KEY).getSecretKey();
        prefs = new SecurePreferences(context.getApplicationContext(),
                SOFT_KEY,
                "mystyle_prefs.xml");
        preLoad();
    }

    private void preLoad() {
        isFirstRun = prefs.getBoolean(KEY_FIRST_RUN, true);
        userId = prefs.getString(KEY_USER_ID, "");
    }

    public boolean isFirstRun() {
        return isFirstRun;
    }

    public void setFirstRun(boolean firstRun) {
        isFirstRun = firstRun;
        prefs.edit().putBoolean(KEY_FIRST_RUN, isFirstRun).apply();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
        prefs.edit().putString(KEY_USER_ID, userId).apply();

    }
}
