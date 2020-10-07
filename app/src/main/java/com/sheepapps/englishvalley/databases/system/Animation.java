package com.sheepapps.englishvalley.databases.system;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "sys_animation")
public class Animation {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "table_name")
    public String tableName;

    public String descriptions;
}
