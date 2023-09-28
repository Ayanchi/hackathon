package com.example.hackathon.repository;

import com.example.hackathon.entities.Petition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

@Repository
public interface PetitionRepository extends JpaRepository<Petition, Long> {
}
