package com.sheepapps.englishvalley.viewmodels;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import com.sheepapps.englishvalley.helpers.Constants;
import com.sheepapps.englishvalley.helpers.PreferencesHelper;

public class MixedSettingDialogViewModel extends ViewModel {

    public ObservableInt progress = new ObservableInt();
    public ObservableField<String> progressText = new ObservableField<>();

    public void init() {
        int mixedMaxValue = PreferencesHelper.getMixedMaxValue();
        progress.set(mixedMaxValue - Constants.MIXED_MIN_VALUE);
        progressText.set(mixedMaxValue + "/" + Constants.MIXED_MAX_VALUE);
    }

    public void onProgressChanged(int progressValue) {
        PreferencesHelper.saveMixedMaxValue(progressValue + Constants.MIXED_MIN_VALUE);
        progressText.set(progressValue + Constants.MIXED_MIN_VALUE + "/" + Constants.MIXED_MAX_VALUE);
    }

}
