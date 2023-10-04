package com.example.hackathon.service.Impl;

import com.example.hackathon.dto.petition.PetitionRequest;
import com.example.hackathon.dto.petition.PetitionResponse;
import com.example.hackathon.entities.*;
import com.example.hackathon.enums.Role;
import com.example.hackathon.mapper.FileDataMapper;
import com.example.hackathon.mapper.PetitionMapper;
import com.example.hackathon.repository.CommentRepository;
import com.example.hackathon.repository.FileDataRepository;
import com.example.hackathon.repository.PetitionRepository;
import com.example.hackathon.repository.PublicationRepository;
import com.example.hackathon.service.FileDataService;
import com.example.hackathon.service.OpenAIApiService;
import com.example.hackathon.service.PetitionService;
import com.example.hackathon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class PetitionServiceImpl implements PetitionService {


    @Value("${openai.api.key}")
    public String apiKey;

    @Autowired
    private  PetitionMapper petitionMapper;
    @Autowired
    private OpenAIApiService openAIApiService;
    @Autowired

    private  PetitionRepository petitionRepository;
    @Autowired

    private  UserService userService;
    @Autowired

    private  FileDataRepository fileDataRepository;
    @Autowired

    private  PublicationRepository publicationRepository;
    @Autowired

    private  FileDataService fileDataService;
    @Autowired

    private  FileDataMapper fileDataMapper;
    @Autowired

    private  CommentRepository commentRepository;


    @Override
    public List<PetitionResponse> getAllPetitions() {
        System.out.println("\n\nthe key:+"+apiKey);

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
            petition.setFromGPT(false);
            petition.setGoal(50);
            petition.setCountOfSignIn(0);
            petition.setCreationDate(petitionResponse.getCreationDate()!=null? petitionResponse.getCreationDate() : null);
            petitionRepository.save(petition);
        }
    }

    @Override
    public PetitionResponse update(PetitionRequest petitionRequest, Long id) {
        Petition petition = petitionRepository.findById(id).orElseThrow();
        petition.setName(petitionRequest.getName());
        petition.setAuthor(petitionRequest.getAuthor());
        petition.setDescription(petitionRequest.getDescription());
        petition.setGoal(petitionRequest.getGoal());
        Optional<FileData> fileData = fileDataRepository.findById(petitionRequest.getImageId()!=null? petitionRequest.getImageId(): 0);
        if (fileData.isPresent()){
            petition.setImageOfPetition(fileData.get());

        }
        petitionRepository.save(petition);
        return petitionMapper.toDto(petition);
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
    public String createPetitionAI(Long publicationId) {
        System.out.println("the function is called");
        Publication publication = publicationRepository.findById(publicationId).get();
        List<Comment> topComments = commentRepository.findTop3ByPublicationOrderByLikeCountDesc(publication);
        StringBuilder commentsText = new StringBuilder();
        for (Comment comment : topComments) {
            commentsText.append(comment.getComment()).append(" "); // Разделители по желанию
        }
        System.out.println("the comments: "+commentsText);
        return openAIApiService.getResponse("оцени данную публикацию(тут название/комментарии и описание) и переработай на петицию, название: "+ publication.getName()+", описание: "+ publication.getDescription()+" и также несколько актуальных коментариев: "+ commentsText+".Пример того как я хочу получить ответ: Название:бла-бла-бла. Описание:бла-бла-бла");

    }
    @Override
    public Object uploadImagePetition(MultipartFile file, Long id) {


        Petition petition = new Petition();
        FileData fileData = new FileData();

        if (id!=null){
            petition = petitionRepository.findById(id).orElseThrow();
            petition.setImageOfPetition(fileData);

        }

        if (petition.getImageOfPetition() != null) {
            fileData = petition.getImageOfPetition();
            petition.setImageOfPetition(null);
            FileData save = fileDataService.uploadFile(file, fileData);
            petition.setImageOfPetition(save);
            Object o = id!=null? petitionRepository.save(petition): "";
            return fileDataMapper.toDto(save);
        } else {
            fileData = fileDataService.uploadFile(file);
            Object o = id!=null? petitionRepository.save(petition): "";
            return fileDataMapper.toDto(fileData);
        }



    }

}
