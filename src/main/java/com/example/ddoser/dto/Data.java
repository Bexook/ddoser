package com.example.ddoser.dto;

import lombok.experimental.Accessors;

import java.util.List;

@lombok.Data
@Accessors(chain = true)
public class Data {

    private List<String> emails;
    private String text;
    private final String apiKey;
    private final String apiToken;
}
