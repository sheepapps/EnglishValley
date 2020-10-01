package com.sheepapps.englishvalley.viewmodels;

import android.content.Context;
import androidx.databinding.BaseObservable;
import androidx.databinding.ObservableField;
import android.graphics.drawable.Drawable;

import com.sheepapps.englishvalley.databases.system.Storage;
import com.sheepapps.englishvalley.helpers.Constants;

public class MenuItemViewModel extends BaseObservable {

    public ObservableField<Drawable> background = new ObservableField<>();
    public ObservableField<String> category = new ObservableField<>();
    public ObservableField<String> stringNumber = new ObservableField<>();

    private Storage mStorage;
    private OnMenuItemClickedListener mListener;

    public MenuItemViewModel(Context context) {
        if (context instanceof OnMenuItemClickedListener) {
            mListener = (OnMenuItemClickedListener)context;
        }
    }

    public void setStorage(Storage storage) {
        mStorage = storage;
        stringNumber.set("");
        category.set(storage.name);
        if (mStorage.id != Constants.Categories.MIXED_CATEGORY) {
            if (mStorage.completed != 0) {
                stringNumber.set((mStorage.completed + 1) + "/" + mStorage.total);
            } else {
                stringNumber.set(mStorage.completed + "/" + mStorage.total);
            }
        }
    }

    public void emptyItem() {
        category.set("");
        stringNumber.set("");
        mListener = null;
    }

    public void setBackground(Drawable drawable) {
        background.set(drawable);
    }

    public void onClick() {
        if (mListener != null) {
            mListener.onItemClicked(mStorage.id);
        }
    }

    public interface OnMenuItemClickedListener {
        void onItemClicked(int category);
    }
}
