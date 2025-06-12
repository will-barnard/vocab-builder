package com.barnard.controller;

import com.barnard.model.DictionaryDTO;
import com.barnard.model.Meaning;
import com.barnard.model.Phonetic;
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
    public Word getDefinitions(@RequestParam String word) {
        Word result = new Word();
        try {
            List<DictionaryDTO> response = dictionaryApiService.getDefinition(word);
            if (response == null) {
                return null;
            }
            result.setWord(word);
            result.setMeanings(new ArrayList<>());
            int meaningCount = 0;
            for (DictionaryDTO dto : response) {
                String phoneticTemp = null;
                if (dto.getPhonetic() != null) {
                    phoneticTemp = dto.getPhonetic();
                } else if (!dto.getPhonetics().isEmpty()) {
                    for (Phonetic phonetic : dto.getPhonetics()) {
                        if (phonetic.getText() != null && !phonetic.getText().isEmpty()) {
                            phoneticTemp = phonetic.getText();
                            break;
                        }
                    }
                }
                for (Meaning meaning : dto.getMeanings()) {
                    meaning.setPhonetic(phoneticTemp);
                    meaning.setMeaningId(meaningCount);
                    meaningCount++;
                    result.getMeanings().add(meaning);
                }
            }
        } catch (Exception e) {
            // Handle exceptions, possibly log them
            System.err.println("Error fetching definitions: " + e.getMessage());
            result.setMeanings(new ArrayList<>());
        }


        return result;
    }

}
