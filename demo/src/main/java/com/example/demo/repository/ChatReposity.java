package com.example.demo.repository;

import java.util.Date;
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
    @Query(value = "SELECT * FROM chat c order by c.id desc  LIMIT 20",nativeQuery = true)
    List<Chat> getChat();
    @Query(value = "select * from chat order by chat.id desc limit 5 offset :pos ",nativeQuery = true)
    List<Chat> loading(@Param("pos")int pos);
    @Modifying
    @Transactional
    @Query(value = "INSERT chat (user_id,message,send_time,status) values (:user_id,:message,:send_time,:status)",nativeQuery = true)
    void addChat(@Param("user_id")int user_id,@Param("message")String message,@Param("send_time")Date send_time,@Param("status")int status);
}
