package com.sheepapps.englishvalley.databases;

import androidx.room.Entity;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "quotes")
public class Quote extends WordAbs {

    @NotNull
    public static Quote getQuote(String nMain, String nExtra,
                                 int nCategory) {
        Quote nWordAbs = new Quote();
        nWordAbs.main = nMain;
        nWordAbs.extra = nExtra;
        nWordAbs.category = nCategory;

        return nWordAbs;
    }
}
