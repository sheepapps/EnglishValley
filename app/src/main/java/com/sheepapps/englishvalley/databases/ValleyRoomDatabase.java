package com.sheepapps.englishvalley.databases;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.sheepapps.englishvalley.databases.system.Animation;
import com.sheepapps.englishvalley.databases.system.Description;
import com.sheepapps.englishvalley.databases.system.Storage;
import com.sheepapps.englishvalley.databases.system.SystemDao;

@Database(entities = {
        Abbreviation.class,
        Adjective.class,
        Idiom.class,
        Jokee.class,
        Murphy.class,
        Oxymoron.class,
        Palindrome.class,
        Philosophy.class,
        Proverb.class,
        Quote.class,
        Riddle.class,
        Silent.class,
        Symbol.class,
        Tippp.class,
        Tongue.class,
        Animation.class,
        Description.class,
        Storage.class},
        version = 3, exportSchema = false)

public abstract class ValleyRoomDatabase extends RoomDatabase {
    public abstract WordDao wordsDao();
    public abstract SystemDao systemDao();
}