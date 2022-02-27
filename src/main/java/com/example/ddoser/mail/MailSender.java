package com.example.ddoser.mail;

import com.example.ddoser.MailData;

import java.util.List;

public interface MailSender {

    void send(List<MailData> mailData);

    List<MailData> buildMailData(List<String> emails);

}
