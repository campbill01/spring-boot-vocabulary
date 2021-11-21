package com.campbell.vocabulary.repository;

import com.campbell.vocabulary.domain.Word;
import org.springframework.data.repository.CrudRepository;

public interface WordRepository extends CrudRepository<Word, Long>{
    Word findFirstByOrderByIdAsc();
}
