package com.thinklogics_backend.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "visitors") // Specify the collection name in MongoDB
public class Visitors {

    @Id
    private String id;
    private String name;
    private String email;
    private String phoneNumber;
    private String description;
}
