package com.sheepapps.englishvalley.databases;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;

public class WordAbs implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "_id")
    public long id;

    @NonNull
    public String main = "";

    public String sense;

    public String example;

    public String extra;

    @NonNull
    public int category;

    @NonNull
    public int favorite;

    @NonNull
    @ColumnInfo(name = "favorite_time")
    public long favoriteTime;
}