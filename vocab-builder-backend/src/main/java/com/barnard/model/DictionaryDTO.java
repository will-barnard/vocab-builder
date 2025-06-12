package com.barnard.model;

import java.util.List;

public class DictionaryDTO {

    private String word;
    private String phonetic;
    private List<Phonetic> phonetics;
    private List<Meaning> meanings;
    private List<String> sourceUrls;

    public String getWord() {
        return word;
    }

    public String getPhonetic() {
        return phonetic;
    }

    public List<Phonetic> getPhonetics() {
        return phonetics;
    }

    public List<Meaning> getMeanings() {
        return meanings;
    }

    public List<String> getSourceUrls() {
        return sourceUrls;
    }

    public Word mapToWord() {
        Word word = new Word();
        word.setWord(this.word);

        String phoneticTemp = null;
        if (this.phonetic != null) {
            phoneticTemp = this.phonetic;
        } else if (!this.phonetics.isEmpty()) {
            for (Phonetic phonetic : this.phonetics) {
                if (phonetic.getText() != null && !phonetic.getText().isEmpty()) {
                    phoneticTemp = phonetic.getText();
                    break;
                }
            }
        }
        for (Meaning meaning : this.meanings) {
            meaning.setPhonetic(phoneticTemp);
        }
        word.setMeanings(this.meanings);
        return word;
    }
}