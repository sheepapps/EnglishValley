package com.sheepapps.englishvalley.adapters;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.sheepapps.englishvalley.R;
import com.sheepapps.englishvalley.app.ValleyApp;
import com.sheepapps.englishvalley.databases.system.Storage;
import com.sheepapps.englishvalley.databinding.MenuItemRawBinding;
import com.sheepapps.englishvalley.helpers.FactHelper;
import com.sheepapps.englishvalley.helpers.MixedHelper;
import com.sheepapps.englishvalley.helpers.ScreenHelper;
import com.sheepapps.englishvalley.viewmodels.MenuItemViewModel;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {

    private String[] mColors;
    public static int sColumnCount;
    private final List<Storage> mStorages;

    public MenuAdapter() {
        ValleyApp app = ValleyApp.getInstance();
        mStorages = app.getDb().systemDao().getAllStorage();
        mStorages.add(0, MixedHelper.getInstance().getMixedStorage());
        mStorages.add(1, FactHelper.INSTANCE.getFactStorage());
        sColumnCount = app.getResources().getInteger(R.integer.menu_col_count);
        mColors = app.getResources().getStringArray(R.array.menu_colors);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private MenuItemRawBinding mBinding;
        private MenuItemViewModel mViewModel;

        private ViewHolder(MenuItemRawBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
            mViewModel = new MenuItemViewModel(mBinding.getRoot().getContext());
            mBinding.setViewModel(mViewModel);
            int scWidth = new ScreenHelper().getScreenWidth() / sColumnCount;
            ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(scWidth + 2, scWidth);
            mBinding.getRoot().setLayoutParams(layoutParams);
        }

        private void bind(int position) {
            mViewModel.setBackground(new ColorDrawable(Color.parseColor(mColors[position])));
            mViewModel.setStorage(mStorages.get(position));
        }

        private void emptyBind(int position) {
            mViewModel.setBackground(new ColorDrawable(Color.parseColor(mColors[position])));
            mViewModel.emptyItem();
        }
    }

    @NonNull
    @Override
    public MenuAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        MenuItemRawBinding binding = DataBindingUtil
                .inflate(inflater, R.layout.menu_item_raw, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public int getItemCount() {
        return mColors.length;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        if (!(position == mStorages.size())) {
            viewHolder.bind(position);
        } else {
            viewHolder.emptyBind(position);
        }
    }
}
