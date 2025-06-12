package com.barnard.service;

import com.barnard.model.DictionaryDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class DictionaryApiService {

    private RestTemplate restTemplate = new RestTemplate();
    private String apiUrl = "https://api.dictionaryapi.dev/api/v2/entries/en/";

    public List<DictionaryDTO> getDefinition(String word) {
        String url = apiUrl + word;
        DictionaryDTO[] response = restTemplate.getForObject(url, DictionaryDTO[].class);
        return Arrays.asList(response);
    }
}