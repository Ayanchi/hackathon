package com.example.hackathon.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Publication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private FileData petitionImage;

    private LocalDateTime createdTime;
    @Column(length = 3000)
    private String name;
    @Column(length = 3000)
    private String description;

    @ManyToOne()
    private Person person;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Comment> comments;

    private Long countLikes;
    private Long maxSignCount = 50000L;
    @ManyToMany(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private List<Person> likedPersons;

}
