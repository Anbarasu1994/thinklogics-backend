package com.thinklogics_backend.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@AllArgsConstructor
@Data
@NoArgsConstructor
@Document
public class Enquiry {
    @Id
    private String id;
    private String name;
    private String email;
    private String phoneNumber;
    private String message;

}
