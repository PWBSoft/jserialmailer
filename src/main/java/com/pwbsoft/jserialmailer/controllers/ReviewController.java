package com.pwbsoft.jserialmailer.controllers;

import com.pwbsoft.jserialmailer.App;
import com.pwbsoft.jserialmailer.Views;
import com.pwbsoft.jserialmailer.service.TemplateService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.web.WebView;
import lombok.SneakyThrows;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class ReviewController extends BaseController {

    @FXML
    TabPane tabPane;
    @FXML
    Button nextButton;

    @Override
    @FXML
    void initialize() {
        tabPane.getTabs().clear();
        nextButton.setOnMouseClicked(e -> App.getHomeController().setSubScene(Views.PROCESS));
        App.getHomeController().setStatus("Loading Review", 0);
        tabPane.getTabs().addAll(getTabs());
    }

    @SneakyThrows
    private List<Tab> getTabs() {
        int total = App.getMessage().getMessageDTO().getRecipients().size();
        AtomicInteger current = new AtomicInteger(0);
        return App.getMessage().getMessageDTO().getRecipients().stream().map(r -> {
            var tab = new Tab(r.getEmail());
            var webView = new WebView();
            Platform.runLater(() -> webView.getEngine().loadContent(TemplateService.parse(App.getMessage().getMessageDTO().getTemplate(), r.getData())));
            tab.setContent(webView);
            App.getHomeController().setStatus(String.format("Loading Review %d/%d", current.incrementAndGet(), total), (double) (current.get()) / (double) total);
            return tab;
        }).collect(Collectors.toList());
    }

}
