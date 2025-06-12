package com.barnard.controller;

import com.barnard.model.DictionaryDTO;
import com.barnard.model.Word;
import com.barnard.service.DictionaryApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
public class DictionaryController {

    @Autowired
    private DictionaryApiService dictionaryApiService;

    @GetMapping(path = "/dictionary")
    public List<Word> getDefinitions(@RequestParam String word) {
        List<Word> words = new ArrayList<>();
        List<DictionaryDTO> response = dictionaryApiService.getDefinition(word);

        for (DictionaryDTO dto : response) {
            words.add(dto.mapToWord());
        }

        return words;
    }

}
