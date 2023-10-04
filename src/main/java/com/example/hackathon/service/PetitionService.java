package com.example.hackathon.service;

import com.example.hackathon.dto.petition.PetitionRequest;
import com.example.hackathon.dto.petition.PetitionResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PetitionService {
    List<PetitionResponse> getAllPetitions();

    void save(PetitionRequest petitionRequest, String token);

    void update(PetitionRequest petitionResponse, String token);
    PetitionResponse update(PetitionRequest petitionResponse, Long id);

    void delete(String token, Long petitionId);

    void signToPetition(String token, Long petitionId);

    PetitionResponse getById(Long petitionId);

    String createPetitionAI(Long publicationId);

    Object uploadImagePetition(MultipartFile file, Long id);
}
