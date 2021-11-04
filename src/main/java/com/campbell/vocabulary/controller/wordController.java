package com.campbell.vocabulary.controller;

import com.campbell.vocabulary.domain.Word;
import com.campbell.vocabulary.service.wordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class wordController {
    final wordService wordService;

    @Autowired
    public wordController(wordService wordService) {
        this.wordService = wordService;
    }

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/get")
    @ResponseBody
    public Word get(){
        return wordService.get();
    }

    @GetMapping("/get/{name}")
    @ResponseBody
    public Word getName(@PathVariable(value="name") String name){
        return wordService.get(name);
    }

    @GetMapping("/list")
    @ResponseBody
    public List<Word> home(){
        return wordService.list();
    }

    @PostMapping(value = {"/words/update/{word}/{meaning}", "/words/add/{word}/{meaning}"})
    @ResponseBody
    public Word upsert(@PathVariable(value="word") String name, @PathVariable(value="meaning") String meaning){
        Word word = wordService.get(name);
        try {
            word.setMeaning(meaning);
            word = wordService.update(word);
        }catch (Exception e){
            word = wordService.create(name, meaning);
        }

        return word;
    }

    @RequestMapping("/random/word")
    public String random(Model model){
        Word word = wordService.random();

        model.addAttribute("words", word );
        return "word";
    }


    @PostMapping("/random/word")
    public String wordSubmission(@RequestParam("name") String word,
                                 @RequestParam("meaning") String meaning,
                                 @RequestParam("requestUri") String requestUri,
                                 ModelMap modelMap){
        String testMeaning = wordService.get(word).meaning;
        String result;

        if(testMeaning.equals(meaning)) {
            result = "Success";
        }else{
            result = "Failure";
            modelMap.put("submitted", meaning);
            modelMap.put("provided", testMeaning);
        }

        modelMap.put("result", result);
        modelMap.put("selected", word);
        modelMap.put("requester", requestUri);
        return "result";
    }

    @RequestMapping("/multiple/word")
    public String multiWord(Model model){
        Word word = wordService.random();
        model.addAttribute("word", word);
        // get list of N meanings, add word.meaning to list
        // send back word, list of meanings to be put into radio button choice
        List <Word> meanings = new ArrayList<>();
        meanings.add(word);
        Integer size = 3;
        for(int i=0; i<size; i++){
            // allow dupes for now
            Word testWord = wordService.random();
            while(meanings.contains(testWord)){
                testWord = wordService.random();
            }
            meanings.add(testWord);
        }
        Collections.shuffle(meanings);
        model.addAttribute("meanings", meanings);
        return "multiWord";
    }

    @PostMapping("/multiple/word")
    public String multiWordSubmission(@RequestParam("name") String word,
                                 @RequestParam("meaning") String meaning,
                                      @RequestParam("requestUri") String requestUri,
                                 ModelMap modelMap){
        String testMeaning = wordService.get(word).meaning;
        String result;

        if(testMeaning.equals(meaning)) {
            result = "Success";
        }else{
            result = "Failure";
            modelMap.put("submitted", meaning);
            modelMap.put("provided", testMeaning);
        }

        modelMap.put("result", result);
        modelMap.put("selected", word);
        modelMap.put("requester", requestUri);
        return "result";
    }


    @PostMapping("/words/delete/{name}")
    @ResponseBody
    public String delete(@PathVariable(value="name") String name){
        Word word = wordService.get(name);
        String status;
        try {
            status = wordService.delete(word);
        }catch (Exception e){
            status = "There was a problem deleting: " + e;
        }

        return status;
    }

}
