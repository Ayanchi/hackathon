package com.example.hackathon.service.Impl;

import com.example.hackathon.dto.publication.PublicationRequest;
import com.example.hackathon.dto.publication.PublicationResponse;
import com.example.hackathon.entities.Comment;
import com.example.hackathon.entities.Person;
import com.example.hackathon.entities.Publication;
import com.example.hackathon.entities.User;
import com.example.hackathon.enums.Role;
import com.example.hackathon.mapper.PublicationMapper;
import com.example.hackathon.repository.CommentRepository;
import com.example.hackathon.repository.FileDataRepository;
import com.example.hackathon.repository.PublicationRepository;
import com.example.hackathon.service.PublicationService;
import com.example.hackathon.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
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
    @Override
    public List<PublicationResponse> getAll(String token) {
        userService.getUsernameFromToken(token);
        return publicationMapper.toDtos(publicationRepository.findAll());
    }

    @Override
    public void save(String token, PublicationRequest publicationRequest) {
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
    }

    @Override
    public PublicationResponse getPublicationById(String token, Long petitionId) {
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

    }
}
