package com.example.hackathon.controller;


import com.example.hackathon.service.FileDataService;
import com.example.hackathon.service.PersonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/file")
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
public class FileController {

    private final FileDataService fileDataService;
    private final PersonService personService;


    @GetMapping("/file/{id}")
    public void getFileById(@PathVariable Long id, HttpServletResponse httpServletResponse){
        fileDataService.getFileById(id, httpServletResponse);
    }

    @GetMapping("/download/file/{id}")
    public void downloadFile(@PathVariable Long id, HttpServletResponse http) throws IOException {
        fileDataService.downloadFile(id, http);
    }





    @PostMapping("/passport/upload")
    public ResponseEntity<?> uploadAvatar(@RequestPart MultipartFile file, @RequestHeader("Authorization") String token) throws IOException {

        return ResponseEntity.status(HttpStatus.OK)
                .body(personService.uploadResume(file, token));
    }

    @PostMapping("/image/publication/upload/{publicationId}")
    public ResponseEntity<?> uploadImagePublication(@RequestPart MultipartFile file, @PathVariable Long publicationId,
                                                 @RequestHeader("Authorization") String token) throws IOException {

        return ResponseEntity.status(HttpStatus.OK)
                .body(personService.uploadImagePublication(file, token, publicationId));
    }
    @PostMapping("/image/personPassport/upload")
    public ResponseEntity<?> uploadImagePersonPassport(@RequestPart MultipartFile file,
                                                    @RequestHeader("Authorization") String token) throws IOException {

        return ResponseEntity.status(HttpStatus.OK)
                .body(personService.uploadImagePitition(file, token));
    }
    @PostMapping("/image/petition/upload/{petitionId}")
    public ResponseEntity<?> uploadImagePetition(@RequestPart MultipartFile file, @PathVariable Long petitionId,
                                                 @RequestHeader("Authorization") String token) throws IOException {

        return ResponseEntity.status(HttpStatus.OK)
                .body(personService.uploadImagePitition(file, token, petitionId));
    }


}
