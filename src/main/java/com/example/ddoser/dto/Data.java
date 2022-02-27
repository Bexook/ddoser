package com.example.ddoser.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@lombok.Data
@Accessors(chain = true)
@AllArgsConstructor
public class Data {

    private List<String> emails;
    private String text;
    private final String apiKey;
    private final String apiToken;
}
