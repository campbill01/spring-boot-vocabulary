package com.campbell.vocabulary.controller;

import com.campbell.vocabulary.domain.Word;
import com.campbell.vocabulary.service.WordService;
import org.apache.commons.codec.language.Soundex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class MeaningController {
    private final WordService wordService;

    @Autowired
    public MeaningController(WordService wordService) {
        this.wordService = wordService;
    }

    @RequestMapping("/random/meaning")
    public String randomMeaning(Model model){
        Word word = wordService.random();
        //System.out.println(word.word);
        model.addAttribute("words", word );
        return "meaning";
    }


    @PostMapping("/random/meaning")
    public String meaningSubmission(@RequestParam("word") String word,
                                    @RequestParam("meaning") String meaning,
                                    @RequestParam("requestUri") String requestUri,
                                    ModelMap modelMap){

        List<Word> words = wordService.list();
        Word foundWord = new Word();
        for(Word searchWord: words){
            if(meaning.equals(searchWord.meaning)){
                foundWord = searchWord;
            }
        }
        String testWord = foundWord.name;
        String result;
        // Try the soundex
        Soundex soundex = new Soundex();
        String phoneticValue = soundex.encode(testWord);
        String phoneticWord  = soundex.encode(word);
        if(phoneticValue.equals(phoneticWord)) {
            result = "Success";
        }else{
            result = "Failure";
            modelMap.put("provided", testWord);
            modelMap.put("submitted", word);
        }

        modelMap.put("result", result);
        modelMap.put("selected", meaning);
        modelMap.put("requester", requestUri);
        return "result";
    }

    @RequestMapping("/multiple/meaning")
    public String multiMeaning(Model model){
        Word meaning = wordService.random();
        model.addAttribute("meaning", meaning);
        // get list of N meanings, add word.meaning to list
        // send back word, list of meanings to be put into radio button choice
        List <Word> words = new ArrayList<>();
        words.add(meaning);
        Integer size = 3;
        for(int i=0; i<size; i++){
            // allow dupes for now
            Word testWord = wordService.random();
            while( words.contains(testWord)){
                testWord = wordService.random();
            }
            words.add(testWord);
        }
        Collections.shuffle(words);
        model.addAttribute("words", words);
        return "multiMeaning";
    }

    @PostMapping("/multiple/meaning")
    public String multiMeaningSubmission(@RequestParam("word") String word,
                                         @RequestParam("meaning") String meaning,
                                         @RequestParam("requestUri") String requestUri,
                                         ModelMap modelMap){
        List<Word> words = wordService.list();
        Word foundWord = new Word();
        for(Word searchWord: words){
            if(meaning.equals(searchWord.meaning)){
                foundWord = searchWord;
            }
        }
        String testWord = foundWord.name;
        String result;

        Soundex soundex = new Soundex();
        String phoneticValue = soundex.encode(testWord);
        String phoneticWord  = soundex.encode(word);
        if(phoneticValue.equals(phoneticWord)) {
            result = "Success";
        }else{
            result = "Failure";
            modelMap.put("provided", testWord);
            modelMap.put("submitted", word);
        }

        modelMap.put("result", result);
        modelMap.put("selected", foundWord.meaning);
        modelMap.put("requester", requestUri);
        return "result";
    }


}
