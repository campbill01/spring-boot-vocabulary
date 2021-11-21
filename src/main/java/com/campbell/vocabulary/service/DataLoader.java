package com.campbell.vocabulary.service;

import com.campbell.vocabulary.domain.Word;
import com.campbell.vocabulary.repository.WordRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Profile("local")
public class DataLoader {

    private static final String COMMA_DELIMITER = ",";
    private final WordRepository wordRepository;

    //@Autowired
    public DataLoader(WordRepository wordRepository){
        this.wordRepository = wordRepository;
    }

    @PostConstruct
    public void loadData() {
        //LOG.info(Arrays.asList(environment.getDefaultProfiles()));
        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/spanish_vocabulary.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                //System.out.println(values);
                records.add(Arrays.asList(values));
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(List<String> record: records){
            if(record.size() > 1) {
                String word = record.get(0);
                String meaning = record.get(1);
                Word newWord = new Word(word, meaning);
                wordRepository.save(newWord);
            } else {
                System.out.println("There was a problem processing " + record.get(0));
            }
        }
    }
}
