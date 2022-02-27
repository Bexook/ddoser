package com.example.ddoser.util;

import lombok.SneakyThrows;

import java.io.*;

public class Util {


    public static String printFileContent(InputStream is) throws IOException {
        try (InputStreamReader isr = new InputStreamReader(is);
             BufferedReader br = new BufferedReader(isr);) {
            String line;
            while ((line = br.readLine()) != null) {
                return line;
            }
        } finally {
            is.close();
        }
        return null;
    }


}
