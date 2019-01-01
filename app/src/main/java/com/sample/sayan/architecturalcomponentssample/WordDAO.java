package com.sample.sayan.architecturalcomponentssample;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface WordDAO {

    @Insert
    void insert(Word word);

    @Delete
    void delete(Word word);

    @Update
    void update(Word word);

    @Query("SELECT * FROM word_table ORDER BY word ASC")
    LiveData<List<Word>> getAllAlphabetizedWords();

    @Query("DELETE FROM word_table")
    void deleteAll();
}
