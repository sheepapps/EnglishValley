package com.sheepapps.englishvalley.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import com.sheepapps.englishvalley.R;
import com.sheepapps.englishvalley.app.ValleyApp;
import com.sheepapps.englishvalley.databases.Abbreviation;
import com.sheepapps.englishvalley.databases.Adjective;
import com.sheepapps.englishvalley.databases.Idiom;
import com.sheepapps.englishvalley.databases.Jokee;
import com.sheepapps.englishvalley.databases.Murphy;
import com.sheepapps.englishvalley.databases.Oxymoron;
import com.sheepapps.englishvalley.databases.Palindrome;
import com.sheepapps.englishvalley.databases.Philosophy;
import com.sheepapps.englishvalley.databases.Proverb;
import com.sheepapps.englishvalley.databases.Quote;
import com.sheepapps.englishvalley.databases.Riddle;
import com.sheepapps.englishvalley.databases.Silent;
import com.sheepapps.englishvalley.databases.Symbol;
import com.sheepapps.englishvalley.databases.Tippp;
import com.sheepapps.englishvalley.databases.Tongue;
import com.sheepapps.englishvalley.databases.ValleyRoomDatabase;
import com.sheepapps.englishvalley.databases.WordDao;
import com.sheepapps.englishvalley.databases.WordAbs;
import com.sheepapps.englishvalley.databases.system.Storage;
import com.sheepapps.englishvalley.databases.system.SystemDao;
import com.sheepapps.englishvalley.helpers.Constants.Categories;

public class CardViewModel extends AndroidViewModel {

    public final ObservableField<String> categoryString = new ObservableField<>("");
    public final ObservableField<String> main = new ObservableField<>("");
    public final ObservableField<String> sense = new ObservableField<>("");
    public final ObservableField<String> example = new ObservableField<>("");
    public final ObservableField<String> extra = new ObservableField<>("");
    public final ObservableInt categoryVisibility = new ObservableInt(View.GONE);
    public final ObservableInt senseVisibility = new ObservableInt(View.GONE);
    public final ObservableInt exampleVisibility = new ObservableInt(View.GONE);
    public final ObservableInt extraVisibility = new ObservableInt(View.GONE);

    public final ObservableBoolean isFavorite = new ObservableBoolean(false);
    public final ObservableBoolean isClickable = new ObservableBoolean(true);

    public final ObservableField<String> senseDescription = new ObservableField<>("");
    public final ObservableField<String> exampleDescription = new ObservableField<>("");
    public final ObservableField<String> extraDescription = new ObservableField<>("");
    public final ObservableInt senseDescriptionVisibility = new ObservableInt(View.GONE);
    public final ObservableInt exampleDescriptionVisibility = new ObservableInt(View.GONE);
    public final ObservableInt extraDescriptionVisibility = new ObservableInt(View.GONE);

    private final MutableLiveData<Boolean> shareSelected = new MutableLiveData<>();
    private WordAbs mWordAbs;
    private boolean mIsAnimated;
    private final ValleyRoomDatabase DB = ValleyApp.getInstance().getDb();
    private OnFavoriteClickListener mListener;
    private OnCardClickListener mCardListener;

    public CardViewModel(@NonNull Application application) {
        super(application);
    }

    public void setWord(WordAbs wordAbs) {
        mWordAbs = wordAbs;
        setValues(wordAbs);
        mIsAnimated = isAnimated();
        if (mIsAnimated) {
            senseVisibility.set(View.INVISIBLE);
            senseDescriptionVisibility.set(View.INVISIBLE);
        }
    }

    public void setFavoriteListener(OnFavoriteClickListener listener) {
        mListener = listener;
    }

    private boolean isAnimated() {
        return mWordAbs.category == Categories.CATEGORY_RIDDLE ||
                mWordAbs.category == Categories.CATEGORY_SILENT;
    }

    public void cancelAnimation() {
        mIsAnimated = false;
        if (isAnimated()) {
            senseVisibility.set(View.VISIBLE);
            senseDescriptionVisibility.set(View.VISIBLE);
        }
    }

    private void setValues(WordAbs wordAbs) {
        clearValues();
        setCategoryString();
        main.set(wordAbs.main);
        sense.set(wordAbs.sense);
        example.set(wordAbs.example);
        extra.set(wordAbs.extra);
        if (wordAbs.category == Categories.CATEGORY_FACT) {
            isClickable.set(false);
        } else {
            isFavorite.set(wordAbs.favorite == 1);
        }
        if (wordAbs.sense != null) {
            senseVisibility.set(wordAbs.sense.length() > 0 ? View.VISIBLE : View.GONE);
        }
        if (wordAbs.example != null) {
            exampleVisibility.set(wordAbs.example.length() > 0 ? View.VISIBLE : View.GONE);
        }
        if (wordAbs.extra != null) {
            extraVisibility.set(wordAbs.extra.length() > 0 ? View.VISIBLE : View.GONE);
        }
        String description = "";
        if (wordAbs.category != Categories.CATEGORY_FACT) {
            description = "category";
        } else {
            description = DB.systemDao().getDescriptionByCategory(wordAbs.category);
        }
        parseDescription(description);
    }

