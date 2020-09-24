package com.sheepapps.englishvalley.views;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.sheepapps.englishvalley.R;
import com.sheepapps.englishvalley.databases.WordAbs;
import com.sheepapps.englishvalley.databinding.ItemRowBinding;
import com.sheepapps.englishvalley.helpers.ShareCardHelper;
import com.sheepapps.englishvalley.viewmodels.CardViewModel;

public class CardFragment extends Fragment {

    public static final String ARGUMENT_WORD = "com.sheepapps.englishvalley.word";
    public static final String CATEGORY_VISIBILITY = "com.sheepapps.englishvalley.visibility";
    private CardViewModel mViewModel;
    private View viewToShare;

    public static CardFragment newInstance() {
        return new CardFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(CardViewModel.class);
        if (getArguments() != null) {
            WordAbs data = (WordAbs)getArguments().getSerializable(ARGUMENT_WORD);
            if (data == null) {
                return;
            }
            mViewModel.setWord(data);
            boolean isCategoryVisible = getArguments().getBoolean(CATEGORY_VISIBILITY);
            mViewModel.setCategoryVisibility(isCategoryVisible ? View.VISIBLE : View.GONE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ItemRowBinding binding = DataBindingUtil
                .inflate(inflater, R.layout.item_row, container, false);
        viewToShare = binding.getRoot().findViewById(R.id.cardView);
        mViewModel.getShareSelected().observe(this, v -> {
            ShareCardHelper shareCardHelper = new ShareCardHelper();
            shareCardHelper.shareImage(shareCardHelper.loadBitmapFromView(viewToShare));
        });
        binding.setViewModel(mViewModel);
        checkAnimation(binding.getRoot());
        return binding.getRoot();
    }

    private void checkAnimation(View root) {
        if (mViewModel.isCardAnimated()) {
            final TextView sense = root.findViewById(R.id.card_sense);
            final Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.extra_word_animation);
            mViewModel.setOnClickListener(() -> {
                mViewModel.cancelAnimation();
                sense.startAnimation(animation);
            });
        }
    }
}


