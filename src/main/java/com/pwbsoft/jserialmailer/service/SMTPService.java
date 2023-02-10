package com.pwbsoft.jserialmailer.service;

import com.pwbsoft.jserialmailer.App;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

@RequiredArgsConstructor
public class SMTPService {
    final String server;
    final Integer port;
    final String username;
    final String email;
    final String password;
    final boolean ssl;

    Properties properties;
    Session session;

    private void configure() {
        properties = new Properties();
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.ssl.enable", ssl);
        properties.put("mail.smtp.host", server);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.ssl.trust", server);
    }

    private void createSession() {
        configure();
        session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                System.out.printf("Logging in with %s pw %s on %s:%d - SSL? %s",
                        username, password, server, port, ssl);
                return new PasswordAuthentication(username, password);
            }
        });
    }

    private Session getSession() {
        if (session == null) createSession();
        return session;
    }

    public void testConnection() throws SMTPException {
        var s = getSession();
        try {
            sendMail("JSerialMailer Test Email", App.getMessage().getSmtp().getEmail(), App.getMessage().getSmtp().getEmail(), "Test email sent " + new Date());
        } catch (Exception e) {
            throw new SMTPException(e.getMessage(), e.getCause());
        }
    }

    public void sendMail(String subject, String from, String to, String body) throws MessagingException, IOException {
        sendMail(subject, from, to, body, new File[]{});
    }

    public void sendMail(String subject, String from, String to, String body, File... attachments) throws MessagingException, IOException {
        try {
            Message message = new MimeMessage(getSession());
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);

            var b = new MimeBodyPart();
            b.setContent(body, "text/html; charset=utf-8");

            var m = new MimeMultipart();
            m.addBodyPart(b);

            for (var a : attachments) {
                var aBP = new MimeBodyPart();
                aBP.attachFile(a);
                m.addBodyPart(aBP);
            }

            message.setContent(m);

            Transport.send(message);
        } catch (Exception e) {
            System.out.println("Error while sending email: ");
            e.printStackTrace();
            throw e;
        }
    }

    public static class SMTPException extends Exception {
        public SMTPException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
