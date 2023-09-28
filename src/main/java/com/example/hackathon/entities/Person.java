package com.example.hackathon.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;
    private String lastname;


    @OneToOne(mappedBy = "person")
    private User user;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Publication> publications;

    @OneToOne(cascade = CascadeType.ALL)
    private FileData personAvatar;

    @OneToOne()
    private FileData passportImage;

    private String passport_code;
    @ManyToMany(cascade = CascadeType.ALL)
    private List<Comment> myLikedComments;

    @ManyToMany(mappedBy = "likedPersons")
    private List<Publication> likedPublications;

    @ManyToMany(mappedBy = "")
    private List<Petition> signedPetitions;

}
