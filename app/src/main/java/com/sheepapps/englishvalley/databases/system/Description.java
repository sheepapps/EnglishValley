package com.sheepapps.englishvalley.databases.system;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

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
