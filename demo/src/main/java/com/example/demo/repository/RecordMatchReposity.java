package com.example.demo.repository;

import javax.transaction.Transactional;

import com.example.demo.model.RecordMatch;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordMatchReposity extends JpaRepository<RecordMatch,Integer> {
    @Modifying
    @Transactional
    @Query(value = "UPDATE recordmatch rd SET rd.status=:status WHERE rd.id=:id",nativeQuery = true)
    void updateStatus(@Param("status")int status, @Param("id")int id);
    @Modifying
    @Transactional
    @Query(value = "UPDATE recordmatch rd SET rd.data=:status WHERE rd.id=:id",nativeQuery = true)
    void updateData(@Param("status")String data, @Param("id")int id);
}
