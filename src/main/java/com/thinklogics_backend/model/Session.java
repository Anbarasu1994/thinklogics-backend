package com.thinklogics_backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;


import java.util.Date;

@Data
@Entity
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sessionTitle;
    private String sessionDescription;
    private Date date;
    private String time;
    private int duration; // Duration in minutes
    private String host;
    private String platform;
  private String meetLink;



}
