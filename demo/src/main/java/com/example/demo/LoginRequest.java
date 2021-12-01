package com.example.demo;

import javax.validation.constraints.NotBlank;

public class LoginRequest {
    @NotBlank
    private String email;

    @NotBlank
    private String password;
    private String new_password;

    public String getEmail(){return this.email;}
    public String getPassword(){return this.password;}
    public String getNew_password() {
        return new_password;
    }
    @Override
    public String toString(){
        return this.email+" "+this.password+" "+this.new_password;
    }
}
