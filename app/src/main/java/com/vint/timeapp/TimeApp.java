package com.vint.timeapp;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by ygrigortsevich on 01.07.16.
 */
public class TimeApp extends Application {
    private String REALM_NAME="timeapp.realm";

    @Override
    public void onCreate() {
        super.onCreate();
        initRealmConfiguration();
    }

    private void initRealmConfiguration() {
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .name(REALM_NAME)
                .schemaVersion(0)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
    }

}
