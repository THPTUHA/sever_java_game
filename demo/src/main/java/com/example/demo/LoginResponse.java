package com.example.demo;

public class LoginResponse {
    private String accessToken;
    private String tokenType = "Bearer";

    public LoginResponse(String accessToken) {
        this.accessToken = accessToken;
    }
    public String getAccessToken(){return this.accessToken;}
    public String getTokenType(){return this.tokenType;}
    @Override
    public String toString(){
        return "token: "+accessToken+"/ntokenType: "+tokenType;
    }
}
