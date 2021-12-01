package com.example.demo.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.example.demo.model.User;

import org.springframework.stereotype.Repository;

@Repository
public class UserRepo {
    @PersistenceContext
    private EntityManager entityManager;
    
    public List<User> findById(int id){
        return entityManager.createQuery("SELECT u FROM User u WHERE u.id="+id,
          User.class).setMaxResults(1).getResultList();
    }

    public List<User>getGame(int id){
        return entityManager.createQuery("SELECT DISTINCT u FROM User u  JOIN FETCH  u.gameplay_1 d   WHERE u.id="+id,
          User.class).setMaxResults(1).getResultList();
    }
}
