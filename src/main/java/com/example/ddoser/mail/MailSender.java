package com.example.ddoser.mail;

import com.example.ddoser.dto.Data;

public interface MailSender {

    void buildMailDataAndSend(final Data data);

}
