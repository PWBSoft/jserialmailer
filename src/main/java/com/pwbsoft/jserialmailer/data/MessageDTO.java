package com.pwbsoft.jserialmailer.data;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MessageDTO {
    private String template;
    private List<Recipient> recipients = new ArrayList<>();
}
