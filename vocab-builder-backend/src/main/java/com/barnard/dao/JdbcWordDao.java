package com.barnard.dao;

import com.barnard.model.Word;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JdbcWordDao implements WordDao {


    @Override
    public Word createWord(Word word) {
        return null;
    }

    @Override
    public Word getWordById(int id) {
        return null;
    }

    @Override
    public List<Word> getWordsByUserId(int userId) {
        return List.of();
    }

    @Override
    public Word updateWord(Word word) {
        return null;
    }

    @Override
    public boolean deleteWord(int id) {
        return false;
    }


}
