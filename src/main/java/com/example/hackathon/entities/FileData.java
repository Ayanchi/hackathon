package com.example.hackathon.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class FileData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type;
    //    @Lob
    @Column(name = "image_data")
    private byte[] fileData;

    @OneToOne(mappedBy = "personAvatar")
    private Person personAvatar;
    @OneToOne()
    private Person passportImage;

    @OneToOne(mappedBy = "petitionImage")
    private Publication publicationImage;


    @Column(name = "path")
    private String path;
}
