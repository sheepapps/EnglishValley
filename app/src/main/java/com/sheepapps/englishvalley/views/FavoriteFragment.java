package com.sheepapps.englishvalley.views;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.sheepapps.englishvalley.R;
import com.sheepapps.englishvalley.adapters.FavoriteAdapter;
import com.sheepapps.englishvalley.app.ValleyApp;
import com.sheepapps.englishvalley.dialogs.CategoriesListDialog;
import com.sheepapps.englishvalley.helpers.Constants;
import com.sheepapps.englishvalley.helpers.PreferencesHelper;
import com.sheepapps.englishvalley.viewmodels.FavouritesViewModel;

public class FavoriteFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private FavoriteAdapter mAdapter;
    private FavouritesViewModel mViewModel;
    private TextView mEmptyLabel;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    public static FavoriteFragment newInstance() {
        return new FavoriteFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(FavouritesViewModel.class);
        mViewModel.init();
        mAdapter = new FavoriteAdapter(mViewModel.getFavorites(PreferencesHelper.getFavoriteCategory()), this);
        getLifecycle().addObserver(mAdapter);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.findItem(R.id.sorting_menu).setVisible(true);
        int sortingTypeSet = mViewModel.getSortingType();
        switch (sortingTypeSet) {
            case Constants.SortingType.CATEGORY: {
                Drawable drawable = menu.findItem(R.id.category_menu).getIcon();
                drawable.mutate();
                drawable.setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
            } break;
            case Constants.SortingType.ADDING_DOWN: {
                Drawable drawable = menu.findItem(R.id.date_down_menu).getIcon();
                drawable.mutate();
                drawable.setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
            } break;
            case Constants.SortingType.ADDING_UP: {
                Drawable drawable = menu.findItem(R.id.date_up_menu).getIcon();
                drawable.mutate();
                drawable.setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.category_menu: {
                mViewModel.setSortingType(Constants.SortingType.CATEGORY);
            } break;
            case R.id.date_down_menu: {
                mViewModel.setSortingType(Constants.SortingType.ADDING_DOWN);
            } break;
            case R.id.date_up_menu: {
                mViewModel.setSortingType(Constants.SortingType.ADDING_UP);
            } break;
        }
        if (getActivity() != null) {
            getActivity().invalidateOptionsMenu();
        }
        return false;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_favorite, container, false);
        mRecyclerView = root.findViewById(R.id.favorite_recycler_view);
        mRecyclerView.setAdapter(mAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(container.getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        layoutManager.scrollToPositionWithOffset(mViewModel.getFavouritesIndex(), mViewModel.getFavouritesPosition());
        mEmptyLabel = root.findViewById(R.id.empty_text);
        if (mAdapter.getItemCount() > 0) {
            mEmptyLabel.setVisibility(View.GONE);
        }
        root.findViewById(R.id.categories_fab).setOnClickListener(view -> showCategoriesListDialog());
        mViewModel.getDataReloaded().observe(this, aBoolean -> mAdapter
                .setFavouritesList(mViewModel.getFavorites(mViewModel.getCurrentCategory())));
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() instanceof AppCompatActivity) {
            mViewModel.getTitle().observe(this, (id) -> ((AppCompatActivity) getActivity())
                    .getSupportActionBar().setTitle(getTitleById(id)));
        }
    }

    private String getTitleById(Integer id) {
        String title;
        if (id == null) {
            title = getString(R.string.bottom_favourite);
        } else if (id > 0 && id <= Constants.Categories.CATEGORIES_COUNT) {
            title = ValleyApp.getInstance().getDb().systemDao().getStorageByCategoryId(id).name;
        } else {
            title = getString(R.string.bottom_favourite);
        }
        return title;
    }

    private void showCategoriesListDialog() {
        CategoriesListDialog categoriesListDialog = new CategoriesListDialog();
        categoriesListDialog.getChosenCategory().observe(this, id -> {
            mAdapter.setFavouritesList(mViewModel.getFavorites(id));
            PreferencesHelper.saveFavoriteCategory(id);
            if (mAdapter.getItemCount() > 0) {
                mEmptyLabel.setVisibility(View.GONE);
            } else {
                mEmptyLabel.setVisibility(View.VISIBLE);
            }
        });
        categoriesListDialog.show(getChildFragmentManager(), null);
    }

    private void initAds(final AdView adView) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mViewModel.saveFavouritesIndex(((LinearLayoutManager)mRecyclerView.getLayoutManager())
                .findFirstVisibleItemPosition());
        View view = mRecyclerView.getChildAt(0);
        mViewModel.saveFavouritesPosition(view == null ? 0 : (view.getTop() - mRecyclerView
                .getPaddingTop()));
    }
}
