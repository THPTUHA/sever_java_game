package com.example.demo.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
public class CustomUserDetails implements UserDetails{
    private User user;
    public CustomUserDetails(){}
    public CustomUserDetails(User user){this.user=user;}
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority>roles = new HashSet<>();
        System.out.println("ROLE>>>>>>>>>>>>>>>");
        roles.add(new SimpleGrantedAuthority(user.getRole()));
        return roles;
    }
    public User getUser(){
        return this.user;
    }
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.getExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.getLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.getEnabled();
    }
}

