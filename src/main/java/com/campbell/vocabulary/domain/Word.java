package com.campbell.vocabulary.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Word {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //@Column(name = "id", nullable = false, updatable = false)
    Long id;
    public String name;
    public String meaning;

    public Word(){

    }

    public Word(String name, String meaning) {
        this.name = name;
        this.meaning = meaning;
    }

    public Long getId() {
        return id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }
}
