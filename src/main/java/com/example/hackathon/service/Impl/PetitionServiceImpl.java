package com.example.hackathon.service.Impl;

import com.example.hackathon.ChatGpt.OpenAIApiClient;
import com.example.hackathon.dto.petition.PetitionRequest;
import com.example.hackathon.dto.petition.PetitionResponse;
import com.example.hackathon.entities.Person;
import com.example.hackathon.entities.Petition;
import com.example.hackathon.entities.Publication;
import com.example.hackathon.entities.User;
import com.example.hackathon.enums.Role;
import com.example.hackathon.mapper.PetitionMapper;
import com.example.hackathon.repository.FileDataRepository;
import com.example.hackathon.repository.PetitionRepository;
import com.example.hackathon.repository.PublicationRepository;
import com.example.hackathon.service.OpenAIApiService;
import com.example.hackathon.service.PetitionService;
import com.example.hackathon.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class PetitionServiceImpl implements PetitionService {
    private final PetitionMapper petitionMapper;
    private final PetitionRepository petitionRepository;
    private final UserService userService;
    private final FileDataRepository fileDataRepository;
    private final PublicationRepository publicationRepository;
    @Override
    public List<PetitionResponse> getAllPetitions() {
        return petitionMapper.toDtos(petitionRepository.findAll());
    }

    @Override
    public void save(PetitionRequest petitionResponse, String token) {
        if(userService.getUsernameFromToken(token).getRole().equals(Role.ADMIN)){
            petitionRepository.save(petitionMapper.toEntityFromRequest(petitionResponse));
        }
    }

    @Override
    public void update(PetitionRequest petitionResponse, String token) {
        if(userService.getUsernameFromToken(token).getRole().equals(Role.ADMIN)){
            Petition petition = petitionRepository.findById(petitionResponse.getId()).get();
            petition.setImageOfPetition(petitionResponse.getImageId()!=null?fileDataRepository.findById(petitionResponse.getImageId()).get():null);
            petition.setDescription(petitionResponse.getDescription());
            petition.setAuthor(petitionResponse.getAuthor());
            petition.setName(petitionResponse.getName());
            petition.setCreationDate(petitionResponse.getCreationDate()!=null? petitionResponse.getCreationDate() : null);
            petitionRepository.save(petition);
        }
    }

    @Override
    public void delete(String token, Long petitionId) {
        if(userService.getUsernameFromToken(token).getRole().equals(Role.ADMIN)){
            petitionRepository.deleteById(petitionId);
        }
    }

    @Override
    public void signToPetition(String token, Long petitionId) {
        if (userService.getUsernameFromToken(token).getRole().equals(Role.ADMIN))
            return;
        Person person = userService.getUsernameFromToken(token).getPerson();
        Petition petition = petitionRepository.findById(petitionId).orElseThrow(() -> new IllegalArgumentException("Petition not found"));

        List<Person> signedPersons = petition.getSignedPersons();
        if (signedPersons == null) {
            signedPersons = new ArrayList<>();
        }

        if (signedPersons.contains(person)) {
            System.out.println("its contain");
            signedPersons.remove(person);
            petition.setCountOfSignIn(petition.getCountOfSignIn() == null ? 0 : petition.getCountOfSignIn() - 1);
        } else {
            System.out.println("its not contain");
            signedPersons.add(person);
            petition.setCountOfSignIn(petition.getCountOfSignIn() == null ? 1 : petition.getCountOfSignIn() + 1);
        }

        petition.setSignedPersons(signedPersons);
        petitionRepository.save(petition);
    }


    @Override
    public PetitionResponse getById(Long petitionId) {
        return petitionMapper.toDto(petitionRepository.findById(petitionId).get());
    }

    @Override
    public PetitionResponse createPetitionAI(Long publicationId) {
        Publication publication = publicationRepository.findById(publicationId).get();
        OpenAIApiService aiApiService = new OpenAIApiService();
        aiApiService.getResponse("privet");
//        OpenAIApiClient apiClient = new OpenAIApiClient();
//        String response = apiClient.getResponse("privet");
//        System.out.println(response);

//        String response = apiClient.getResponse("оцени данную петицию переработай, название: "+
//                publication.getName()+", описание: "+ publication.getDescription()+" и также несколько актуальных коментарии: A)круто B)ужас C)невереятно");
//        System.out.println(response);
        return null;
    }
}
