package com.example.hackathon.entities;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class Petition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 3000)
    private String name;
    @OneToOne(cascade = CascadeType.ALL)
    private FileData imageOfPetition;
    private String author;
    @Column(length = 3000)
    private String description;
    private String creationDate;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<Person> signedPersons;
    private Integer countOfSignIn;
    private Integer goal;
    private Boolean fromGPT = false;


}
