package com.WizGuys.eStudent.helperClass.firebase;

import android.app.Application;

import com.firebase.client.Firebase;

public class FireBase extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
