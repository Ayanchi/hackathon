package com.example.hackathon.mapper;

import com.example.hackathon.dto.publication.PublicationResponse;
import com.example.hackathon.entities.Publication;

import java.util.List;

public interface PublicationMapper {
    List<PublicationResponse> toDtos(List<Publication> all);

    PublicationResponse toDto(Publication publication);
}
