package com.example.ddoser;

import com.example.ddoser.mail.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MailSenderSource {

    @Autowired
    private MailSender mailSender;

    @PostMapping("/emails")
    public void getEmails(@RequestBody List<String> emails){
        mailSender.send(mailSender.buildMailData(emails));
    }


}
