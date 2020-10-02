package com.sheepapps.englishvalley.app;

import android.app.Application;
import android.content.SharedPreferences;
import android.support.multidex.MultiDexApplication;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.huma.room_for_asset.RoomAsset;
import com.sheepapps.englishvalley.databases.Migrations;
import com.sheepapps.englishvalley.databases.ValleyRoomDatabase;
import com.sheepapps.englishvalley.helpers.MixedHelper;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ValleyApp extends MultiDexApplication {

    private static final String PREFS = "shared_pref";
    private static final String BASE_URL_INFO = "https://cat-fact.herokuapp.com/";

    private static ValleyApp app;
    private ValleyRoomDatabase db;
    private SharedPreferences mPreferences;
    private Retrofit retrofit;

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

        retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().create()))
                .baseUrl(BASE_URL_INFO)
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

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
