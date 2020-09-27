package com.sheepapps.englishvalley.app;

import android.app.Application;
import android.content.SharedPreferences;
import android.support.multidex.MultiDexApplication;

import com.huma.room_for_asset.RoomAsset;
import com.sheepapps.englishvalley.databases.Migrations;
import com.sheepapps.englishvalley.databases.ValleyRoomDatabase;

import static com.sheepapps.englishvalley.databases.Migrations.MIGRATION_3_4;

public class ValleyApp extends MultiDexApplication {

    private static final String PREFS = "shared_pref";

    private static ValleyApp app;
    private ValleyRoomDatabase db;
    private SharedPreferences mPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        mPreferences = getSharedPreferences(PREFS, MODE_PRIVATE);
        db = RoomAsset.databaseBuilder(
                getApplicationContext(),
                ValleyRoomDatabase.class,
                "english_valley.db")
                .allowMainThreadQueries()
                .addMigrations(Migrations.MIGRATION_2_3, MIGRATION_3_4)
                .build();
    }

    public static ValleyApp getInstance() {
        return app;
    }

    public ValleyRoomDatabase getDb() {
        return db;
    }

    public SharedPreferences getPreferences() {
        return mPreferences;
    }
}
