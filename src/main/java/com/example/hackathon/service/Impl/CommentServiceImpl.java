package com.example.hackathon.service.Impl;

import com.example.hackathon.dto.comment.CommentResponse;
import com.example.hackathon.entities.Comment;
import com.example.hackathon.entities.Person;
import com.example.hackathon.entities.Publication;
import com.example.hackathon.entities.User;
import com.example.hackathon.mapper.CommentMapper;
import com.example.hackathon.repository.CommentRepository;
import com.example.hackathon.repository.PersonRepository;
import com.example.hackathon.repository.PublicationRepository;
import com.example.hackathon.repository.UserRepository;
import com.example.hackathon.service.CommentService;
import com.example.hackathon.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserService userService;
    private final PersonRepository personRepository;
    private final CommentMapper commentMapper;
    private final PublicationRepository publicationRepository;
    @Override
    public Boolean likeTheComment(String token, Long commentId) {
        Person person = userService.getUsernameFromToken(token).getPerson();
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        if (person.getMyLikedComments().contains(comment)){
            comment.setIsLiked(false);
            comment.setLikeCount(comment.getLikeCount()-1);
            person.getMyLikedComments().remove(comment);
            personRepository.save(person);
            commentRepository.save(comment);
            return false;
        }
        else {
            comment.setLikeCount(comment.getLikeCount()==null?1: comment.getLikeCount()+1);
            comment.setIsLiked(true);
            person.getMyLikedComments().add(comment);
            personRepository.save(person);
            commentRepository.save(comment);
            return true;
        }
    }

    @Override
    public List<CommentResponse> getbyPublicationId(Long publicationId) {
        return commentMapper.toDtos(commentRepository.findAllByPublicationIdOrderByLikeCountDesc(publicationId));
    }

    @Transactional
    @Override
    public void delete(Long commentId) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        if (comment.isPresent()){
            System.out.println("here 0");
            Publication publication = comment.get().getPublication();
            publication.getComments().remove(comment.get());
            publicationRepository.save(publication);
            commentRepository.deleteById(commentId);
            System.out.println("here 1");
        }
    }
}
