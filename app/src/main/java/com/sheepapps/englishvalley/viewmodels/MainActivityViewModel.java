package com.sheepapps.englishvalley.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.annotation.NonNull;

import com.sheepapps.englishvalley.R;
import com.sheepapps.englishvalley.helpers.Constants;


public class MainActivityViewModel extends ViewModel {

    private MutableLiveData<Integer> mItemId;
    private int mCategory = Constants.Categories.MIXED_CATEGORY;

    @NonNull
    public MutableLiveData<Integer> getItemId() {
        if (mItemId == null) {
            mItemId = new MutableLiveData<>();
            mItemId.setValue(R.id.action_all);
        }
        return mItemId;
    }

    public void setItemId(int id) {
        mItemId.setValue(id);
    }

    public int getCategory() {
        return mCategory;
    }

    public void setCategory(int category) {
        mCategory = category;
    }
}