    public void onFavouriteClicked() {
        mWordAbs.favorite = (mWordAbs.favorite + 1) % 2;
        mWordAbs.favoriteTime = System.currentTimeMillis();
        isFavorite.set(!isFavorite.get());
        updateWord();
        if (mListener != null) {
            mListener.onFavoriteClicked();
        }
        int strId = isFavorite.get() ? R.string.added_to_favorites : R.string.removed_from_favorites;
        Toast.makeText(getApplication().getApplicationContext(), strId, Toast.LENGTH_SHORT).show();
    }

    public MutableLiveData<Boolean> getShareSelected() {
        return shareSelected;
    }

    public void onSelectClicked() {
        String msgToCopy = main.get() + (main.get().isEmpty() ? "" : "\n") + sense.get() +
                (sense.get().isEmpty() ? "" : "\n") + example.get() +
                (example.get().isEmpty() ? "" : "\n") + extra.get();
        ClipboardManager clipboard = (ClipboardManager)getApplication().getApplicationContext()
                .getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("", msgToCopy);
        if (clipboard != null) {
            clipboard.setPrimaryClip(clip);
        }
        Toast.makeText(getApplication().getApplicationContext(),
                R.string.copied_msg, Toast.LENGTH_SHORT).show();
    }

    public void onShareClicked() {
        shareSelected.postValue(true);
    }

    public void onCardClick() {
        if (mIsAnimated) {
            senseVisibility.set(View.VISIBLE);
            senseDescriptionVisibility.set(View.VISIBLE);
            if (mCardListener != null) {
                mCardListener.onCardClicked();
            }
        }
    }

    private void parseDescription(String description) {
        if (description == null) {
            return;
        }
        int index = 0;
        String[] descriptions = new String[]{"", "", ""};
        if (description.length() > 0) {
            description += "*";
        }
        int[] visibilities = new int[]{View.GONE, View.GONE, View.GONE};
        for (int i = 0; i < 3; i++) {
            int temp = description.indexOf('*', index);
            if (temp == -1) {
                break;
            }
            descriptions[i] = description.substring(index, temp);
            visibilities[i] = View.VISIBLE;
            index = temp + 1;
        }
        senseDescription.set(descriptions[0]);
        exampleDescription.set(descriptions[1]);
        extraDescription.set(descriptions[2]);

        senseDescriptionVisibility.set(visibilities[0]);
        exampleDescriptionVisibility.set(visibilities[1]);
        extraDescriptionVisibility.set(visibilities[2]);
    }

    private void updateWord() {
        WordDao dao = DB.wordsDao();
        switch (mWordAbs.category) {
            case Categories.CATEGORY_ABBREVIATION: dao.updateAbbreviation((Abbreviation) mWordAbs); break;
            case Categories.CATEGORY_IDIOM: dao.updateIdiom((Idiom) mWordAbs); break;
            case Categories.CATEGORY_JOKE: dao.updateJoke((Jokee) mWordAbs); break;
            case Categories.CATEGORY_MURPHY: dao.updateMurphy((Murphy) mWordAbs); break;
            case Categories.CATEGORY_OPPOSITE: dao.updateAdjective((Adjective) mWordAbs); break;
            case Categories.CATEGORY_OXYMORON: dao.updateOxymoron((Oxymoron) mWordAbs); break;
            case Categories.CATEGORY_PALINDROME: dao.updatePalindrome((Palindrome) mWordAbs); break;
            case Categories.CATEGORY_PHILOSOPHY: dao.updatePhilosophy((Philosophy) mWordAbs); break;
            case Categories.CATEGORY_PROVERB: dao.updateProverb((Proverb) mWordAbs); break;
            case Categories.CATEGORY_QUOTE: dao.updateQuote((Quote) mWordAbs); break;
            case Categories.CATEGORY_RIDDLE: dao.updateRiddle((Riddle) mWordAbs); break;
            case Categories.CATEGORY_SILENT: dao.updateSilent((Silent) mWordAbs); break;
            case Categories.CATEGORY_SYMBOL: dao.updateSymbol((Symbol) mWordAbs); break;
            case Categories.CATEGORY_TIP: dao.updateTip((Tippp) mWordAbs); break;
            case Categories.CATEGORY_TONGUE: dao.updateTongue((Tongue) mWordAbs); break;
        }
    }

    private void setCategoryString() {
        if (categoryVisibility.get() == View.VISIBLE) {
            SystemDao dao = DB.systemDao();
            Storage storage = dao.getStorageByCategoryId(mWordAbs.category);
            categoryString.set(storage.name);
        }
    }

    public void setCategoryVisibility(int visibility) {
        categoryVisibility.set(visibility);
    }

    private void clearValues() {
        main.set("");
        sense.set("");
        example.set("");
        extra.set("");
        senseDescriptionVisibility.set(View.GONE);
        exampleDescriptionVisibility.set(View.GONE);
        extraDescriptionVisibility.set(View.GONE);
        senseDescription.set("");
        exampleDescription.set("");
        extraDescription.set("");
    }

    public boolean isCardAnimated() {
        return mIsAnimated;
    }

    public void setOnClickListener(OnCardClickListener listener) {
        mCardListener = listener;
    }

    public interface OnFavoriteClickListener {
        void onFavoriteClicked();
    }

    public interface OnCardClickListener {
        void onCardClicked();
    }
}
