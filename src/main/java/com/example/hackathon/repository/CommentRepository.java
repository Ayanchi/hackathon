package com.example.hackathon.repository;

import com.example.hackathon.entities.Comment;
import com.example.hackathon.entities.Publication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPublicationId(Long publicationId);
    List<Comment> findTop3ByPublicationOrderByLikeCountDesc(Publication publication);
    List<Comment> findAllByPublicationIdOrderByLikeCountDesc(Long publicationId);

}
