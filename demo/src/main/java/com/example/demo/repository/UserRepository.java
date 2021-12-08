package com.example.demo.repository;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import com.example.demo.model.GamePlay;
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
        void updateLastLogin(@Param("last_login")long last_login,@Param("email")String email);
        @Modifying
        @Transactional
        @Query(value = "UPDATE User u SET u.gold=:gold, u.exp=:exp WHERE u.id=:id",nativeQuery = true)
        void updateGoldExp(@Param("gold")long gold,@Param("exp")long exp, @Param("id")int id);
        @Modifying
        @Transactional
        @Query(value = "UPDATE User u SET u.password=:password WHERE u.id=:id",nativeQuery = true)
        void updatePassword(@Param("password")String password, @Param("id")int id);
        @Modifying
        @Transactional
        @Query(value = "UPDATE User u SET u.phone=:phone,u.description=:description WHERE u.id=:id",nativeQuery = true)
        void updateInfo(@Param("phone")String phone,@Param("description")String description, @Param("id")int id);

        @Modifying
        @Transactional
        @Query(value = "UPDATE User u SET u.status=:status WHERE u.id=:id",nativeQuery = true)
        void updateStatus(@Param("status")int status, @Param("id")int id);
        @Modifying
        @Transactional
        @Query(value = "UPDATE User u SET u.avatar=:avatar WHERE u.id=:id",nativeQuery = true)
        void updateAvatar(@Param("avatar")String avatar, @Param("id")int id);
        @Modifying
        @Transactional
        @Query(value = "UPDATE User u SET u.role=:role WHERE u.id=:id",nativeQuery = true)
        void updateRole(@Param("role")String role, @Param("id")int id);
        
        @Query(value = "SELECT DISTINCT u FROM User u  JOIN FETCH  u.gameplay_1  d   WHERE u.id=:id ")
        User getGame_1(@Param("id")int id);
        
        @Query(value = "SELECT DISTINCT u FROM User u  JOIN FETCH  u.gameplay_2  d   WHERE u.id=:id ")
        User getGame_2(@Param("id")int id);

        @Query(value = "SELECT DISTINCT u FROM User u  JOIN FETCH  u.gameplay_3  d   WHERE u.id=:id ")
        User getGame_3(@Param("id")int id);

        @Query(value = "SELECT DISTINCT u FROM User u  JOIN FETCH  u.gameplay_4  d   WHERE u.id=:id ")
        User getGame_4(@Param("id")int id);

        @Query(value = "select *  from user order by user.id desc limit 5 offset :pos ",nativeQuery = true)
        List<User> loading(@Param("pos")int pos);

        @Query(value = "select count(*) from user",nativeQuery = true)
        int numberUser();

         @Query(value = "select *  from user  where user.role=:role order by user.id desc  limit 5 offset :pos",nativeQuery = true)
        List<User> loadingByRole(@Param("pos")int pos,@Param("role")String role);

        @Query(value = "select count(*) from user where user.role=:role",nativeQuery = true)
        int numberUserByRole(@Param("role")String role);

        User findByEmail(String email);
        User findById(int id);
        void deleteById(int id);
}
