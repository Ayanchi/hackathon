package com.example.hackathon.service;

import com.example.hackathon.dto.user.UserRequest;
import com.example.hackathon.dto.authenticate.AuthenticationRequest;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {
    ResponseEntity<?> userRegister(UserRequest request);

    Object authenticate(AuthenticationRequest request);

    ResponseEntity<?> adminRegister(UserRequest request);
}
