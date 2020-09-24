package com.sheepapps.englishvalley.databases;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.migration.Migration;
import android.support.annotation.NonNull;

public abstract class Migrations {

    public static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            String[] tables = {"abbreviations", "adjectives", "idioms", "jokes", "murphies",
                    "oxymorons", "palindromes", "philosophies", "proverbs", "quotes",
                    "riddles", "silents", "symbols", "tips", "tongue"};
            for (String tableName : tables) {
                database.execSQL("ALTER TABLE " + tableName + " ADD COLUMN favorite_time INTEGER NOT NULL DEFAULT 0");
            }
        }
    };
}
