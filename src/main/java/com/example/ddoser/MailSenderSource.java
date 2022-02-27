package com.example.ddoser;

import com.example.ddoser.dto.Data;
import com.example.ddoser.mail.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MailSenderSource {

    @Autowired
    private MailSender mailSender;

    @PostMapping("/emails")
    public void getEmails(@RequestBody Data data) {
        mailSender.buildMailDataAndSend(data);
    }


}
