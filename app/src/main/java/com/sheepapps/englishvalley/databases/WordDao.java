package com.sheepapps.englishvalley.databases;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface WordDao {
    @Query("SELECT * FROM abbreviations")
    List<Abbreviation> getAbbreviations();

    @Query("SELECT * FROM adjectives")
    List<Adjective> getAdjectives();

    @Query("SELECT * FROM idioms")
    List<Idiom> getIdioms();

    @Query("SELECT * FROM jokes")
    List<Jokee> getJokes();

    @Query("SELECT * FROM murphies")
    List<Murphy> getMurphies();

    @Query("SELECT * FROM oxymorons")
    List<Oxymoron> getOxymorons();

    @Query("SELECT * FROM palindromes")
    List<Palindrome> getPalindromes();

    @Query("SELECT * FROM philosophies")
    List<Philosophy> getPhilosophies();

    @Query("SELECT * FROM proverbs")
    List<Proverb> getProverbs();

    @Query("SELECT * FROM quotes")
    List<Quote> getQuotes();

    @Query("SELECT * FROM riddles")
    List<Riddle> getRiddles();

    @Query("SELECT * FROM silents")
    List<Silent> getSilents();

    @Query("SELECT * FROM symbols")
    List<Symbol> getSymbols();

    @Query("SELECT * FROM tips")
    List<Tippp> getTips();

    @Query("SELECT * FROM tongue")
    List<Tongue> getTongues();

    @Query("SELECT * FROM abbreviations WHERE favorite = 1")
    List<Abbreviation> getAbbreviationsFavorite();

    @Query("SELECT * FROM adjectives WHERE favorite = 1")
    List<Adjective> getAdjectivesFavorite();

    @Query("SELECT * FROM idioms WHERE favorite = 1")
    List<Idiom> getIdiomsFavorite();

    @Query("SELECT * FROM jokes WHERE favorite = 1")
    List<Jokee> getJokesFavorite();

    @Query("SELECT * FROM murphies WHERE favorite = 1")
    List<Murphy> getMurphiesFavorite();

    @Query("SELECT * FROM oxymorons WHERE favorite = 1")
    List<Oxymoron> getOxymoronsFavorite();

    @Query("SELECT * FROM palindromes WHERE favorite = 1")
    List<Palindrome> getPalindromesFavorite();

    @Query("SELECT * FROM philosophies WHERE favorite = 1")
    List<Philosophy> getPhilosophiesFavorite();

    @Query("SELECT * FROM proverbs WHERE favorite = 1")
    List<Proverb> getProverbsFavorite();

    @Query("SELECT * FROM quotes WHERE favorite = 1")
    List<Quote> getQuotesFavorite();

    @Query("SELECT * FROM riddles WHERE favorite = 1")
    List<Riddle> getRiddlesFavorite();

    @Query("SELECT * FROM silents WHERE favorite = 1")
    List<Silent> getSilentsFavorite();

    @Query("SELECT * FROM symbols WHERE favorite = 1")
    List<Symbol> getSymbolsFavorite();

    @Query("SELECT * FROM tips WHERE favorite = 1")
    List<Tippp> getTipsFavorite();

    @Query("SELECT * FROM tongue WHERE favorite = 1")
    List<Tongue> getTonguesFavorite();

    @Update
    void updateAbbreviation(Abbreviation words);

    @Update
    void updateAdjective(Adjective words);

    @Update
    void updateIdiom(Idiom words);

    @Update
    void updateJoke(Jokee words);

    @Update
    void updateMurphy(Murphy words);

    @Update
    void updateOxymoron(Oxymoron words);

    @Update
    void updatePalindrome(Palindrome words);

    @Update
    void updatePhilosophy(Philosophy words);

    @Update
    void updateProverb(Proverb words);

    @Update
    void updateQuote(Quote words);

    @Update
    void updateRiddle(Riddle words);

    @Update
    void updateSilent(Silent words);

    @Update
    void updateSymbol(Symbol words);

    @Update
    void updateTip(Tippp words);

    @Update
    void updateTongue(Tongue words);
}