package com.thinklogics_backend.model;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "session_attendees") // Specify the collection name in MongoDB
@Data
public class SessionAttendee {

    @Id
    private String id; // Using String as ID type for MongoDB

    private String name;
    private String email;
    private String institution;
    private String course;
    private String year;
    private String department;
    private String phoneNumber;
}
