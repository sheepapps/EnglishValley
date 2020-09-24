package com.sheepapps.englishvalley.databases.system;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "sys_description")
public class Description {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "table_name")
    public String tableName;

    public String descriptions;

    @NonNull
    public int category;
}
