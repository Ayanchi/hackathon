package com.example.hackathon.mapper;

import com.example.hackathon.dto.person.PersonResponse;
import com.example.hackathon.entities.Person;

public interface PersonMapper {
    PersonResponse toDto(Person person);
}
