package com.example.demo.repository;

import com.example.demo.model.Game;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameReposity extends JpaRepository<Game,Integer>  {
    Game findById(int id);
}
