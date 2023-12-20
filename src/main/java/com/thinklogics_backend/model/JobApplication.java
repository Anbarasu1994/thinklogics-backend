package com.thinklogics_backend.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "job_applications")
public class JobApplication {
    @Id
    private String id;
    private String name;
    private String email;
    private String coverLetter;
//
//    @Lob
//    @Column(length = 16777215) // Adjust the length as needed
    private byte[] resume; // Store the file content as a byte array

    private String jobTitle;
}
