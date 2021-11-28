package com.example.demo.repository;

import java.util.List;

import javax.transaction.Transactional;

import com.example.demo.model.GamePlay;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GamePlayReposity extends JpaRepository<GamePlay,Integer>{
    @Query(value = "SELECT * FROM play p WHERE p.status=:status LIMIT 1",nativeQuery = true)
    GamePlay getOneMatch(@Param("status")int status);
    @Modifying
    @Transactional
    @Query(value = "UPDATE play p SET p.player=:player, p.status=:status , p.user_num=:user_num WHERE p.id=:id",nativeQuery = true)
    void updatePlayGame(@Param("id")int id, @Param("player")String player,@Param("status")int status,@Param("user_num")int user_num);
    @Modifying
    @Transactional
    @Query(value = "INSERT recordusergame (game_id,match_id,user_id,opponent,status) values (:game_id,:match_id,:user_id,:opponent,:status)",nativeQuery = true)
    void addRecord(@Param("game_id")int game_id,@Param("match_id")int match_id,@Param("user_id")int user_id,@Param("opponent")String opponent,@Param("status")int status);
    @Query(value = "SELECT * FROM recordusergame p  WHERE p.id=:id ",nativeQuery = true)
    GamePlay findById(@Param("id")int id);
    @Query(value = "SELECT * FROM recordusergame p  WHERE p.user_id=:id ",nativeQuery = true)
    List<GamePlay> getHistory(@Param("id")int user_id);
}
