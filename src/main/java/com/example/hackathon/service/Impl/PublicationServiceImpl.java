package com.example.hackathon.service.Impl;

import com.example.hackathon.dto.publication.PublicationRequest;
import com.example.hackathon.dto.publication.PublicationResponse;
import com.example.hackathon.entities.*;
import com.example.hackathon.enums.Role;
import com.example.hackathon.mapper.FileDataMapper;
import com.example.hackathon.mapper.PublicationMapper;
import com.example.hackathon.repository.CommentRepository;
import com.example.hackathon.repository.FileDataRepository;
import com.example.hackathon.repository.PublicationRepository;
import com.example.hackathon.service.FileDataService;
import com.example.hackathon.service.PetitionService;
import com.example.hackathon.service.PublicationService;
import com.example.hackathon.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PublicationServiceImpl implements PublicationService {

    private final PublicationMapper publicationMapper;
    private final PublicationRepository publicationRepository;
    private final UserService userService;
    private final FileDataRepository fileDataRepository;
    private final CommentRepository commentRepository;
    private final FileDataService fileDataService;
    private final FileDataMapper fileDataMapper;
    private final PetitionService petitionService;
    @Override
    public List<PublicationResponse> getAll() {
        return publicationMapper.toDtos(publicationRepository.findAll());
    }

    @Override
    public PublicationResponse save(String token, PublicationRequest publicationRequest) {
        Publication publication = new Publication();
        publication.setName(publicationRequest.getName());
        publication.setDescription(publicationRequest.getDescription());
        publication.setCountLikes(0L);
        publication.setPetitionImage(publicationRequest.getFileDataId()==null?null:
                fileDataRepository.findById(publicationRequest.getFileDataId()).get());
        publication.setCreatedTime(LocalDateTime.now());
        User user = userService.getUsernameFromToken(token);
        publication.setPerson(user.getPerson());
        publicationRepository.save(publication);
        return publicationMapper.toDto(publication);
    }

    @Override
    public PublicationResponse getPublicationById(Long petitionId) {
        Optional<Publication> petition = publicationRepository.findById(petitionId);
        if (petition.isEmpty())
            throw new NotFoundException("the petition with this id not found!");
        return publicationMapper.toDto(petition.get());
    }

    @Override
    public void commentToPetition(String token, Long petitionId, String comment) {
        User user = userService.getUsernameFromToken(token);
        Publication publication = publicationRepository.findById(petitionId).orElseThrow();
        List<Comment> comments = publication.getComments();
        Comment comment1 = new Comment();
        comment1.setCommentedTime(LocalDateTime.now().toString());
        comment1.setLastnameOfSender(user.getLastname());
        comment1.setFirstnameOfSender(user.getFirstname());
        comment1.setEmailOfSender(user.getEmail());
        comment1.setComment(comment);
        comment1.setPublication(publication);
        comments.add(comment1);


        publication.setComments(comments);

        publicationRepository.save(publication);

    }

    @Override
    public void delete(String token, Long publicationId) {
        if (userService.getUsernameFromToken(token).getRole().equals(Role.ADMIN))
            publicationRepository.deleteById(publicationId);
    }

    @Override
    public void likeToPublication(String token, Long publicationId) {
        Person person = userService.getUsernameFromToken(token).getPerson();
        Optional<Publication> publication = publicationRepository.findById(publicationId);
        if (!publication.get().getLikedPersons().contains(person)){
            publication.get().getLikedPersons().add(person);
            publication.get().setCountLikes(Long.valueOf(publication.get().getLikedPersons().size()));
            publicationRepository.save(publication.get());
        }
        else {
            publication.get().getLikedPersons().remove(person);
            publication.get().setCountLikes(Long.valueOf(publication.get().getLikedPersons().size()));
            publicationRepository.save(publication.get());

        }
        checkForGpt(publication.get());

    }

    private void checkForGpt(Publication publication) {
        if (publication.getCountLikes()>= publication.getMaxSignCount()){
            petitionService.createPetitionAI(publication.getId());
        }
    }

    @Override
    public Object uploadImagePublication(MultipartFile file, Long id) {


        Publication publication = new Publication();
        FileData fileData = new FileData();

        if (id!=null){
            publication = publicationRepository.findById(id).orElseThrow();
            publication.setPetitionImage(fileData);
        }

            if (publication.getPetitionImage() != null) {
                fileData = publication.getPetitionImage();
                publication.setPetitionImage(null);
                FileData save = fileDataService.uploadFile(file, fileData);
                publication.setPetitionImage(save);
                Object o = id!=null? publicationRepository.save(publication): "";
                return fileDataMapper.toDto(save);
            } else {
                fileData = fileDataService.uploadFile(file);
                Object o = id!=null? publicationRepository.save(publication): "";
                return fileDataMapper.toDto(fileData);
            }



    }

//     public Object uploadImagePublication(MultipartFile file, String token, Long publicationId) {
//
//        User user = userService.getUsernameFromToken(token);
//        if (user.getRole() == Role.USER) {
//            Publication publication = publicationRepository.findById(publicationId).orElseThrow();
//
//            if (publication.getPetitionImage() != null) {
//                FileData fileData = new FileData();
//                fileData = publication.getPetitionImage();
//                publication.setPetitionImage(null);
//                FileData save = fileDataService.uploadFile(file, fileData);
//                publication.setPetitionImage(save);
//                return fileDataMapper.toDto(save);
//            } else {
//                FileData fileData = fileDataService.uploadFile(file);
//                publication.setPetitionImage(fileData);
//                publicationRepository.save(publication);
//                return fileDataMapper.toDto(fileData);
//            }
//        }
//        return null;
//    }
//
//    public Object uploadImagePitition(MultipartFile file, String token, Long petitionId) {
//        User user = userService.getUsernameFromToken(token);
//        if (user.getRole() == Role.ADMIN) {
//            Petition petition = petitionRepository.findById(petitionId).get();
//
//            if (petition.getImageOfPetition() != null) {
//                FileData fileData = new FileData();
//                fileData = petition.getImageOfPetition();
//                petition.setImageOfPetition(null);
//                FileData save = fileDataService.uploadFile(file, fileData);
//                petition.setImageOfPetition(save);
//                return fileDataMapper.toDto(save);
//            } else {
//                FileData fileData = fileDataService.uploadFile(file);
//                petition.setImageOfPetition(fileData);
//                petitionRepository.save(petition);
//                return fileDataMapper.toDto(fileData);
//            }
//        }
//        return null;
//    }
//    public Object uploadImagePitition(MultipartFile file, String token) {
//        User user = userService.getUsernameFromToken(token);
//        if (user.getRole() != Role.ADMIN) {
//            Person person = userService.getUsernameFromToken(token).getPerson();
//
//            if (person.getPassportImage() != null) {
//                FileData fileData = new FileData();
//                fileData = person.getPassportImage();
//                person.setPassportImage(null);
//                FileData save = fileDataService.uploadFile(file, fileData);
//                person.setPassportImage(save);
//                return fileDataMapper.toDto(save);
//            } else {
//                FileData fileData = fileDataService.uploadFile(file);
//                person.setPassportImage(fileData);
//                personRepository.save(person);
//                return fileDataMapper.toDto(fileData);
//            }
//        }
//        return null;
//    }
}
