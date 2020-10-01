package com.sheepapps.englishvalley.viewmodels;


import androidx.lifecycle.ViewModel;
import com.sheepapps.englishvalley.helpers.PreferencesHelper;

public class MenuViewModel extends ViewModel {

    public void saveMenuPosition(int position) {
        PreferencesHelper.saveMenuPosition(position);
    }

    public int getMenuPosition() {
        return PreferencesHelper.getMenuPosition();
    }

    public void saveMenuIndex(int index) {
        PreferencesHelper.saveMenuIndex(index);
    }

    public int getMenuIndex() {
        return PreferencesHelper.getMenuIndex();
    }
}
