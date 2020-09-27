package com.sheepapps.englishvalley.helpers;

public final class Constants {

    public static final class Categories {
        public static final int CATEGORY_ABBREVIATION = 1;
        public static final int CATEGORY_OPPOSITE = 2;
        public static final int CATEGORY_IDIOM = 3;
        public static final int CATEGORY_TIP = 4;
        public static final int CATEGORY_MURPHY = 5;
        public static final int CATEGORY_PROVERB = 6;
        public static final int CATEGORY_PHILOSOPHY = 7;
        public static final int CATEGORY_SYMBOL = 8;
        public static final int CATEGORY_TONGUE = 9;
        public static final int CATEGORY_PALINDROME = 10;
        public static final int CATEGORY_RIDDLE = 11;
        public static final int CATEGORY_OXYMORON = 12;
        public static final int CATEGORY_QUOTE = 13;
        public static final int CATEGORY_JOKE = 14;
        public static final int CATEGORY_SILENT = 15;
        public static final int MIXED_CATEGORY = -1;
        public static final int CATEGORY_FACT = 16;

        public static final int CATEGORIES_COUNT = 15;
        public static final String KEY_LAST_CATEGORY = "last_category";
    }

    public static final class SortingType {
        public static final int CATEGORY = 0;
        public static final int ADDING_DOWN = 1;
        public static final int ADDING_UP = 2;
    }

    public static final int MIXED_MAX_VALUE = 50;
    public static final int MIXED_MIN_VALUE = 5;
}
