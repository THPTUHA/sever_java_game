package com.example.demo.service;

import javax.transaction.Transactional;

import com.example.demo.model.CustomUserDetails;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService{
    @Autowired
    UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) {
        User user=userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(email);
        }
        return new CustomUserDetails(user);
    }

    @Transactional
    public UserDetails loadUserById(int id) {
        User user=userRepository.findById(id);
        if (user == null) {
            throw new UsernameNotFoundException("User is not found");
        }
        return new CustomUserDetails(user);
    }
}
