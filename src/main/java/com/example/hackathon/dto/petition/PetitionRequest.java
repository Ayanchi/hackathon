package com.example.hackathon.dto.petition;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
public class PetitionRequest {
    private Long id;
    private Long imageId;
    private String name;
    private String author;
    private String description;
    private String creationDate;
    private Integer goal;
}
