package com.example.hackathon.dto.file;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileDataResponse {
    private Long fileDataId;
    String name;
    String type;
    byte[] fileData;
    Long entityId;
    String path;
}
