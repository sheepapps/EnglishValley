package com.sheepapps.englishvalley.helpers;

import com.sheepapps.englishvalley.app.ValleyApp;

public final class PreferencesHelper {

    private static final String SORTING_TYPE = "com.sheepapps.englishvalley.SORTING_TYPE";
    private static final String LAST_FAVORITE = "com.sheepapps.englishvalley.LAST_FAVORITE";
    private static final String MIXED_MAX_VALUE = "com.sheepapps.englishvalley.MIXED_MAX_VALUE";
    private static final String MENU_POSITION = "com.sheepapps.englishvalley.MENU_POSITION";
    private static final String MENU_INDEX = "com.sheepapps.englishvalley.MENU_INDEX";
    private static final String FAVOURITES_POSITION = "com.sheepapps.englishvalley.FAVOURITES_POSITION";
    private static final String FAVOURITES_INDEX = "com.sheepapps.englishvalley.FAVOURITES_INDEX";

    public static void saveSortingType(int sortingType) {
        ValleyApp.getInstance().getPreferences().edit().putInt(SORTING_TYPE, sortingType).apply();
    }

    public static int getSortingType() {
        return ValleyApp.getInstance().getPreferences().getInt(SORTING_TYPE, 0);
    }

    public static void saveFavoriteCategory(int id) {
        ValleyApp.getInstance().getPreferences().edit().putInt(LAST_FAVORITE, id).apply();
    }

    public static int getFavoriteCategory() {
        return ValleyApp.getInstance().getPreferences().getInt(LAST_FAVORITE, 0);
    }

    public static void saveMixedMaxValue(int value) {
        ValleyApp.getInstance().getPreferences().edit().putInt(MIXED_MAX_VALUE, value).apply();
    }

    public static int getMixedMaxValue() {
        return ValleyApp.getInstance().getPreferences().getInt(MIXED_MAX_VALUE, 25);
    }

    public static void saveMenuPosition(int value) {
        ValleyApp.getInstance().getPreferences().edit().putInt(MENU_POSITION, value).apply();
    }

    public static int getMenuPosition() {
        return ValleyApp.getInstance().getPreferences().getInt(MENU_POSITION, 0);
    }

    public static void saveMenuIndex(int index) {
        ValleyApp.getInstance().getPreferences().edit().putInt(MENU_INDEX, index).apply();
    }

    public static int getMenuIndex() {
        return ValleyApp.getInstance().getPreferences().getInt(MENU_INDEX, 0);
    }

    public static void saveFavouritesPosition(int value) {
        ValleyApp.getInstance().getPreferences().edit().putInt(FAVOURITES_POSITION, value).apply();
    }

    public static int getFavouritesPosition() {
        return ValleyApp.getInstance().getPreferences().getInt(FAVOURITES_POSITION, 0);
    }

    public static void saveFavouritesIndex(int index) {
        ValleyApp.getInstance().getPreferences().edit().putInt(FAVOURITES_INDEX, index).apply();
    }

    public static int getFavouritesIndex() {
        return ValleyApp.getInstance().getPreferences().getInt(FAVOURITES_INDEX, 0);
    }
}
