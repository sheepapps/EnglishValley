package com.sheepapps.englishvalley.dialogs;

import androidx.lifecycle.ViewModelProviders;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.sheepapps.englishvalley.R;
import com.sheepapps.englishvalley.databinding.DialogMixedSettingsBinding;
import com.sheepapps.englishvalley.viewmodels.MixedSettingDialogViewModel;
import fr.tvbarthel.lib.blurdialogfragment.SupportBlurDialogFragment;

public class MixedSettingsDialog extends SupportBlurDialogFragment {

    private MixedSettingDialogViewModel mViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        DialogMixedSettingsBinding binding = DataBindingUtil
                .inflate(inflater, R.layout.dialog_mixed_settings, container, false);
        mViewModel = ViewModelProviders.of(this).get(MixedSettingDialogViewModel.class);
        binding.setViewModel(mViewModel);
        mViewModel.init();
        return binding.getRoot();
    }
}
