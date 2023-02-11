package com.pwbsoft.jserialmailer.controllers;

import com.pwbsoft.jserialmailer.App;
import com.pwbsoft.jserialmailer.Views;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import lombok.SneakyThrows;

public class HomeController extends BaseController {
    @FXML
    Label statusLabel;
    @FXML
    ProgressBar progressBar;
    @FXML
    SubScene container;
    @FXML
    MenuItem menuNew;
    @FXML
    MenuItem menuClose;
    @FXML
    MenuItem menuAbout;
    @FXML
    MenuItem menuLicense;

    BaseController subSceneController;

    boolean initialSetup = true;

    @Override
    @FXML
    public void initialize() {
        statusLabel.setText("Ready");
        setSubScene(Views.CSV_SELECT);

        if (initialSetup) {
            initialSetup = false;
            menuAbout.setOnAction(e -> App.openAbout());
            menuLicense.setOnAction(e -> App.openLicense());
            menuNew.setOnAction(e -> App.getMainApp().startAnew());
            menuClose.setOnAction(e -> System.exit(0));
        }
    }

    @SneakyThrows
    public void setSubScene(Views view) {
        subSceneController = view.getControllerInstance();
        var fxml = App.loadFXML(view, subSceneController);
        container.setRoot(fxml);

    }

    public void setStatus(String labelValue, double progress) {
        Platform.runLater(() -> {
            statusLabel.setText(labelValue);
            progressBar.setProgress(progress);
        });
    }
}
