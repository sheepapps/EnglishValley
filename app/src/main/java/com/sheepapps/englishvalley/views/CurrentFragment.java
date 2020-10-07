package com.sheepapps.englishvalley.views;

import androidx.lifecycle.ViewModelProviders;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sheepapps.englishvalley.R;
import com.sheepapps.englishvalley.adapters.CardAdapter;
import com.sheepapps.englishvalley.app.ValleyApp;
import com.sheepapps.englishvalley.databinding.FragmentCurrentBinding;
import com.sheepapps.englishvalley.helpers.Constants;
import com.sheepapps.englishvalley.viewmodels.CurrentViewModel;

public class CurrentFragment extends Fragment {

    public static final String KEY_CATEGORY = "com.sheep_apps.englishvalley.views.CurrentFragment.category";

    private CurrentViewModel mViewModel;
    private FragmentCurrentBinding mBinding;
    private int mCurrentCategoryId;
    private boolean mIsCategoryVisible;

    public static Fragment newInstance(int type) {
        Fragment fragment = new CurrentFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_CATEGORY, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(CurrentViewModel.class);
        mCurrentCategoryId = Constants.Categories.MIXED_CATEGORY;
        if (getArguments() != null) {
            mCurrentCategoryId = getArguments().getInt(KEY_CATEGORY, Constants.Categories.MIXED_CATEGORY);
        }
        mViewModel.init(mCurrentCategoryId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String title;
        boolean isCategoryVisible = false;
        if (mCurrentCategoryId == Constants.Categories.MIXED_CATEGORY) {
            title = "Mixed";
            isCategoryVisible = true;
        } else {
            title = ValleyApp.getInstance().getDb().systemDao()
                    .getStorageByCategoryId(mCurrentCategoryId).name;
        }

        ((MainActivity)getActivity()).getSupportActionBar().setTitle(title);
        mBinding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_current, container, false);
        mBinding.setViewModel(mViewModel);
        initAds(mBinding.getRoot());

        CardAdapter adapter = new CardAdapter(getChildFragmentManager(), isCategoryVisible);
        adapter.setList(mViewModel.getWords());
        mBinding.currentViewPager.setAdapter(adapter);
        mBinding.currentViewPager.addOnPageChangeListener(new WordsListener());
        mBinding.currentViewPager.setCurrentItem(mViewModel.getStorage().current);
        return mBinding.getRoot();
    }

    @Override
    public void onStop() {
        super.onStop();
        mViewModel.saveData(mBinding.currentViewPager.getCurrentItem());
    }

    private void initAds(View root) {

    }

    private class WordsListener extends ViewPager.SimpleOnPageChangeListener {

        private int pagesCount = 0;

        @Override
        public void onPageSelected(int position) {
            mViewModel.currentWord.set(position);
            mViewModel.updateCount(position);
            pagesCount++;
            if ((pagesCount > 15)) {
                pagesCount = 0;
            }
        }
    }
}
