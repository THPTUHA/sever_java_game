package com.example.demo.repository;

import com.example.demo.model.IndexGame;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndexGameRepo  extends JpaRepository<IndexGame,Integer>{
    
}
