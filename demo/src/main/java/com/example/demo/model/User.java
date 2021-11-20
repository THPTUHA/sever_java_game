package com.example.demo.model;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="user")

public class User implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;
    @Column(name="first_name")
    private String first_name;
    @Column(name="last_name")
    private String last_name;
    @Column(name="email")
    private String email;
    @Column(name="password")
    private String password;
    @Column(name="role")
    private String role = "ROLE_USER";
    @Column(name="avatar")
    private String avatar;
    @Column(name="address")
    private String address;
    @Column(name="phone")
    private String phone;
    @Column(name="discription")
    private String description;
    @Column(name="sex")
    private int sex;
    @Column(name="facebook")
    private String facebook;
    @Column(name="last_login")
    private Date last_login;
    @Column(name="expired")
    private boolean expired=true;
    @Column(name="locked")
    private boolean locked=true;
    @Column(name="enabled")
    private boolean enabled=true;
    @Column(name="exp")
    private long exp;
    @Column(name="gold")
    private long gold;
    @Column(name="status")
    private int status;

    public User(){}
    public User(String eamil,String password){
        this.email=eamil;
        this.password=password;
    }
    public int getId(){return this.id;}
    public String getFirst_name(){return this.first_name;}
    public String getLast_name(){return this.last_name;}
    public String getEmail(){return this.email;}
    public String getPassword(){return this.password;}
    public String getRole(){return this.role;}
    public String getAvatar(){return this.avatar;}
    public boolean getExpired(){return this.expired;}
    public int getSex(){return this.sex;}
    public boolean getLocked(){return this.locked;}
    public boolean getEnabled(){return this.enabled;}
    public Date getLast_login(){return this.last_login;}
    public long getExp(){return this.exp;}
    public long getGold(){return this.gold;}
    public void setPassword(String password){this.password=password;}
    public int getStatus(){return this.status;}
    public void setStatus(int status){ this.status = status;}
    @Override
    public String toString(){
        return "id: "+ id + "\nfirst_name: "+ first_name+"\nlast_name:"+last_name+"\nemail: "+email
        +"\npassword: "+password +"\nrole: "+role+"\naddress: "+address+"\nphone: "+phone
        +"\ndescription: "+description +"\nsex: "+sex+"\nfacebook: "+facebook+"\nlase_login: "+last_login
        +"\nexpired: "+expired+"\nlocked:"+locked+"\nenabled:"+enabled+"\nexp:"+this.exp+"\ngold"+this.gold;
    }
}

