package com.example.hackathon.controller;


import com.example.hackathon.entities.User;
import com.example.hackathon.enums.Role;
import com.example.hackathon.repository.UserRepository;
import com.example.hackathon.service.EmailSenderService;
import com.example.hackathon.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/email")
@CrossOrigin(origins = "*", maxAge = 3600)
@AllArgsConstructor
public class EmailSenderController {
    private final UserRepository userRepository;
    private final UserService userService;

    @GetMapping("/check")
    public Boolean checkVerificationCode(@RequestParam(required = false) Integer code){
        Optional<User> user = userRepository.findByVerifyCode(code);
        if(user.isEmpty())
            return false;
        else {
            user.get().setRole(Role.USER);
            userRepository.save(user.get());
            return true;
        }
    }
}
