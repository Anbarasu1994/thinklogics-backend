package com.thinklogics_backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private Environment environment;

    @Autowired
    private MailSender mailSender;

    public void sendSessionCreatedEmail(String sessionTitle, String date, String time, String location,String meetLink, String participant) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(environment.getProperty("spring.mail.username")); // Your email address
        message.setTo(participant);
        message.setSubject("New Session: " + sessionTitle);
        message.setText("Session: " + sessionTitle + "\n"
                + "Date: " + date + "\n"
                + "Time: " + time + "\n"
                + "Location: " + location + "\n"
                 + "Meeting Link: " + meetLink );

        mailSender.send(message);
    }
}
