package com.example.hackathon.mapper.impl;

import com.example.hackathon.dto.publication.PublicationResponse;
import com.example.hackathon.entities.Publication;
import com.example.hackathon.mapper.CommentMapper;
import com.example.hackathon.mapper.FileDataMapper;
import com.example.hackathon.mapper.PersonMapper;
import com.example.hackathon.mapper.PublicationMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class PublicationMapperImpl implements PublicationMapper {
    private final PersonMapper personMapper;
    private final FileDataMapper fileDataMapper;
    private final CommentMapper commentMapper;
    @Override
    public List<PublicationResponse> toDtos(List<Publication> all) {
        List<PublicationResponse> publicationRespons = new ArrayList<>();
        for(Publication publication : all){
            publicationRespons.add(toDto(publication));
        }
        return publicationRespons;
    }

    @Override
    public PublicationResponse toDto(Publication publication) {
        PublicationResponse publicationResponse = new PublicationResponse();
        publicationResponse.setPetitionId(publication.getId());
        publicationResponse.setName(publication.getName());
        if(publication.getComments()!=null)
            publicationResponse.setComments(commentMapper.toDtos(publication.getComments()));
        publicationResponse.setPersonResponse(publication.getPerson()!=null? personMapper.toDto(publication.getPerson()): null);
        publicationResponse.setDescription(publication.getDescription());
        publicationResponse.setFileDataResponse(publication.getPetitionImage()==null?null: fileDataMapper.toDto(publication.getPetitionImage()));
        publicationResponse.setCountSign(publication.getCountLikes());
        publicationResponse.setDate(String.valueOf(publication.getCreatedTime()!=null? publication.getCreatedTime() : LocalDateTime.now()));
        publicationResponse.setGoal(publication.getMaxSignCount()!=null? publication.getMaxSignCount():5000L);
        return publicationResponse;
    }
}
