package com.sheepapps.englishvalley.app;

import android.app.Application;
import android.content.SharedPreferences;
import com.huma.room_for_asset.RoomAsset;
import com.sheepapps.englishvalley.API.RetrofitInstance;
import com.sheepapps.englishvalley.databases.Migrations;
import com.sheepapps.englishvalley.databases.ValleyRoomDatabase;

public class ValleyApp extends Application {

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
                .addMigrations(Migrations.MIGRATION_2_3)
                .build();
        RetrofitInstance.INSTANCE.getExtra();
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
