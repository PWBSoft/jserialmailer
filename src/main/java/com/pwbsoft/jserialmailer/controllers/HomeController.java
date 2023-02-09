package com.pwbsoft.jserialmailer.controllers;

import com.pwbsoft.jserialmailer.App;
import com.pwbsoft.jserialmailer.Views;
import javafx.fxml.FXML;
import javafx.scene.SubScene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import lombok.Setter;
import lombok.SneakyThrows;

public class HomeController extends BaseController {
    @FXML
    Label statusLabel;
    @FXML
    ProgressBar progressBar;
    @FXML
    SubScene container;

    BaseController subSceneController;

    @Override @FXML
    public void initialize() {
        statusLabel.setText("Ready");
        setSubScene(Views.CSV_SELECT);
    }

    @SneakyThrows
    private void setSubScene(Views view) {
        subSceneController = view.getControllerInstance();
        var fxml = App.loadFXML(view, subSceneController);
        container.setRoot(fxml);

    }

    public void setStatus(String labelValue, double progress) {
        statusLabel.setText(labelValue);
        progressBar.setProgress(progress);
    }
}
