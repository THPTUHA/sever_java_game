package com.example.demo;

public class LoginResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private boolean verify = false;
    public LoginResponse(){}
    public LoginResponse(String accessToken) {
        this.accessToken = accessToken;
        this.verify = true;
    }
    public String getAccessToken(){return this.accessToken;}
    public boolean getVerify(){return this.verify;}
    public String getTokenType(){return this.tokenType;}
    @Override
    public String toString(){
        return "token: "+accessToken+"/ntokenType: "+tokenType;
    }
}
