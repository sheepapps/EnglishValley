package com.sheepapps.englishvalley.helpers;

import android.content.res.Resources;

public class ScreenHelper {

    public int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

}
