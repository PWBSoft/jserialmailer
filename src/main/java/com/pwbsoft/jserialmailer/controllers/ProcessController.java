package com.pwbsoft.jserialmailer.controllers;

import com.pwbsoft.jserialmailer.App;
import com.pwbsoft.jserialmailer.Views;
import com.pwbsoft.jserialmailer.data.SmtpDTO;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class ProcessController extends BaseController {

    @FXML
    TextField smtpServer;
    @FXML
    TextField smtpPort;
    @FXML
    TextField username;
    @FXML
    TextField from;
    @FXML
    PasswordField password;
    @FXML
    TextField subject;
    @FXML
    ComboBox<String> smtpEnc;
    @FXML
    Button sendBtn;

    @Override
    @FXML
    void initialize() {
        sendBtn.setOnMouseClicked(this::prepareSend);
        var smtp = App.getMessage().getSmtp();
        smtpServer.setText(smtp.getServer());
        smtpPort.setText(smtp.getPort() != null ? smtp.getPort().toString() : null);
        smtpEnc.setValue(smtp.isSsl() ? "SSL" : "NONE");
        from.setText(smtp.getEmail());
        username.setText(smtp.getUsername());
        password.setText(smtp.getPassword());
        subject.setText(App.getMessage().getMessageDTO().getSubject());
    }

    private void prepareSend(Event event) {
        var smtp = new SmtpDTO();
        smtp.setSsl(smtpEnc.getValue().equals("SSL"));
        smtp.setServer(smtpServer.getText());
        smtp.setPort(Integer.parseInt(smtpPort.getText()));
        smtp.setUsername(username.getText());
        smtp.setEmail(from.getText());
        smtp.setPassword(password.getText());
        App.getMessage().setSmtp(smtp);
        App.getMessage().getMessageDTO().setSubject(subject.getText());
        App.getHomeController().setSubScene(Views.END);
    }
}
