package com.barnard.dao;

import com.barnard.model.Word;

import java.util.List;

public interface WordDao {

    Word createWord(Word word);
    Word getWordById(int id);
    List<Word> getWordsByUserId(int userId);
    Word updateWord(Word word);
    boolean deleteWord(int id);

}
