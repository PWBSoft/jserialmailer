package com.pwbsoft.jserialmailer.controllers;

import com.pwbsoft.jserialmailer.App;
import com.pwbsoft.jserialmailer.Views;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Path;

public class HtmlImportController extends BaseController {
    @FXML
    TextField htmlFileLabel;
    @FXML
    Button htmlFileButton;
    @FXML
    Button nextButton;
    @FXML
    WebView webView;

    @FXML
    @Override
    void initialize() {
        htmlFileButton.setOnMouseClicked(this::selectFile);
        nextButton.setOnMouseClicked(e -> App.getHomeController().setSubScene(Views.REVIEW));
        reset();
    }

    private void reset() {
        var s = App.getMessage().getTemplateFileName();
        if (s != null) {
            htmlFileLabel.setText(s);
            Platform.runLater(() -> loadHtml(s));
        }
    }

    @SneakyThrows
    private void selectFile(Event event) {
        var chooser = new FileChooser();
        var filter = new FileChooser.ExtensionFilter("HTML Files", "*.html", "*.htm");
        chooser.getExtensionFilters().add(filter);
        var file = chooser.showOpenDialog(App.getRootStage());
        if (file != null) {

            htmlFileLabel.setText(file.getAbsolutePath());
            App.getMessage().setTemplateFileName(file.getAbsolutePath());
            loadHtml(file.getAbsolutePath());
            var content = Files.readString(Path.of(file.getAbsolutePath()));
            App.getMessage().getMessageDTO().setTemplate(content);
            nextButton.setDisable(false);
        }
    }

    private void loadHtml(String absolutePath) {
        webView.getEngine().load("file:///" + absolutePath);
    }
}
