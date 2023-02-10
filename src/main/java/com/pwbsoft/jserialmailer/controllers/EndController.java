package com.pwbsoft.jserialmailer.controllers;

import com.pwbsoft.jserialmailer.App;
import com.pwbsoft.jserialmailer.data.Recipient;
import com.pwbsoft.jserialmailer.service.SMTPService;
import com.pwbsoft.jserialmailer.service.TemplateService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import lombok.Synchronized;

import java.util.List;

import static com.pwbsoft.jserialmailer.SystemInfo.NEW_LINE;

public class EndController extends BaseController {
    @FXML
    Button restartButton;
    @FXML
    TextFlow statusText;

    @Override
    @FXML
    void initialize() {
        restartButton.setOnMouseClicked(e -> App.getMainApp().startAnew());
        Platform.runLater(this::sendEmails);
    }

    @Synchronized
    private void updateStatus(String line) {
        var text = new Text(line + NEW_LINE);
        text.setFill(Color.RED);
        text.setFont(Font.font("Consolas"));
        this.statusText.getChildren().add(text);
    }

    private void sendEmails() {
        var smtpConf = App.getMessage().getSmtp();
        var smtpService = new SMTPService(smtpConf.getServer(),
                smtpConf.getPort(), smtpConf.getUsername(),
                smtpConf.getEmail(), smtpConf.getPassword(), smtpConf.isSsl());

        List<Recipient> recipients = App.getMessage().getMessageDTO().getRecipients();
        for (int i = 0; i < recipients.size(); i++) {
            var recipient = recipients.get(i);
            updateStatus("Sending message to " + recipient.getEmail());
            var html = TemplateService.parse(App.getMessage().getMessageDTO().getTemplate(), recipient.getData());
            try {
                smtpService.sendMail(
                        App.getMessage().getMessageDTO().getSubject(),
                        smtpConf.getEmail(),
                        recipient.getEmail(),
                        html
                );
            } catch (Exception e) {
                updateStatus("Error while sending mail: ");
                updateStatus(e.getMessage());
                e.printStackTrace();
            }

            App.getHomeController().setStatus(String.format("Processed %d/%d", i + 1, recipients.size()), (double) i + 1 / (double) recipients.size());
        }

        restartButton.setDisable(false);
    }
}
