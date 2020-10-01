package com.sheepapps.englishvalley.databases.system;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

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
