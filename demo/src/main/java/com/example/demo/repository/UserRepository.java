package com.example.demo.repository;

import java.util.Date;

import javax.transaction.Transactional;

import com.example.demo.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer>{
        @Modifying
        @Transactional
        @Query(value = "UPDATE User u SET u.last_login=:last_login WHERE u.email=:email",nativeQuery = true)
        void updateLastLogin(@Param("last_login")Date last_login,@Param("email")String email);
        User findByEmail(String email);
        User findById(int id);
}
