package com.barnard.model;

import java.util.List;

public class DictionaryDTO {

    private String word;
    private List<Phonetic> phonetics;
    private List<Meaning> meanings;
    private License license;
    private List<String> sourceUrls;

    private static class Phonetic {
        private String text;
        private String audio;
        private String sourceUrl;
        private License license;
    }

    public static class License {
        private String name;
        private String url;
    }

    public String getWord() {
        return word;
    }

    public List<Phonetic> getPhonetics() {
        return phonetics;
    }

    public List<Meaning> getMeanings() {
        return meanings;
    }

    public License getLicense() {
        return license;
    }

    public List<String> getSourceUrls() {
        return sourceUrls;
    }

    public Word mapToWord() {
        Word word = new Word();
        word.setWord(this.word);
        word.setPhonetic(this.phonetics.get(0).text);
        word.setMeanings(this.meanings);
        return word;
    }
}
