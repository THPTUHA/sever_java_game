package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="indexgame")
public class IndexGame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="status")
    private int status;

    public IndexGame(int status){
        this.status = status;
    }
    public int getId() {
        return id;
    }
    public int getStatus() {
        return status;
    }
}
