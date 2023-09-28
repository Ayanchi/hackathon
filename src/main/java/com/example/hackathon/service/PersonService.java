package com.example.hackathon.service;

import com.example.hackathon.dto.person.PersonRequest;
import org.springframework.web.multipart.MultipartFile;

public interface PersonService {
    Object uploadResume(MultipartFile file, String token);

    Object uploadImagePublication(MultipartFile file, String token, Long petitionId);
    Object uploadImagePitition(MultipartFile file, String token);

    Object uploadImagePitition(MultipartFile file, String token, Long petitionId);

    void update(String token, PersonRequest personRequest);
}
