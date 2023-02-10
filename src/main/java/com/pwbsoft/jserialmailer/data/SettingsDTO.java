package com.pwbsoft.jserialmailer.data;

import lombok.Data;

@Data
public class SettingsDTO {
    private String csvFileName;
    private String templateFileName;
    private SmtpDTO smtp = new SmtpDTO();
    private MessageDTO messageDTO = new MessageDTO();
}
