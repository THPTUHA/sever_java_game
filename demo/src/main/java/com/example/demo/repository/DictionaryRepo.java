package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.demo.model.Dictionary;

@Repository
public interface DictionaryRepo extends JpaRepository<Dictionary,Integer>{
    @Query(value = "SELECT EXISTS (SELECT dic.word FROM av dic WHERE dic.word=:word)",nativeQuery = true)
    int checkWord(@Param("word")String word);
}
