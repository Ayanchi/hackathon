package com.example.hackathon.controller;

import com.example.hackathon.dto.petition.PetitionRequest;
import com.example.hackathon.dto.petition.PetitionResponse;
import com.example.hackathon.service.PetitionService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/petition")
@CrossOrigin(origins = "*", maxAge = 3600)
@AllArgsConstructor
public class PetitionController {
    private final PetitionService petitionService;

    @GetMapping("/allPetitions")
    public List<PetitionResponse> getAllPetitions(){
        return petitionService.getAllPetitions();
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("/add")
    public void addPetition(@RequestBody PetitionRequest petitionResponse, @RequestHeader("Authorization") String token){
        petitionService.save(petitionResponse, token);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("/update")
    public void updatePetition(@RequestBody PetitionRequest petitionResponse, @RequestHeader("Authorization") String token) {
        petitionService.update(petitionResponse, token);
    }


    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping("/delete/{petitionId}")
    public void deletePetition(@RequestHeader("Authorization") String token, @PathVariable Long petitionId){
        petitionService.delete(token, petitionId);
    }

    @PostMapping("/signToPetition/{petitionId}")
    public void signToPetition(@RequestHeader("Authorization") String token, @PathVariable Long petitionId){
        petitionService.signToPetition(token, petitionId);
    }
    @GetMapping("/getById/{petitionId}")
    public PetitionResponse getById(@PathVariable Long petitionId){
        return petitionService.getById(petitionId);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/createPetitionWithChat/{publicationId}")
    public String createPetitionOnChatGpt(@PathVariable Long publicationId){
        return petitionService.createPetitionAI(publicationId);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PutMapping("/update/{petitionId}")
    public PetitionResponse updatePetition(@RequestBody PetitionRequest petitionRequest,@PathVariable Long petitionId){
        return petitionService.update(petitionRequest, petitionId);
    }


//eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBlbWFpbCIsImF1dGgiOnsiYXV0aG9yaXR5IjoiQURNSU4ifSwiaWF0IjoxNjk1NDk1ODc0LCJleHAiOjE2OTU3OTU4NzR9._saRIXO8ro-BpXJuywJ2O2KnHBQae72Zv48gVq6NKHo



}
