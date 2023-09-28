package com.example.hackathon.service.Impl;

import com.example.hackathon.dto.comment.CommentResponse;
import com.example.hackathon.entities.Comment;
import com.example.hackathon.entities.Person;
import com.example.hackathon.entities.User;
import com.example.hackathon.mapper.CommentMapper;
import com.example.hackathon.repository.CommentRepository;
import com.example.hackathon.repository.PersonRepository;
import com.example.hackathon.repository.UserRepository;
import com.example.hackathon.service.CommentService;
import com.example.hackathon.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final PersonRepository personRepository;
    private final CommentMapper commentMapper;
    @Override
    public void likeTheComment(String token, Long commentId) {
        Person person = userService.getUsernameFromToken(token).getPerson();
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        if (person.getMyLikedComments().contains(comment)){
            comment.setLikeCount(comment.getLikeCount()-1);
            person.getMyLikedComments().remove(comment);
            personRepository.save(person);
            commentRepository.save(comment);
        }
        else {
            comment.setLikeCount(comment.getLikeCount()==null?1: comment.getLikeCount()+1);
            person.getMyLikedComments().add(comment);
            personRepository.save(person);
            commentRepository.save(comment);
        }
    }

    @Override
    public List<CommentResponse> getbyPetitionId( Long petitionId) {
        return commentMapper.toDtos(commentRepository.findAllByPublicationId(petitionId));
    }
}
