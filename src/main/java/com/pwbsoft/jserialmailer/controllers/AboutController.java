package com.pwbsoft.jserialmailer.controllers;

import com.pwbsoft.jserialmailer.App;
import com.pwbsoft.jserialmailer.SystemInfo;
import com.pwbsoft.jserialmailer.data.AboutDTO;
import com.pwbsoft.jserialmailer.utils.UrlUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import lombok.Setter;

public class AboutController extends BaseController {
    @FXML
    Button closeBtn;
    @FXML
    Hyperlink goToWebsite;
    @FXML
    TableView<AboutDTO> table;

    @FXML
    Button licenseBtn;

    @Setter
    Stage stage;

    @FXML
    @Override
    void initialize() {
        closeBtn.setOnMouseClicked(e -> stage.close());
        goToWebsite.setOnMouseClicked(e -> UrlUtils.openUrl(SystemInfo.getProperties().getProperty("provider.url")));
        licenseBtn.setOnMouseClicked(e -> App.openLicense());

        table.getItems().add(new AboutDTO("Application Version", SystemInfo.appVersion()));
        table.getItems().add(new AboutDTO("Application License", SystemInfo.getProperties().getProperty("app.license")));
        table.getItems().add(new AboutDTO("Java Version", SystemInfo.javaVersion()));
        table.getItems().add(new AboutDTO("JavaFX Version", SystemInfo.javafxVersion()));
        table.getItems().add(new AboutDTO("Recognised OS", SystemInfo.getOS().name()));
    }
}
