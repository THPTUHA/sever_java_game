package com.example.demo.repository;

import java.util.List;

import javax.transaction.Transactional;

import com.example.demo.model.Chat;
import com.example.demo.model.GamePlay;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatReposity extends JpaRepository<Chat,Integer>{
    @Query(value = "SELECT * FROM chat c order by c.id desc  LIMIT 50",nativeQuery = true)
    List<Chat> getChat();
   
}
