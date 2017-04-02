package com.captcha.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.captcha.model.User;
import com.captcha.repository.UserRepository;



@Service
public class UserService{
	@Autowired
    private UserRepository userRepository;
    

    public User getByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }
}
