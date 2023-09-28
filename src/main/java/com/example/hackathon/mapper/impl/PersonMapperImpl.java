package com.example.hackathon.mapper.impl;

import com.example.hackathon.dto.person.PersonResponse;
import com.example.hackathon.entities.Person;
import com.example.hackathon.mapper.PersonMapper;
import org.springframework.stereotype.Component;

@Component
public class PersonMapperImpl implements PersonMapper {
    @Override
    public PersonResponse toDto(Person person) {
        PersonResponse personResponse = new PersonResponse();
        personResponse.setPersonId(person.getId());
        personResponse.setLastname(person.getLastname());
        personResponse.setFirstname(person.getFirstname());
        return personResponse;
    }
}
