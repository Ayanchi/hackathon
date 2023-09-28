package com.example.hackathon.dto.person;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonRequest {
    private String passport_code;
    private Long passportId;
}
