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

    public static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE 'catsFacts' ('extra'TEXT, 'favorite_time' INTEGER NOT NULL DEFAULT 0, main TEXT NOT NULL DEFAULT 0, sense TEXT, _id INTEGER NOT NULL DEFAULT 0 PRIMARY KEY DEFAULT 1, category INTEGER NOT NULL DEFAULT 0, favorite INTEGER NOT NULL DEFAULT 0, example TEXT)");
//            for (int i=0; i!=10; i++){
//                database.execSQL("INSERT INTO 'catsFacts' (_id, extra, favorite_time, main, sense, category, favorite, example) VALUES($i, null, 0, 'main', null, 16, 0, null);");
//            }
            database.execSQL("INSERT INTO 'sys_storage' (id, table_name, completed, total, name, current) VALUES(16, 'catsFacts', 0, 10, 'CatsFacts', 0);");
        }
    };
}
