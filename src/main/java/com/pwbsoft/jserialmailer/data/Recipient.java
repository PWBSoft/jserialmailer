package com.pwbsoft.jserialmailer.data;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Recipient {
    private String email;
    private Map<String, String> data = new HashMap<>();
}
