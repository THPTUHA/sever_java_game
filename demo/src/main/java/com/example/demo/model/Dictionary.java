package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="av")
public class Dictionary {

    @Id
    @Column(name="word")
    private String word;
    @Column(name="pro")
    private String pro;
    @Column(name="mean")
    private String mean;

    public String getMean() {
        return mean;
    }
    
    public String getPro() {
        return pro;
    }

    public String getWord() {
        return word;
    }
}
