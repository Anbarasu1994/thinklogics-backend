package com.thinklogics_backend.model;


import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.Date;

@Data
@Document
public class Session {
    @Id
    private String id;

    private String sessionTitle;
    private String sessionDescription;
    private Date date;
    private String time;
    private int duration; // Duration in minutes
    private String host;
    private String platform;
  private String meetLink;



}
