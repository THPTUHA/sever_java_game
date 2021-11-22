package com.example.demo.model;

public class Mail {
    private String to;
    private String body;
    private String subject;
    public Mail(String to,String body,String subject){
        this.to=to;
        this.body=body;
        this.subject=subject;
    }

    public String getBody() {
        return body;
    }
    public String getTo() {
        return to;
    }
    public String getSubject() {
        return subject;
    }
}
