package com.sheepapps.englishvalley.views;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sheepapps.englishvalley.R;
import com.sheepapps.englishvalley.adapters.CardAdapter;
import com.sheepapps.englishvalley.app.ValleyApp;
import com.sheepapps.englishvalley.data.QuoteRetrofit;
import com.sheepapps.englishvalley.databases.WordAbs;
import com.sheepapps.englishvalley.databinding.FragmentCurrentBinding;
import com.sheepapps.englishvalley.helpers.Constants;
import com.sheepapps.englishvalley.helpers.Event;
import com.sheepapps.englishvalley.viewmodels.CurrentViewModel;

import java.util.List;

public class CurrentFragment extends Fragment {

    public static final String KEY_CATEGORY = "com.sheep_apps.englishvalley.views.CurrentFragment.category";

    private CurrentViewModel mViewModel;
    private FragmentCurrentBinding mBinding;
    private int mCurrentCategoryId;
    private boolean mIsCategoryVisible;
    private CardAdapter adapter;
    private  boolean isLoaded = false;

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
        observeStatusLoading();
        mViewModel.init(mCurrentCategoryId);

    }

    private void observeStatusLoading(){
        mViewModel.responseLiveData.observe(this, new Observer<Event<List<QuoteRetrofit>>>() {
            @Override
            public void onChanged(@Nullable Event<List<QuoteRetrofit>> listEvent) {

                switch (listEvent.getStatus()){

                    case LOADING: {
                        Toast.makeText(CurrentFragment.super.getContext(),
                                "loading quotes...", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case SUCCESS: {
                        Toast.makeText(CurrentFragment.super.getContext(),
                                "loaded quotes", Toast.LENGTH_SHORT).show();
                        List<WordAbs> mWords = (List<WordAbs>) mViewModel.getWords();

                        isLoaded = true;

                        for(QuoteRetrofit quote: listEvent.getData()){
                            WordAbs word = new WordAbs();
                            word.main = quote.getText();
                            word.category = 13;
                            word.favorite = 0;
                            word.favoriteTime = 0;
                            word.sense = quote.getType();
                            mWords.add(word);

                        }

                        updateAdapter(mWords.size());
                        break;
                    }
                    case ERROR: {
                        Toast.makeText(CurrentFragment.super.getContext(),
                                "loading error", Toast.LENGTH_SHORT).show();
                        break;
                    }

                }

            }
        });
    }

    private void updateAdapter(int count){
        adapter.notifyDataSetChanged();
        mViewModel.totalWords.set(count);

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

        adapter = new CardAdapter(getChildFragmentManager(), isCategoryVisible);
        adapter.setList(mViewModel.getWords());

        mBinding.currentViewPager.setAdapter(adapter);
        mBinding.currentViewPager.addOnPageChangeListener(new WordsListener());
        mBinding.currentViewPager.setCurrentItem(mViewModel.getStorage().current);
        return mBinding.getRoot();
    }

    @Override
    public void onStop() {
        super.onStop();

        if(isLoaded && mBinding.currentViewPager.getCurrentItem() >=
                mViewModel.getWords().size() - 10){
            int position = mViewModel.getWords().size() - 11;

            mViewModel.getStorage().completed = position;
            mViewModel.getStorage().current = position;
            mViewModel.updateSystemDao();

        } else {
            mViewModel.saveData(mBinding.currentViewPager.getCurrentItem());
        }

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
