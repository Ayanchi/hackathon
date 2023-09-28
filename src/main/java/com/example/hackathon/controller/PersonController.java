package com.example.hackathon.controller;


import com.example.hackathon.dto.person.PersonRequest;
import com.example.hackathon.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/person")
@CrossOrigin(origins = "*", maxAge = 3600)
@AllArgsConstructor
public class PersonController {

    private final PersonService personService;

    @PostMapping("/update/person")
    public void updatePerson(@RequestHeader("Authorization") String token, @RequestBody PersonRequest personRequest){
        personService.update(token, personRequest);
    }
}
