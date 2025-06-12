package com.barnard.controller;

import com.barnard.dao.JdbcWordDao;
import com.barnard.dao.UserDao;
import com.barnard.model.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@CrossOrigin
@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
@RequestMapping(path = "/word")
public class WordController {

    @Autowired
    private JdbcWordDao jdbcWordDao;
    @Autowired
    private UserDao userDao;

    @PostMapping
    public Word createWord(@RequestBody Word word, Principal principal) {
        word.setUserId(userDao.getUserByUsername(principal.getName()).getId());
        return jdbcWordDao.createWord(word);
    }

    @GetMapping("/{id}")
    public Word getWordById(@PathVariable int id, Principal principal) {
        Word word = null;
        try {
            word = jdbcWordDao.getWordById(id);
        } catch (Exception e) {
            throw new RuntimeException("Word not found with id: " + id);
        }
        return word;
    }

    @PutMapping()
    public Word updateWord(@RequestBody Word word, Principal principal) {
        Word updatedWord = null;
        try {
            Word existingWord = jdbcWordDao.getWordById(word.getWordId());
            if (existingWord.getUserId() != userDao.getUserByUsername(principal.getName()).getId()) {
                throw new SecurityException("Unauthorized access");
            }
            updatedWord = jdbcWordDao.updateWord(word);
        } catch (Exception e) {
        }

        return updatedWord;
    }

    @DeleteMapping("/{id}")
    public void deleteWord(@PathVariable int id, Principal principal) {
        try {
            Word word = jdbcWordDao.getWordById(id);
            if (word.getUserId() != userDao.getUserByUsername(principal.getName()).getId()) {
                throw new SecurityException("Unauthorized access");
            }
            jdbcWordDao.deleteWord(id);
        } catch (Exception e) {
            throw new RuntimeException("Word not found with id: " + id);
        }

    }

    @GetMapping("/user")
    public List<Word> getWordsByUserId(Principal principal) {
        int userId = userDao.getUserByUsername(principal.getName()).getId();
        return jdbcWordDao.getWordsByUserId(userId);
    }
}
