package com.example.hackathon.service;

import com.example.hackathon.dto.person.PersonRequest;
import org.springframework.web.multipart.MultipartFile;

public interface PersonService {
    Object uploadAvatar(MultipartFile file, String token);


    void update(String token, PersonRequest personRequest);
}
