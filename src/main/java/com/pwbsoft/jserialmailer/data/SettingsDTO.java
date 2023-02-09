package com.pwbsoft.jserialmailer.data;

import lombok.Data;

@Data
public class SettingsDTO {
    private String csvFileName;
    private String templateFileName;
    private MessageDTO messageDTO = new MessageDTO();
}
