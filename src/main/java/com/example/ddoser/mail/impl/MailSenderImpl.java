package com.example.ddoser.mail.impl;

import com.example.ddoser.MailData;
import com.example.ddoser.domain.EmailsSend;
import com.example.ddoser.mail.MailSender;
import com.example.ddoser.repo.EmailSendRepo;
import com.example.ddoser.util.Util;
import com.mailjet.client.ClientOptions;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import com.mailjet.client.errors.MailjetException;
import com.mailjet.client.errors.MailjetSocketTimeoutException;
import com.mailjet.client.resource.Emailv31;
import lombok.SneakyThrows;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MailSenderImpl implements MailSender {

    @Autowired
    private EmailSendRepo emailSendRepo;

    private Util util = new Util();
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
    public void send(List<MailData> mailData) {
        mailData.stream().forEach(this::sendMail);
    }

    @Override
    public List<MailData> buildMailData(List<String> emails) {
        return emails.stream().map(this::getMailData).collect(Collectors.toList());
    }


    private MailData getMailData(final String email) {
        try {
            emailSendRepo.save(new EmailsSend().setEmail(email));
        } catch (Exception e) {
            System.out.println("Email may already exists in db");
        }
        return MailData.builder().url(email).build();
    }


    @SneakyThrows
    private void sendMail(final MailData mailData) {
        MailjetClient client;
        MailjetRequest request;
        MailjetResponse response;
        client = new MailjetClient("23d7a3923de8928a36833c6a49933267", "e9faef86efe19b7f125b09cbbb4623b8", new ClientOptions("v3.1"));
        request = new MailjetRequest(Emailv31.resource)
                .property(Emailv31.MESSAGES, new JSONArray()
                        .put(new JSONObject()
                                .put(Emailv31.Message.FROM, new JSONObject()
                                        .put("Email", "bezook12@gmail.com")
                                        .put("Name", "Bandera"))
                                .put(Emailv31.Message.TO, new JSONArray()
                                        .put(new JSONObject()
                                                .put("Email", mailData.getUrl())
                                                .put("Name", "Bandera")))
                                .put(Emailv31.Message.SUBJECT, "ВОЙНА!!!")
                                .put(Emailv31.Message.TEXTPART, "ВОЙНА!")
                                .put(Emailv31.Message.HTMLPART, htmlPart)
                                .put(Emailv31.Message.INLINEDATTACHMENTS, new JSONArray()
                                        .put(new JSONObject()
                                                .put("ContentType", "image/png")
                                                .put("Filename", "photo_1.png")
                                                .put("ContentID", "id1")
                                                .put("Base64Content", ph1)
                                        )
                                        .put(new JSONObject()
                                                .put("ContentType", "image/png")
                                                .put("Filename", "photo_2.png")
                                                .put("ContentID", "id2")
                                                .put("Base64Content", ph2)
                                        )

                                        .put(new JSONObject()
                                                .put("ContentType", "image/png")
                                                .put("Filename", "photo_3.png")
                                                .put("ContentID", "id3")
                                                .put("Base64Content", ph3)
                                        )
                                )
                                .put(Emailv31.Message.PRIORITY, 1)));
        response = client.post(request);
        System.out.println("Email send to ->" + mailData.getUrl() + "\n Response-> " + response.getStatus());
    }
}
