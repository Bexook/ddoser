package com.example.ddoser.mail.impl;

import com.example.ddoser.dto.Data;
import com.example.ddoser.mail.MailSender;
import com.example.ddoser.util.Util;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileInputStream;
import java.io.IOException;

@Service
public class MailSenderImpl implements MailSender {

    @Autowired
    private JavaMailSender javaMailSender;
    private final static String htmlPart = "<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "<head>\n" +
            "    <meta charset=\"UTF-8\">\n" +
            "    <title>ВОЙНА!!!</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "<p>Россия , сейчас ваши дети погибают пачками на Украине из-за сумасшедшего дела.</p>\n" +
            "<p> Страдают и обычные мирные жители, уже умерло несколько детей от ваших же солдат.</p>\n" +
            "<p>Не стойте в стороне, хватит быть  терпилами! Выходите на митинге, если не сейчас то когда !?</p>\n" +
            "<p>Вы так и всю жизнь будете страдать из-за Путлера.</p>\n" +
            "<img src=\"/templates/cid:id1\" alt=\"\">\n" +
            "<img src=\"/templates/cid:id2\" alt=\"\">\n" +
            "<img src=\"/templates/cid:id3\" alt=\"\">\n" +
            "</body>\n" +
            "</html>";


    private final String ph1 = Util.printFileContent(new FileInputStream(this.getClass().getClassLoader().getResource("templates/ph_1.txt").getFile()));
    private final String ph2 = Util.printFileContent(new FileInputStream(this.getClass().getClassLoader().getResource("templates/ph_2.txt").getFile()));
    private final String ph3 = Util.printFileContent(new FileInputStream(this.getClass().getClassLoader().getResource("templates/ph_3.txt").getFile()));

    public MailSenderImpl() throws IOException {
    }

    @Override
    @SneakyThrows
    public void buildMailDataAndSend(Data data) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        mimeMessage.setFrom("info");
        mimeMessage.setText(data.getText());
        mimeMessage.addFrom(new Address[]{
                new InternetAddress("FBI-info"),
        });
        data.getEmails().forEach(email -> addRecipientAndSend(email, mimeMessage));
    }


    @SneakyThrows
    private void addRecipientAndSend(final String email, final MimeMessage message) {
        message.addRecipients(Message.RecipientType.TO, email);
        javaMailSender.send(message);
    }

}
