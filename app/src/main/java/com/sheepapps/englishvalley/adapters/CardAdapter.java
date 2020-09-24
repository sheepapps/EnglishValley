package com.sheepapps.englishvalley.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.sheepapps.englishvalley.databases.WordAbs;
import com.sheepapps.englishvalley.views.CardFragment;

import java.util.ArrayList;
import java.util.List;

public class CardAdapter extends FragmentStatePagerAdapter {

    private List<? extends WordAbs> mWords;
    private boolean mIsCategoryVisible;

    public CardAdapter(FragmentManager fm, boolean isCategoryVisible) {
        super(fm);
        mWords = new ArrayList<>();
        mIsCategoryVisible = isCategoryVisible;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = CardFragment.newInstance();
        Bundle args = new Bundle();
        WordAbs wordAbs = mWords.get(position);
        args.putSerializable(CardFragment.ARGUMENT_WORD, wordAbs);
        args.putBoolean(CardFragment.CATEGORY_VISIBILITY, mIsCategoryVisible);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return mWords.size();
    }

    public void setList(List<? extends WordAbs> list) {
        mWords = list;
        notifyDataSetChanged();
    }
}
