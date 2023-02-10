package com.pwbsoft.jserialmailer.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SmtpDTO {
    String server;
    Integer port = 25;
    String username;
    String email;
    String password;
    boolean ssl;
}
