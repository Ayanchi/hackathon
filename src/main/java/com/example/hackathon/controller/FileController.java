package com.example.hackathon.controller;


import com.example.hackathon.repository.PetitionRepository;
import com.example.hackathon.service.FileDataService;
import com.example.hackathon.service.PersonService;
import com.example.hackathon.service.PetitionService;
import com.example.hackathon.service.PublicationService;
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
    private final PublicationService publicationService;
    private final PetitionService petitionService;


    @GetMapping("/file/{id}")
    public void getFileById(@PathVariable Long id, HttpServletResponse httpServletResponse){
        fileDataService.getFileById(id, httpServletResponse);
    }

    @GetMapping("/download/file/{id}")
    public void downloadFile(@PathVariable Long id, HttpServletResponse http) throws IOException {
        fileDataService.downloadFile(id, http);
    }

    @PostMapping("/upload/avatar")
    public ResponseEntity<?> uploadAvatar(@RequestPart MultipartFile file, @RequestHeader("Authorization") String token) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(personService.uploadAvatar(file, token));
    }

    @PostMapping("/upload/image/publication")
    public ResponseEntity<?> uploadImagePublication(@RequestPart MultipartFile file, @RequestParam(required = false) Long id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(publicationService.uploadImagePublication(file, id));
    }
    @PostMapping("/upload/iamge/petition")
    public ResponseEntity<?> uploadImagePetition(@RequestPart MultipartFile file, @RequestParam(required = false) Long id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(petitionService.uploadImagePetition(file, id));
    }





//    @PostMapping("/passport/upload")
//    public ResponseEntity<?> uploadAvatar(@RequestPart MultipartFile file, @RequestHeader("Authorization") String token) throws IOException {
//
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(personService.uploadResume(file, token));
//    }
//
//    @PostMapping("/image/publication/upload")
//    public ResponseEntity<?> uploadImagePublication(@RequestPart MultipartFile file,
//                                                 @RequestHeader("Authorization") String token) throws IOException {
//
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(personService.uploadImagePublication(file, token));
//    }
//    @PostMapping("/image/personPassport/upload")
//    public ResponseEntity<?> uploadImagePersonPassport(@RequestPart MultipartFile file,
//                                                    @RequestHeader("Authorization") String token) throws IOException {
//
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(personService.uploadImagePersonPassport(file, token));
//    }
//    @PostMapping("/image/petition/upload")
//    public ResponseEntity<?> uploadImagePetition(@RequestPart MultipartFile file,
//                                                 @RequestHeader("Authorization") String token) throws IOException {
//
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(personService.uploadImagePitition(file, token));
//    }


}
