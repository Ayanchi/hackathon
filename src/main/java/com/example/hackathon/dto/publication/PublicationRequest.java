package com.example.hackathon.dto.publication;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PublicationRequest {
    private String name;
    private Long fileDataId;
    private String description;

}
