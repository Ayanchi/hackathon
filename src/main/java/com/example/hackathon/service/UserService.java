package com.example.hackathon.service;

import com.example.hackathon.entities.User;

public interface UserService {
    User getUsernameFromToken(String token);
}
