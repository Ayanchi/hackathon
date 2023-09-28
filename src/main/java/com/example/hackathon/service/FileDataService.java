package com.example.hackathon.service;

import com.example.hackathon.entities.FileData;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

public interface FileDataService {
    FileData uploadFile(MultipartFile file, FileData pdf) ;

    FileData uploadFile(MultipartFile file) ;

    void downloadFile(Long id, HttpServletResponse http) ;

    void getFileById(Long id, HttpServletResponse httpServletResponse);

}
