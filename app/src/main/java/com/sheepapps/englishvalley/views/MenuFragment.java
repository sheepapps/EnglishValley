package com.sheepapps.englishvalley.views;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gjiazhe.panoramaimageview.GyroscopeObserver;
import com.sheepapps.englishvalley.R;
import com.sheepapps.englishvalley.adapters.MenuAdapter;
import com.sheepapps.englishvalley.databinding.FragmentMenuBinding;
import com.sheepapps.englishvalley.network.QuotesRepositoryKt;
import com.sheepapps.englishvalley.viewmodels.MenuViewModel;


public class MenuFragment extends Fragment {

    private GyroscopeObserver gyroscopeObserver;
    private FragmentMenuBinding mBinding;
    private MenuViewModel mViewModel;

    public MenuFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        return new MenuFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MenuViewModel.class);
        gyroscopeObserver = new GyroscopeObserver();
        gyroscopeObserver.setMaxRotateRadian(Math.PI / 2);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (getActivity() instanceof AppCompatActivity) {
            AppCompatActivity activity = (AppCompatActivity) getActivity();
            activity.getSupportActionBar().setTitle(activity.getString(R.string.app_name));
        }
        mBinding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_menu, container, false);
        mBinding.menuBackground.setGyroscopeObserver(gyroscopeObserver);

        RecyclerView menuRecyclerView = mBinding.menuRecyclerView;
        menuRecyclerView.setAdapter(new MenuAdapter());

        GridLayoutManager manager = new GridLayoutManager(getContext(), MenuAdapter.sColumnCount);
        menuRecyclerView.setLayoutManager(manager);
        manager.scrollToPositionWithOffset(mViewModel.getMenuIndex(), mViewModel.getMenuPosition());
        if (QuotesRepositoryKt.getQuoteList().isEmpty())
            QuotesRepositoryKt.getQuotes();
        return mBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        gyroscopeObserver.register(getActivity());
    }

    @Override
    public void onStop() {
        super.onStop();
        gyroscopeObserver.unregister();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mViewModel.saveMenuIndex(((LinearLayoutManager) mBinding.menuRecyclerView.getLayoutManager())
                .findFirstVisibleItemPosition());
        View view = mBinding.menuRecyclerView.getChildAt(0);
        mViewModel.saveMenuPosition((view == null) ? 0 : (view.getTop() - mBinding.menuRecyclerView
                .getPaddingTop()));
    }
}
