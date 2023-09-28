package com.example.hackathon.mapper.impl;

import com.example.hackathon.dto.file.FileDataResponse;
import com.example.hackathon.entities.FileData;
import com.example.hackathon.mapper.FileDataMapper;
import org.springframework.stereotype.Component;

@Component
public class FileDataMapperImpl implements FileDataMapper {
    @Override
    public FileDataResponse toDto(FileData petitionImage) {
        FileDataResponse fileDataResponse = new FileDataResponse();
        fileDataResponse.setFileDataId(petitionImage.getId());
        fileDataResponse.setFileData(petitionImage.getFileData());
        fileDataResponse.setType(petitionImage.getType());
        fileDataResponse.setPath(petitionImage.getPath());
        fileDataResponse.setName(petitionImage.getName());
        fileDataResponse.setEntityId(petitionImage.getPublicationImage()==null?null:petitionImage.getPublicationImage().getId());
        return fileDataResponse;
    }

    @Override
    public FileData toEntity(FileDataResponse fileDataResponse) {
        FileData fileData = new FileData();
        fileData.setFileData(fileDataResponse.getFileData());
        fileData.setId(fileDataResponse.getFileDataId());
        fileData.setName(fileDataResponse.getName());
        fileData.setPath(fileDataResponse.getPath());
        fileData.setType(fileDataResponse.getType());
        return fileData;
    }


}
