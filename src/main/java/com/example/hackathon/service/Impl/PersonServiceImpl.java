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
    public FileDataResponse uploadAvatar(MultipartFile file, String token) {
        User user = userService.getUsernameFromToken(token);
        FileData fileData = new FileData();
        if (user.getRole() == Role.USER) {
            Person person = user.getPerson();
            if (person.getPersonAvatar() != null) {
                fileData = person.getPersonAvatar();
                person.setPersonAvatar(null);
                FileData save = fileDataService.uploadFile(file, fileData);
                person.setPersonAvatar(save);
                personRepository.save(person);
                return fileDataMapper.toDto(save);
            } else {
                fileData = fileDataService.uploadFile(file);
                person.setPersonAvatar(fileData);
                personRepository.save(person);
                return fileDataMapper.toDto(fileData);
            }
        }
        return fileDataMapper.toDto(fileData);

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
