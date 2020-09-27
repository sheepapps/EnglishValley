package com.sheepapps.englishvalley.helpers;

import com.sheepapps.englishvalley.app.ValleyApp;
import com.sheepapps.englishvalley.databases.WordAbs;
import com.sheepapps.englishvalley.databases.WordDao;
import com.sheepapps.englishvalley.databases.system.Storage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MixedHelper {

    private static MixedHelper sMixedHelper;
    private final Storage MIXED_STORAGE;
    private final List<WordAbs> mixedWords;

    private MixedHelper() {
        mixedWords = new ArrayList<>();
        initWords();
        MIXED_STORAGE = new Storage();
        initStorage();
        shuffle();
    }

    public static MixedHelper getInstance() {
        if (sMixedHelper == null) {
            sMixedHelper = new MixedHelper();
        }
        return sMixedHelper;
    }

    public List<WordAbs> getMixed() {
        setTotal();
        return mixedWords.subList(0, MIXED_STORAGE.total);
    }

    public MixedHelper shuffle() {
        Collections.shuffle(mixedWords);
        return this;
    }

    public Storage getMixedStorage() {
        return MIXED_STORAGE;
    }

    private void initStorage() {
        MIXED_STORAGE.id = Constants.Categories.MIXED_CATEGORY;
        MIXED_STORAGE.name = "Mixed";
        setTotal();
    }

    private void initWords() {
        WordDao dao = ValleyApp.getInstance().getDb().wordsDao();
        mixedWords.addAll(dao.getAbbreviations());
        mixedWords.addAll(dao.getTongues());
        mixedWords.addAll(dao.getTips());
        mixedWords.addAll(dao.getSymbols());
        mixedWords.addAll(dao.getSilents());
        mixedWords.addAll(dao.getRiddles());
        mixedWords.addAll(dao.getProverbs());
        mixedWords.addAll(dao.getMurphies());
        mixedWords.addAll(dao.getQuotes());
        mixedWords.addAll(dao.getPhilosophies());
        mixedWords.addAll(dao.getPalindromes());
        mixedWords.addAll(dao.getOxymorons());
        mixedWords.addAll(dao.getAdjectives());
        mixedWords.addAll(dao.getJokes());
        mixedWords.addAll(dao.getIdioms());
        mixedWords.addAll(dao.getCatsFacts());
    }

    private void setTotal() {
        MIXED_STORAGE.total = PreferencesHelper.getMixedMaxValue();
        if (MIXED_STORAGE.total > mixedWords.size() || MIXED_STORAGE.total < 1) {
            MIXED_STORAGE.total = mixedWords.size() - 1;
        }
    }

    public int getTotal() {
        return MIXED_STORAGE.total;
    }
}
