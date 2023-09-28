package com.example.hackathon.mapper;

import com.example.hackathon.dto.file.FileDataResponse;
import com.example.hackathon.entities.FileData;

public interface FileDataMapper {
    FileDataResponse toDto(FileData petitionImage);

    FileData toEntity(FileDataResponse fileDataResponse);
}
