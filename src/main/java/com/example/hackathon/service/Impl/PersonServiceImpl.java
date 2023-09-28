package com.example.hackathon.service.Impl;

import com.example.hackathon.dto.file.FileDataResponse;
import com.example.hackathon.dto.person.PersonRequest;
import com.example.hackathon.entities.*;
import com.example.hackathon.enums.Role;
import com.example.hackathon.mapper.FileDataMapper;
import com.example.hackathon.repository.*;
import com.example.hackathon.service.FileDataService;
import com.example.hackathon.service.PersonService;
import com.example.hackathon.service.PublicationService;
import com.example.hackathon.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class PersonServiceImpl implements PersonService {
    private final UserRepository userRepository;
    private final FileDataService fileDataService;
    private final PersonRepository personRepository;
    private final FileDataMapper fileDataMapper;
    private final UserService userService;
    private final PublicationService publicationService;
    private final PublicationRepository publicationRepository;
    private final PetitionRepository petitionRepository;
    private final FileDataRepository fileDataRepository;
    @Override
    public FileDataResponse uploadResume(MultipartFile file, String token) {
        User user = userService.getUsernameFromToken(token);
        if (user.getRole() == Role.USER) {
            Person person = user.getPerson();
            if (person.getPersonAvatar() != null) {
                FileData fileData = new FileData();
                fileData = person.getPersonAvatar();
                person.setPersonAvatar(null);
                FileData save = fileDataService.uploadFile(file, fileData);
                person.setPersonAvatar(save);
                personRepository.save(person);
                return fileDataMapper.toDto(save);
            } else {
                FileData fileData = fileDataService.uploadFile(file);
                person.setPersonAvatar(fileData);
                personRepository.save(person);
                return fileDataMapper.toDto(fileData);
            }
        }
        return null;
    }

    @Override
    public Object uploadImagePublication(MultipartFile file, String token, Long publicationId) {

        User user = userService.getUsernameFromToken(token);
        if (user.getRole() == Role.USER) {
            Publication publication = publicationRepository.findById(publicationId).orElseThrow();

            if (publication.getPetitionImage() != null) {
                FileData fileData = new FileData();
                fileData = publication.getPetitionImage();
                publication.setPetitionImage(null);
                FileData save = fileDataService.uploadFile(file, fileData);
                publication.setPetitionImage(save);
                return fileDataMapper.toDto(save);
            } else {
                FileData fileData = fileDataService.uploadFile(file);
                publication.setPetitionImage(fileData);
                publicationRepository.save(publication);
                return fileDataMapper.toDto(fileData);
            }
        }
        return null;
    }

    @Override
    public Object uploadImagePitition(MultipartFile file, String token, Long petitionId) {
        User user = userService.getUsernameFromToken(token);
        if (user.getRole() == Role.ADMIN) {
            Petition petition = petitionRepository.findById(petitionId).get();

            if (petition.getImageOfPetition() != null) {
                FileData fileData = new FileData();
                fileData = petition.getImageOfPetition();
                petition.setImageOfPetition(null);
                FileData save = fileDataService.uploadFile(file, fileData);
                petition.setImageOfPetition(save);
                return fileDataMapper.toDto(save);
            } else {
                FileData fileData = fileDataService.uploadFile(file);
                petition.setImageOfPetition(fileData);
                petitionRepository.save(petition);
                return fileDataMapper.toDto(fileData);
            }
        }
        return null;
    }
    @Override
    public Object uploadImagePitition(MultipartFile file, String token) {
        User user = userService.getUsernameFromToken(token);
        if (user.getRole() != Role.ADMIN) {
            Person person = userService.getUsernameFromToken(token).getPerson();

            if (person.getPassportImage() != null) {
                FileData fileData = new FileData();
                fileData = person.getPassportImage();
                person.setPassportImage(null);
                FileData save = fileDataService.uploadFile(file, fileData);
                person.setPassportImage(save);
                return fileDataMapper.toDto(save);
            } else {
                FileData fileData = fileDataService.uploadFile(file);
                person.setPassportImage(fileData);
                personRepository.save(person);
                return fileDataMapper.toDto(fileData);
            }
        }
        return null;
    }

    @Override
    public void update(String token, PersonRequest personRequest) {
        Person person = userService.getUsernameFromToken(token).getPerson();
        person.setPassport_code(personRequest.getPassport_code()!=null? personRequest.getPassport_code() : person.getPassport_code());
        person.setPassportImage(personRequest.getPassportId()!=null? fileDataRepository.findById(personRequest.getPassportId()).get():null);
        personRepository.save(person);
        //person.setP(personRequest.getFirstname()!=null? personRequest.getFirstname() : person.getFirstname());

    }
}
