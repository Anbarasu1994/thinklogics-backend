package com.thinklogics_backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class JobApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String email;
    private String coverLetter;

    @Lob
    @Column(length = 16777215) // Adjust the length as needed
    private byte[] resume; // Store the file content as a byte array

    private String jobTitle;
}
