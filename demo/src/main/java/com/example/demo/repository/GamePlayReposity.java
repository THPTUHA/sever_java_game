package com.example.demo.repository;

import java.util.Date;
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
    @Query(value = "UPDATE recordusergame p SET p.point_1=:point_1,p.point_2=:point_2,p.point_3=:point_3,p.point_4=:point_4, p.status=:status  WHERE p.id=:id",nativeQuery = true)
    void updatePlayGame(@Param("id")int match_id, @Param("point_1")int point_1 ,@Param("point_2")int point_2,@Param("point_3")int point_3,@Param("point_4")int point_4,@Param("status")int status);
    @Modifying
    @Transactional
    @Query(value = "INSERT recordusergame (id,game_id,start_time,user_id_1,user_id_2,user_id_3,user_id_4,point_1,point_2,point_3,point_4,status,winner) values (:id,:game_id,:start_time,:user_id_1,:user_id_2,:user_id_3,:user_id_4,:point_1,:point_2,:point_3,:point_4,:status,:winner)",nativeQuery = true)
    void addRecord(@Param("id")int match_id,@Param("game_id")int game_id,@Param("start_time")long start_time,@Param("user_id_1")int user_id_1,@Param("user_id_2")int user_id_2,@Param("user_id_3")int user_id_3,@Param("user_id_4")int user_id_4, @Param("point_1")int point_1 ,@Param("point_2")int point_2,@Param("point_3")int point_3,@Param("point_4")int point_4,@Param("status")int status,@Param("winner")int winner);
    
    @Query(value = "SELECT * FROM recordusergame p  WHERE p.id=:id ",nativeQuery = true)
    GamePlay findById(@Param("id")int id);
    @Query(value = "SELECT id FROM recordusergame c order by c.id desc  LIMIT 1",nativeQuery = true)
    int getMatchId();
    
}
