package com.sheepapps.englishvalley.dialogs;

import android.app.Dialog;
import androidx.lifecycle.MutableLiveData;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import com.sheepapps.englishvalley.app.ValleyApp;
import com.sheepapps.englishvalley.databases.system.Storage;
import java.util.List;
import fr.tvbarthel.lib.blurdialogfragment.SupportBlurDialogFragment;

public class CategoriesListDialog extends SupportBlurDialogFragment {

    MutableLiveData<Integer> chosenCategory = new MutableLiveData<>();

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        List<Storage> storages = ValleyApp.getInstance().getDb().systemDao().getAllStorage();
        CharSequence[] categories = new CharSequence[storages.size() + 1];
        categories[0] = "All";
        for (int i = 1; i < storages.size() + 1; i++) {
            categories[i] = storages.get(i - 1).name;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Choose a category");
        builder.setItems(categories, (dialog, which) -> chosenCategory.postValue(which));
        return builder.create();
    }

    public MutableLiveData<Integer> getChosenCategory() {
        return chosenCategory;
    }
}
