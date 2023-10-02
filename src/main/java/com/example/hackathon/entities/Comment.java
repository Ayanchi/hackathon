package com.example.hackathon.entities;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    private Publication publication;

    private String comment;
    private String commentedTime;
    private String firstnameOfSender;
    private String lastnameOfSender;
    private String emailOfSender;
    private Boolean isLiked = false;
    private Integer likeCount = 0;
    @ManyToMany(mappedBy = "myLikedComments")
    private List<Person> likedPersons;
}
