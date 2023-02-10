package com.pwbsoft.jserialmailer.controllers;

import com.pwbsoft.jserialmailer.App;
import com.pwbsoft.jserialmailer.Views;
import com.pwbsoft.jserialmailer.service.TemplateService;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebView;
import lombok.SneakyThrows;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
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
        tabPane.getTabs().addAll(getTabs());
        nextButton.setOnMouseClicked(e -> App.getHomeController().setSubScene(Views.PROCESS));
    }

    @SneakyThrows
    private List<Tab> getTabs() {
        return App.getMessage().getMessageDTO().getRecipients().stream().map(r -> {
            var tab = new Tab(r.getEmail());
            var webView = new WebView();
            webView.getEngine().loadContent(TemplateService.parse(App.getMessage().getMessageDTO().getTemplate(), r.getData()));
            tab.setContent(webView);
            return tab;
        }).collect(Collectors.toList());
    }

}
