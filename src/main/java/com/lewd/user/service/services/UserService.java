package com.lewd.user.service.services;

import com.lewd.user.service.entity.User;
import com.lewd.user.service.exceptions.ResourceNotFoundException;
import com.lewd.user.service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user){
        String userId = UUID.randomUUID().toString();
        user.setUserId(userId);
       return userRepository.save(user);
    }

    public List<User> getAllUser(){
    return userRepository.findAll();
    }

    public User getUserById(String userId){
    return userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found "+userId));
    }
}
