package com.example.demo.repository;

import javax.transaction.Transactional;

import com.example.demo.model.CommentNews;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentNewsRepo extends JpaRepository<CommentNews,Integer> {
    @Modifying
    @Transactional
    @Query(value = "INSERT commentnews (news_id,user_id,content,since,status) values (:news_id,:user_id,:content,:since,:status)",nativeQuery = true)
    void addComment(@Param("news_id")int news_id,@Param("user_id")int user_id,@Param("content")String content,@Param("since")long since,@Param("status")int status);
    
}
