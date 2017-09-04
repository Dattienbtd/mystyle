package com.btd.mystyle;

import android.app.Application;

import com.btd.mystyle.data.source.local.AppSettings;
import com.facebook.FacebookSdk;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by dattien on 2/5/17.
 */

public class App extends Application {
    private static Application mApplication;
    private static DatabaseReference mDatabase;
    private static StorageReference mStore;

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        FacebookSdk.sdkInitialize(this);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mStore = FirebaseStorage.getInstance().getReference();
        AppSettings.init(this);
    }

    public static Application getInstance() {
        return mApplication;
    }

    public static DatabaseReference getDatabase() {
        return mDatabase;
    }

    public static StorageReference getStore() {
        return mStore;
    }
}
