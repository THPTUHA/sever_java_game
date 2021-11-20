package com.example.demo;

import javax.validation.constraints.NotBlank;

public class LoginRequest {
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    public String getEmail(){return this.email;}
    public String getPassword(){return this.password;}
    @Override
    public String toString(){
        return this.email+" "+this.password;
    }
}
