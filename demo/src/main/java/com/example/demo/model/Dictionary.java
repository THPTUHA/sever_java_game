package com.example.demo.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="dictionary")
public class Dictionary {
    @EmbeddedId
    KeyDic key;

    @Column(name="type")
    private String type;
    @Column(name="mean")
    private String mean;

    public String getMean() {
        return mean;
    }
    public KeyDic getKey() {
        return key;
    }
}
