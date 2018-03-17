package com.ankush.lettersandus;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by nEW u on 9/13/2017.
 */

public class LnUsApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.setPersistenceEnabled(true);
    }
}
