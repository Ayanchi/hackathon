package com.example.hackathon.service;

import com.example.hackathon.dto.petition.PetitionRequest;
import com.example.hackathon.dto.petition.PetitionResponse;

import java.util.List;

public interface PetitionService {
    List<PetitionResponse> getAllPetitions();

    void save(PetitionRequest petitionRequest, String token);

    void update(PetitionRequest petitionResponse, String token);

    void delete(String token, Long petitionId);

    void signToPetition(String token, Long petitionId);

    PetitionResponse getById(Long petitionId);

    PetitionResponse createPetitionAI(Long publicationId);
}
