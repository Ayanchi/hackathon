package com.example.hackathon.controller;

import com.example.hackathon.dto.publication.PublicationRequest;
import com.example.hackathon.dto.publication.PublicationResponse;
import com.example.hackathon.service.OpenAIApiService;
import com.example.hackathon.service.PublicationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/publication")
@CrossOrigin(origins = "*", maxAge = 3600)
@AllArgsConstructor
public class PublicationController {

    private final PublicationService publicationService;

    @GetMapping("/allPublications")
    public List<PublicationResponse> allPublication(){
        return publicationService.getAll();
    }
    @PostMapping("/add")
    public PublicationResponse addPublication(@RequestBody(required = false) PublicationRequest publicationRequest, @RequestHeader( "Authorization") String token){
        return  publicationService.save(token, publicationRequest);
    }
    @GetMapping("/publication/byId/{publicationId}")
    public PublicationResponse getPublicationById(@PathVariable Long publicationId){
        return publicationService.getPublicationById(publicationId);
    }

    private OpenAIApiService openAIApiService;



    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping("/delete/{publicationId}")
    public void delete(@RequestHeader("Authorization") String token, @PathVariable Long publicationId){
        publicationService.delete(token, publicationId);
    }

    @PostMapping("/likeToPublication/{publicationId}")
    public void likeToPublication(@RequestHeader("Authorization") String token,@PathVariable Long publicationId ){
        publicationService.likeToPublication(token, publicationId);
    }




}
