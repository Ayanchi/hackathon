package com.example.hackathon.dto.petition;

import com.example.hackathon.dto.file.FileDataResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PetitionResponse {
    private Long id;
    private FileDataResponse fileDataResponse;
    private String name;
    private String author;
    private String description;
    private String creationDate;
    private Integer countSigned;
    private Integer goal;
}
