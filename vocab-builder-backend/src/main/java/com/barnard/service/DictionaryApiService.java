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
        DictionaryDTO[] response;
        try {
            String url = apiUrl + word;
            response = restTemplate.getForObject(url, DictionaryDTO[].class);
        } catch (Exception e) {
            // Handle exceptions, possibly log them
            System.err.println("Error fetching definitions: " + e.getMessage());
            return null; // Return null or an empty list based on your error handling strategy
        }
        return Arrays.asList(response);
    }
}