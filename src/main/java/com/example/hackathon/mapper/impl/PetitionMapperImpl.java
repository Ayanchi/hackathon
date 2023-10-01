package com.example.hackathon.mapper.impl;

import com.example.hackathon.dto.petition.PetitionRequest;
import com.example.hackathon.dto.petition.PetitionResponse;
import com.example.hackathon.entities.Petition;
import com.example.hackathon.mapper.FileDataMapper;
import com.example.hackathon.mapper.PetitionMapper;
import com.example.hackathon.repository.FileDataRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class PetitionMapperImpl implements PetitionMapper {

    private final FileDataMapper fileDataMapper;
    private final FileDataRepository fileDataRepository;
    @Override
    public List<PetitionResponse> toDtos(List<Petition> all) {
        List<PetitionResponse> petitionResponses = new ArrayList<>();
        for(Petition petition: all){
            petitionResponses.add(toDto(petition));
        }
        return petitionResponses;
    }

    @Override
    public Petition toEntity(PetitionResponse petitionResponse) {
        Petition petition = new Petition();
        petition.setAuthor(petition.getAuthor()!=null? petition.getAuthor() : null);
        petition.setImageOfPetition(petitionResponse.getFileDataResponse()==null?null:
                fileDataMapper.toEntity((petitionResponse.getFileDataResponse())));
        petition.setName(petitionResponse.getName());
        petition.setDescription(petitionResponse.getDescription());
        petition.setCreationDate(petitionResponse.getCreationDate()!=null? petitionResponse.getCreationDate() : null);
        return petition;
    }

    @Override
    public Petition toEntityFromRequest(PetitionRequest petitionResponse) {
        Petition petition = new Petition();
        petition.setDescription(petitionResponse.getDescription());
        petition.setImageOfPetition(petitionResponse.getImageId()!=null? fileDataRepository.findById(petitionResponse.getImageId()).get():null);
        petition.setAuthor(petitionResponse.getAuthor());
        petition.setName(petitionResponse.getName());
        petition.setCreationDate(LocalDateTime.now().toString());
        petition.setGoal(50000);
        petition.setCountOfSignIn(0);

        return petition;
    }

    @Override
    public PetitionResponse toDto(Petition petition) {
        PetitionResponse petitionResponse = new PetitionResponse();
        petitionResponse.setFileDataResponse(petition.getImageOfPetition()==null?null:
                fileDataMapper.toDto(petition.getImageOfPetition()));
        petitionResponse.setId(petition.getId());
        petitionResponse.setName(petition.getName());
        petitionResponse.setDescription(petition.getDescription());
        petitionResponse.setAuthor(petition.getAuthor());
        petitionResponse.setCreationDate(petition.getCreationDate());
        petitionResponse.setGoal(petition.getGoal());
        petitionResponse.setCountSigned(petition.getCountOfSignIn());

        return petitionResponse;
    }
}
