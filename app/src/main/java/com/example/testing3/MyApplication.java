package com.example.testing3;

import android.app.Application;

import com.example.testing3.realm.RealmDataMigration;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().name("myrealm.realm")
                .schemaVersion(3).migration(new RealmDataMigration()).build();

        Realm.setDefaultConfiguration(config);

    }
}
