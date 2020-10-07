package com.sheepapps.englishvalley.databases.system;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface SystemDao {

    @Query("SELECT * FROM sys_animation WHERE table_name = :tableName")
    List<Animation> getAnimationForTable(String tableName);

    @Query("SELECT table_name FROM sys_description")
    List<String> getDescriptionTables();

    @Query("SELECT descriptions FROM sys_description WHERE table_name = :tableName")
    String getDescriptionByTableName(String tableName);

    @Query("SELECT descriptions FROM sys_description WHERE category = :category")
    String getDescriptionByCategory(int category);

    @Query("SELECT * FROM sys_storage")
    List<Storage> getAllStorage();

    @Query("SELECT * FROM sys_storage WHERE id = :category")
    Storage getStorageByCategoryId(int category);

    @Update
    void updateStorage(Storage storage);
}
