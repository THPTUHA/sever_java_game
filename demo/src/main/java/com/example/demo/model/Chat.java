package com.example.demo.model;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;



@Entity
@Table(name="chat")
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_id")
    private User user;
    @Column(name="message")
    private String message;
    @Column(name="send_time")
    private Date send_time;
    @Column(name="status")
    private int status;

    public int getId(){return this.id;}
    public String getUser_name(){return this.user.getFirst_name()+" "+this.user.getLast_name();}
    public String getMessage() {
        return message;
    }
    public String getAvatar() {
        return user.getAvatar();
    }
    public Date getSend_time() {
        return send_time;
    }
    public int getUser_id(){
        return this.user.getId();
    }    
    public int getStatus(){return this.status;}
    public void setStatus(int status){ this.status=status;}
    public Chat(){}
    public Chat(User user,String message, int status){
        this.user = user;
        this.message = message;
        this.status=status;
    }

    @Override
    public String toString(){
        return "id:"+this.id+"\nuser_id:";
    }
}
