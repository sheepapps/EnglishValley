package com.sheepapps.englishvalley.views;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.ads.InterstitialAd;
import com.sheepapps.englishvalley.R;
import com.sheepapps.englishvalley.dialogs.MixedSettingsDialog;
import com.sheepapps.englishvalley.helpers.FactHelper;
import com.sheepapps.englishvalley.viewmodels.MainActivityViewModel;
import com.sheepapps.englishvalley.viewmodels.MenuItemViewModel;

public class MainActivity extends AppCompatActivity implements
        MenuItemViewModel.OnMenuItemClickedListener, BottomNavigationView.OnNavigationItemSelectedListener{

    private Fragment mCurrentFragment;
    private MainActivityViewModel mViewModel;
    private InterstitialAd mInterstitialAd;
    private int mMenuClicksCount = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        mViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        mViewModel.getItemId().observe(this, this::selectBottomItem);
        bottomNavigationView.setSelectedItemId(mViewModel.getItemId().getValue());
        initAds();
        initCatsFacts();
        getSupportActionBar().setTitle("My title");
    }

    private void initCatsFacts() {
        new FactHelper().initFacts();
    }

    private void initAds() {

    }

    private void selectBottomItem(int id) {
        switch (id) {
            case R.id.action_all:
                selectFragment(MenuFragment.newInstance());
                mMenuClicksCount++;
                if ((mMenuClicksCount > 5) && mInterstitialAd.isLoaded()) {
                    mMenuClicksCount = 0;
                    mInterstitialAd.show();
                }
                break;

            case R.id.action_current:
                selectFragment(CurrentFragment.newInstance(mViewModel.getCategory()));
                break;

            case R.id.action_favorite:
                selectFragment(FavoriteFragment.newInstance());
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (mCurrentFragment instanceof MenuFragment) {
            super.onBackPressed();
        } else {
            BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
            bottomNavigationView.setSelectedItemId(R.id.action_all);
        }
    }

    private void selectFragment(Fragment fragment) {
        if ((mCurrentFragment == null) || (mCurrentFragment.getClass() != fragment.getClass())) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if (mCurrentFragment != null) {
                transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
            }
            transaction.replace(R.id.fragmentContainer, fragment);
            mCurrentFragment = fragment;
            transaction.commit();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        checkForCrashes();
    }

    private void checkForCrashes() {
    }

    @Override
    public void onItemClicked(int category) {
        mViewModel.setCategory(category);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.action_current);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        mViewModel.setItemId(item.getItemId());
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about_menu: {
                startActivity(new Intent(this, AboutActivity.class));
                break;
            }
            case R.id.settings_menu: {
                new MixedSettingsDialog().show(getSupportFragmentManager(), null);
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
