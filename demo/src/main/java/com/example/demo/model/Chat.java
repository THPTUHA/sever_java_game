package com.example.demo.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name="chat")
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Column(name="user_id")
    private int user_id;
    @Column(name="user_name")
    private String user_name;
    @Column(name="message")
    private String message;
    @Column(name="time")
    private Date time;
    @Column(name="status")
    private int status;

    public int getId(){return this.id;}
    public int getUser_id(){return this.user_id;}
    public String getUser_name(){return this.user_name;}
    public String getMessage(){return this.message;}
    public int getStatus(){return this.status;}
    public void setStatus(int status){ this.status=status;}

    public Chat(int id,int user_id,String user_name,String message, int status){
        this.id=id;
        this.user_id = user_id;
        this.user_name = user_name;
        this.message = message;
        this.status=status;
    }

    @Override
    public String toString(){
        return "id:"+this.id+"\nuser_id:"+this.user_id+"\nuser_name:"+this.user_name+"\nmessage:"+this.message;
    }
}
