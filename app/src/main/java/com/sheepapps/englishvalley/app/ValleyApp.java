package com.sheepapps.englishvalley.app;

import android.content.SharedPreferences;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import com.huma.room_for_asset.RoomAsset;
import com.sheepapps.englishvalley.databases.Migrations;
import com.sheepapps.englishvalley.databases.ValleyRoomDatabase;
import com.sheepapps.englishvalley.network.NetworkProvider;
import com.sheepapps.englishvalley.network.Repository;

public class ValleyApp extends MultiDexApplication {

    private static final String PREFS = "shared_pref";

    private static ValleyApp app;
    private ValleyRoomDatabase db;
    private SharedPreferences mPreferences;
    private Repository repository;

    @Override
    public void onCreate() {
        MultiDex.install(getApplicationContext());
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
        repository = new Repository(NetworkProvider.INSTANCE.getQuoteApi());
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

    public Repository getRepository() {
        return repository;
    }

}
