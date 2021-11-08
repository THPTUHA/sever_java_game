package com.example.demo.repository;

import javax.transaction.Transactional;

import com.example.demo.model.GamePlay;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GamePlayReposity extends JpaRepository<GamePlay,Integer>{
    @Query(value = "SELECT * FROM play p WHERE p.user_num<:user_num and p.id_game=:id_game LIMIT 1",nativeQuery = true)
    GamePlay getOneGamePlay(@Param("user_num")int user_num,@Param("id_game")int id_game);
    @Query(value = "SELECT * FROM play p WHERE p.status=:status LIMIT 1",nativeQuery = true)
    GamePlay getOneMatch(@Param("status")int status);
    @Modifying
    @Transactional
    @Query(value = "UPDATE play p SET p.player=:player, p.status=:status , p.user_num=:user_num WHERE p.id=:id",nativeQuery = true)
    void updatePlayGame(@Param("id")int id, @Param("player")String player,@Param("status")int status,@Param("user_num")int user_num);
}
