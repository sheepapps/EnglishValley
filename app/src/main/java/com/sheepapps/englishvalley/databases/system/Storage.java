package com.sheepapps.englishvalley.databases.system;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "sys_storage")
public class Storage {

    @PrimaryKey
    @NonNull
    public int id;

    @NonNull
    @ColumnInfo(name = "table_name")
    public String tableName;

    @NonNull
    public int completed;

    @NonNull
    public int total;

    @NonNull
    public String name;

    @NonNull
    public int current;

}
