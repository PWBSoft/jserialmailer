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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

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
        Platform.runLater(() -> {
            var text = new Text(line + NEW_LINE);
            text.setFill(Color.RED);
            text.setFont(Font.font("Consolas"));
            this.statusText.getChildren().add(text);
        });
    }


    private void sendEmails() {
        var smtpConf = App.getMessage().getSmtp();
        var smtpService = new SMTPService(smtpConf.getServer(), smtpConf.getPort(), smtpConf.getUsername(), smtpConf.getEmail(), smtpConf.getPassword(), smtpConf.isSsl());

        List<Recipient> recipients = App.getMessage().getMessageDTO().getRecipients();

        var status = new Status(recipients.size());

        var threads = new ArrayList<Thread>();
        for (var recipient : recipients) {
            threads.add(new Thread(() -> {
                updateStatus("Sending message to " + recipient.getEmail());
                var html = TemplateService.parse(App.getMessage().getMessageDTO().getTemplate(), recipient.getData());
                try {
                    smtpService.sendMail(App.getMessage().getMessageDTO().getSubject(), smtpConf.getEmail(), recipient.getEmail(), html);

                    var c = status.getCount() + 1;
                    var t = status.getTotal();
                    var p = status.getPercentage();

                    var statusStr = String.format("Processed %d/%d (%.1f%%)", c, t, p);

                    updateStatus(statusStr);

                    App.getHomeController().setStatus(statusStr, p);

                    status.increment();
                } catch (Exception e) {
                    updateStatus(String.format("Error while sending mail to %s: ", recipient.getEmail()));
                    updateStatus(e.getMessage());
                    e.printStackTrace();
                }
            }));
        }

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        threads.forEach(executorService::submit);


        new Thread(() -> {
            while (threads.stream().map(Thread::isAlive).reduce((i1, i2) -> i1 && i2).orElse(false)) {
                System.out.println("Still working");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ignored) {
                }
            }

            restartButton.setDisable(false);
        }).start();
    }

    private static class Status {
        AtomicInteger total = new AtomicInteger(0);
        AtomicInteger count = new AtomicInteger(0);

        public Status(int total) {
            this.total.set(total);
        }

        @Synchronized
        public void setTotal(int total) {
            this.total.set(total);
        }

        @Synchronized
        public int getCount() {
            return this.count.get();
        }

        @Synchronized
        public int getTotal() {
            return this.total.get();
        }

        @Synchronized
        public void increment() {
            this.count.incrementAndGet();
        }

        @Synchronized
        public double getPercentage() {
            double c = this.count.get() + 1;
            double t = this.total.get();
            return c / t;
        }
    }
}
