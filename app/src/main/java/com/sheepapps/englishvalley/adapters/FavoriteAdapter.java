package com.sheepapps.englishvalley.adapters;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.sheepapps.englishvalley.R;
import com.sheepapps.englishvalley.app.ValleyApp;
import com.sheepapps.englishvalley.databases.WordAbs;
import com.sheepapps.englishvalley.databinding.CardElementBinding;
import com.sheepapps.englishvalley.helpers.ShareCardHelper;
import com.sheepapps.englishvalley.viewmodels.CardViewModel;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> implements LifecycleObserver {

    private List<? extends WordAbs> mList;
    private Fragment mFragment;

    public FavoriteAdapter(List<? extends WordAbs> favorites, Fragment parentFragment) {
        mList = favorites;
        mFragment = parentFragment;
    }

    @NonNull
    @Override
    public FavoriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CardElementBinding binding = DataBindingUtil
                .inflate(inflater, R.layout.card_element, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteAdapter.ViewHolder holder, int position) {
        holder.bind(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements CardViewModel.OnFavoriteClickListener {

        private CardElementBinding mBinding;
        private CardViewModel mViewModel;

        private ViewHolder(CardElementBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
            mViewModel = new CardViewModel(ValleyApp.getInstance());
            mViewModel.setFavoriteListener(this);
            mBinding.setViewModel(mViewModel);

            View viewToShare = binding.getRoot().findViewById(R.id.cardView);
            mViewModel.getShareSelected().observe(mFragment, v -> {
                ShareCardHelper shareCardHelper = new ShareCardHelper();
                shareCardHelper.shareImage(shareCardHelper.loadBitmapFromView(viewToShare));
            });
            mViewModel.setCategoryVisibility(View.VISIBLE);
        }

        private void bind(WordAbs wordAbs) {
            mBinding.getViewModel().setWord(wordAbs);
        }

        @Override
        public void onFavoriteClicked() {
            int pos = getAdapterPosition();
            mList.remove(pos);
            notifyItemRemoved(pos);
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void clear() {
        mFragment = null;
    }

    public void setFavouritesList(List<? extends WordAbs> favoritesList) {
        mList = favoritesList;
        notifyDataSetChanged();
    }
}
