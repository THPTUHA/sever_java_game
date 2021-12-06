package com.example.demo.repository;

import java.util.List;

import javax.transaction.Transactional;

import com.example.demo.model.News;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRespository extends JpaRepository<News,Integer>{
    News findById(int id);

    @Query(value = "SELECT DISTINCT n FROM News n  JOIN FETCH  n.comment d  JOIN FETCH  n.user f WHERE n.id=:id ")
    News getNewsById(@Param("id")int id);

    @Query(value = "select * from news order by news.id desc limit 5 offset :pos ",nativeQuery = true)
    List<News> getNews(@Param("pos")int pos);

    @Modifying
    @Transactional
    @Query(value = "INSERT news (user_id,content,title,describes,time_create,last_update,status,background_image,view, like_num) values (:user_id,:content,:title,:describes,:time_create,:last_update,:status,:background_image,:view,:like_num)",nativeQuery = true)
    void addNews(@Param("user_id")int user_id,@Param("content")String content,@Param("title")String title,@Param("describes")String describes,@Param("time_create")long time_create,@Param("last_update")long last_update,@Param("status")int status,@Param("background_image")String background_image,@Param("view")int view,@Param("like_num")int like_num);
}
