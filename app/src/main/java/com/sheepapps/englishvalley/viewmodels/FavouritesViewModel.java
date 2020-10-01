package com.sheepapps.englishvalley.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import android.util.SparseArray;

import com.sheepapps.englishvalley.app.ValleyApp;
import com.sheepapps.englishvalley.databases.WordAbs;
import com.sheepapps.englishvalley.databases.WordDao;
import com.sheepapps.englishvalley.helpers.Constants;
import com.sheepapps.englishvalley.helpers.PreferencesHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FavouritesViewModel extends ViewModel {

    private SparseArray<List<? extends  WordAbs>> mArray;
    private final MutableLiveData<Integer> TITLE = new MutableLiveData<>();
    private final MutableLiveData<Boolean> mIsDataReloaded = new MutableLiveData<>();

    public int getCurrentCategory() {
        return mCurrentCategory;
    }

    private int mCurrentCategory;

    public void init() {
        mArray = new SparseArray<>(15);
        WordDao dao = ValleyApp.getInstance().getDb().wordsDao();
        mArray.put(Constants.Categories.CATEGORY_ABBREVIATION, dao.getAbbreviationsFavorite());
        mArray.put(Constants.Categories.CATEGORY_OPPOSITE, dao.getAdjectivesFavorite());
        mArray.put(Constants.Categories.CATEGORY_IDIOM, dao.getIdiomsFavorite());
        mArray.put(Constants.Categories.CATEGORY_TIP, dao.getTipsFavorite());
        mArray.put(Constants.Categories.CATEGORY_MURPHY, dao.getMurphiesFavorite());
        mArray.put(Constants.Categories.CATEGORY_PROVERB, dao.getProverbsFavorite());
        mArray.put(Constants.Categories.CATEGORY_PHILOSOPHY, dao.getPhilosophiesFavorite());
        mArray.put(Constants.Categories.CATEGORY_SYMBOL, dao.getSymbolsFavorite());
        mArray.put(Constants.Categories.CATEGORY_TONGUE, dao.getTonguesFavorite());
        mArray.put(Constants.Categories.CATEGORY_PALINDROME, dao.getPalindromesFavorite());
        mArray.put(Constants.Categories.CATEGORY_RIDDLE, dao.getRiddlesFavorite());
        mArray.put(Constants.Categories.CATEGORY_OXYMORON, dao.getOxymoronsFavorite());
        mArray.put(Constants.Categories.CATEGORY_QUOTE, dao.getQuotesFavorite());
        mArray.put(Constants.Categories.CATEGORY_JOKE, dao.getJokesFavorite());
        mArray.put(Constants.Categories.CATEGORY_SILENT, dao.getSilentsFavorite());
    }

    public List<? extends WordAbs> getFavorites() {
        if (mArray == null) {
            init();
        }
        List<WordAbs> list = new ArrayList<>();
        list.addAll(mArray.get(Constants.Categories.CATEGORY_ABBREVIATION));
        list.addAll(mArray.get(Constants.Categories.CATEGORY_OPPOSITE));
        list.addAll(mArray.get(Constants.Categories.CATEGORY_IDIOM));
        list.addAll(mArray.get(Constants.Categories.CATEGORY_TIP));
        list.addAll(mArray.get(Constants.Categories.CATEGORY_MURPHY));
        list.addAll(mArray.get(Constants.Categories.CATEGORY_PROVERB));
        list.addAll(mArray.get(Constants.Categories.CATEGORY_PHILOSOPHY));
        list.addAll(mArray.get(Constants.Categories.CATEGORY_SYMBOL));
        list.addAll(mArray.get(Constants.Categories.CATEGORY_TONGUE));
        list.addAll(mArray.get(Constants.Categories.CATEGORY_PALINDROME));
        list.addAll(mArray.get(Constants.Categories.CATEGORY_RIDDLE));
        list.addAll(mArray.get(Constants.Categories.CATEGORY_OXYMORON));
        list.addAll(mArray.get(Constants.Categories.CATEGORY_QUOTE));
        list.addAll(mArray.get(Constants.Categories.CATEGORY_JOKE));
        list.addAll(mArray.get(Constants.Categories.CATEGORY_SILENT));
        TITLE.postValue(0);
        sortList(list);
        mCurrentCategory = -1;
        return list;
    }

    public List<? extends WordAbs> getFavorites(int id) {
        List<? extends WordAbs> favouritesList;
        if (id > 0 && id <= 15) {
            if (mArray == null) {
                init();
            }
            favouritesList = mArray.get(id);
            TITLE.postValue(id);
        } else {
            favouritesList = getFavorites();
        }
        sortList(favouritesList);
        mCurrentCategory = id;
        return favouritesList;
    }

    private void sortList(List<? extends WordAbs> list) {
        switch (PreferencesHelper.getSortingType()) {
            case (Constants.SortingType.ADDING_DOWN): {
                Collections.sort(list, (Comparator<WordAbs>) (word1, word2) ->
                        Long.compare(word1.favoriteTime, word2.favoriteTime));
                Collections.reverse(list);
            } break;
            case (Constants.SortingType.ADDING_UP): {
                Collections.sort(list, (Comparator<WordAbs>) (word1, word2) ->
                        Long.compare(word1.favoriteTime, word2.favoriteTime));
            }
        }
    }

    public void saveFavouritesPosition(int position) {
        PreferencesHelper.saveFavouritesPosition(position);
    }

    public MutableLiveData<Boolean> getDataReloaded() {
        return mIsDataReloaded;
    }

    public int getFavouritesPosition() {
        return PreferencesHelper.getFavouritesPosition();
    }

    public void saveFavouritesIndex(int index) {
        PreferencesHelper.saveFavouritesIndex(index);
    }

    public int getFavouritesIndex() {
        return PreferencesHelper.getFavouritesIndex();
    }

    public int getSortingType() {
        return PreferencesHelper.getSortingType();
    }

    public void setSortingType(int sortingType) {
        PreferencesHelper.saveSortingType(sortingType);
        mIsDataReloaded.postValue(true);
    }

    public MutableLiveData<Integer> getTitle() {
        return TITLE;
    }
}
