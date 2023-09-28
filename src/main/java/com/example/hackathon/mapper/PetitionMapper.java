package com.example.hackathon.mapper;

import com.example.hackathon.dto.petition.PetitionRequest;
import com.example.hackathon.dto.petition.PetitionResponse;
import com.example.hackathon.entities.Petition;

import java.util.List;

public interface PetitionMapper {
    List<PetitionResponse> toDtos(List<Petition> all);

    Petition toEntity(PetitionResponse petitionResponse);

    Petition toEntityFromRequest(PetitionRequest petitionResponse);

    PetitionResponse toDto(Petition petition);
}
