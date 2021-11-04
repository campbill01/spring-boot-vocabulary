package com.campbell.vocabulary.repository;

import com.campbell.vocabulary.domain.Word;
import org.springframework.data.repository.CrudRepository;

public interface wordRepository extends CrudRepository<Word, Long>{
    Word findFirstByOrderById();
}