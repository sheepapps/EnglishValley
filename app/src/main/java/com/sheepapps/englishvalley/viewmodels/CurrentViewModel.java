package com.sheepapps.englishvalley.viewmodels;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableInt;
import com.sheepapps.englishvalley.app.ValleyApp;
import com.sheepapps.englishvalley.databases.Quote;
import com.sheepapps.englishvalley.databases.WordDao;
import com.sheepapps.englishvalley.databases.WordAbs;
import com.sheepapps.englishvalley.databases.system.Storage;
import com.sheepapps.englishvalley.databases.system.SystemDao;
import com.sheepapps.englishvalley.helpers.Constants;
import com.sheepapps.englishvalley.helpers.MixedHelper;
import com.sheepapps.englishvalley.network.QuotesRepositoryKt;

import java.util.ArrayList;
import java.util.List;

public class CurrentViewModel extends ViewModel {
    public ObservableInt totalWords = new ObservableInt();
    public ObservableInt currentWord = new ObservableInt();
    public ObservableInt completedWord = new ObservableInt();

    private String mDescription = "";
    private List<? extends WordAbs> mWords;
    private int mCategory = Constants.Categories.MIXED_CATEGORY;
    private Storage mStorage;
    private SystemDao mSystemDao;

    public void init(int category) {
        initList(category);
        if (mCategory == Constants.Categories.MIXED_CATEGORY) {
            initMixed();
        } else {
            initUsual();
        }
    }

    public void saveData(int currentItem) {
        ValleyApp.getInstance().getPreferences().edit()
                .putInt(Constants.Categories.KEY_LAST_CATEGORY, mCategory).apply();
        if (mStorage.id != Constants.Categories.MIXED_CATEGORY) {
            mStorage.current = currentItem;
            if (mStorage.completed < currentItem) {
                mStorage.completed = currentItem;
            }
            mSystemDao.updateStorage(mStorage);
        }
    }

    public void updateCount(int count) {
        currentWord.set(count + 1);
        if (count > mStorage.completed) {
            if (mStorage.id != Constants.Categories.MIXED_CATEGORY) {
                mStorage.completed = count;
            }
            completedWord.set(count + 1);
        }
    }

    private void initList(int category) {
        WordDao dao = ValleyApp.getInstance().getDb().wordsDao();
        mCategory = category;
        switch (category) {
            case Constants.Categories.CATEGORY_JOKE : mWords = dao.getJokes(); break;
            case Constants.Categories.CATEGORY_ABBREVIATION: mWords = dao.getAbbreviations(); break;
            case Constants.Categories.CATEGORY_OPPOSITE: mWords = dao.getAdjectives(); break;
            case Constants.Categories.CATEGORY_IDIOM: mWords = dao.getIdioms(); break;
            case Constants.Categories.CATEGORY_MURPHY: mWords = dao.getMurphies(); break;
            case Constants.Categories.CATEGORY_OXYMORON: mWords = dao.getOxymorons(); break;
            case Constants.Categories.CATEGORY_PALINDROME: mWords = dao.getPalindromes(); break;
            case Constants.Categories.CATEGORY_PHILOSOPHY: mWords = dao.getPhilosophies(); break;
            case Constants.Categories.CATEGORY_PROVERB: mWords = dao.getProverbs(); break;
            case Constants.Categories.CATEGORY_QUOTE:
            List<Quote> newList = new ArrayList<Quote>();
            newList.addAll(QuotesRepositoryKt.getQuoteList());
            newList.addAll(dao.getQuotes());
            mWords = newList;
            break;
            case Constants.Categories.CATEGORY_RIDDLE: mWords = dao.getRiddles(); break;
            case Constants.Categories.CATEGORY_SILENT: mWords = dao.getSilents(); break;
            case Constants.Categories.CATEGORY_SYMBOL: mWords = dao.getSymbols(); break;
            case Constants.Categories.CATEGORY_TIP: mWords = dao.getTips(); break;
            case Constants.Categories.CATEGORY_TONGUE: mWords = dao.getTongues(); break;
            default: mWords = MixedHelper.getInstance().shuffle().getMixed(); break;
        }
        totalWords.set(mWords.size());
    }

    private void initMixed() {
        mStorage = MixedHelper.getInstance().getMixedStorage();
    }

    private void initUsual() {
        mSystemDao = ValleyApp.getInstance().getDb().systemDao();
        mStorage = mSystemDao.getStorageByCategoryId(mCategory);
        List<String> descriptions = mSystemDao.getDescriptionTables();
        if (descriptions.contains(mStorage.tableName)) {
            mDescription = mSystemDao.getDescriptionByTableName(mStorage.tableName);
        }
        currentWord.set(mStorage.current + 1);
        completedWord.set(mStorage.completed + 1);
    }

    public int getCategory() {
        return mCategory;
    }

    public List<? extends WordAbs> getWords() {
        return mWords;
    }

    public void setWords(List<? extends WordAbs> words) {
        this.mWords = words;
    }

    public String getDescription() {
        return mDescription;
    }

    public Storage getStorage() {
        return mStorage;
    }
}
