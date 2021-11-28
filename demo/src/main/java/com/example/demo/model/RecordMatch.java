package com.example.demo.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="recordmatch")
public class RecordMatch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Column(name="start_time")
    private Date start_time;
    @Column(name="status")
    private int status;
    @Column(name="winner")
    private int winner;
    @Column(name="data")
    private String data;

    public int getId() {
        return id;
    }
    public Date getStart_time() {
        return start_time;
    }
    public int getStatus() {
        return status;
    }
    public int getWinner() {
        return winner;
    }

    public RecordMatch(){
        this.start_time = new Date();
    }
    public String getData() {
        return data;
    }
    @Override
    public String toString(){
        return "RecordMatch....:"+"\nwinner:"+this.winner+"\nid:"+this.id;
    }
}
