package com.example.hackathon.controller;


import com.example.hackathon.dto.user.UserRequest;
import com.example.hackathon.dto.authenticate.AuthenticationRequest;
import com.example.hackathon.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
@AllArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<?> userRegister(@RequestBody UserRequest request) {
        return service.userRegister(request);
    }

    //авторизация
    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(service.authenticate(request));
    }
    @PostMapping("/register/admin")
    public ResponseEntity<?> adminRegister(@RequestBody UserRequest request) {
        return service.adminRegister(request);
    }
}
