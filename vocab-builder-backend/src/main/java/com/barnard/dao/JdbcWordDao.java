package com.barnard.dao;

import com.barnard.exception.DaoException;
import com.barnard.model.Definition;
import com.barnard.model.Meaning;
import com.barnard.model.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.dao.DataIntegrityViolationException;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Component
public class JdbcWordDao implements WordDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Word createWord(Word word) {
        try {
            // Step 1: Insert the word
            String wordSql = "INSERT INTO words (word, user_id) VALUES (?, ?) RETURNING word_id";
            int wordId = jdbcTemplate.queryForObject(wordSql, Integer.class, word.getWord(), word.getUserId());
            word.setWordId(wordId);

            // Step 2: For each meaning, insert and get the meaning_id
            String insertMeaningSql = "INSERT INTO meanings (word_id, part_of_speech, phonetic) VALUES (?, ?, ?) RETURNING meaning_id";
            String insertDefinitionSql = "INSERT INTO definitions (meaning_id, definition_text, example_sentence) VALUES (?, ?, ?)";

            for (var meaning : word.getMeanings()) {
                Integer meaningId = jdbcTemplate.queryForObject(
                        insertMeaningSql,
                        Integer.class,
                        wordId,
                        meaning.getPartOfSpeech(),
                        meaning.getPhonetic()
                );

                // Set the id if needed
                meaning.setMeaningId(meaningId);

                for (var definition : meaning.getDefinitions()) {
                    jdbcTemplate.update(
                            insertDefinitionSql,
                            meaningId,
                            definition.getDefinition(),
                            definition.getExample() // null-safe already
                    );
                }
            }

            return word;
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }

    @Override
    public Word getWordById(int id) {
        try {
            String wordSql = "SELECT word_id, word, user_id FROM words WHERE word_id = ?";
            Word word = jdbcTemplate.queryForObject(wordSql, (rs, rowNum) -> {
                Word w = new Word();
                w.setWordId(rs.getInt("word_id"));
                w.setWord(rs.getString("word"));
                w.setUserId(rs.getInt("user_id"));
                return w;
            }, id);

            String meaningSql = "SELECT meaning_id, part_of_speech, phonetic FROM meanings WHERE word_id = ?";
            List<Meaning> meanings = jdbcTemplate.query(meaningSql, (rs, rowNum) -> {
                Meaning meaning = new Meaning();
                meaning.setMeaningId(rs.getInt("meaning_id"));
                meaning.setPartOfSpeech(rs.getString("part_of_speech"));
                meaning.setPhonetic(rs.getString("phonetic"));

                String definitionSql = "SELECT definition_text, example_sentence FROM definitions WHERE meaning_id = ?";
                meaning.setDefinitions(jdbcTemplate.query(definitionSql, (defRs, defRowNum) -> {
                    Definition definition = new Definition();
                    definition.setDefinition(defRs.getString("definition_text"));
                    definition.setExample(defRs.getString("example_sentence"));
                    return definition;
                }, meaning.getMeaningId()));

                String synonymSql = "SELECT synonym_word FROM synonyms WHERE meaning_id = ?";
                meaning.setSynonyms(jdbcTemplate.query(synonymSql, (synRs, synRowNum) -> synRs.getString("synonym_word"), meaning.getMeaningId()));

                String antonymSql = "SELECT antonym_word FROM antonyms WHERE meaning_id = ?";
                meaning.setAntonyms(jdbcTemplate.query(antonymSql, (antRs, antRowNum) -> antRs.getString("antonym_word"), meaning.getMeaningId()));

                return meaning;
            }, id);

            word.setMeanings(meanings);
            return word;
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }

    @Override
    public List<Word> getWordsByUserId(int userId) {
        try {
            String sql = "SELECT word_id, word, user_id FROM words WHERE user_id = ?";
            return jdbcTemplate.query(sql, (rs, rowNum) -> {
                Word word = new Word();
                word.setWordId(rs.getInt("word_id"));
                word.setWord(rs.getString("word"));
                word.setUserId(rs.getInt("user_id"));
                return word;
            }, userId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }

    @Override
    public Word updateWord(Word word) {
        try {
            String sql = "UPDATE words SET word = ? WHERE word_id = ?";
            jdbcTemplate.update(sql, word.getWord(), word.getWordId());
            return word;
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }

    @Override
    public boolean deleteWord(int id) {
        try {
            String sql = "DELETE FROM words WHERE word_id = ?";
            return jdbcTemplate.update(sql, id) > 0;
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
    }
}