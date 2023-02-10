package com.pwbsoft.jserialmailer.controllers;

import com.pwbsoft.jserialmailer.SystemInfo;
import com.pwbsoft.jserialmailer.utils.UrlUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import lombok.Setter;

public class LicenseController extends BaseController {
    @FXML
    Button closeBtn;
    @FXML
    Hyperlink goToWebsite;
    @FXML
    Hyperlink learnMore;
    @FXML
    TextFlow licenseText;

    @Setter
    Stage stage;

    @FXML
    @Override
    void initialize() {
        closeBtn.setOnMouseClicked(e -> stage.close());
        goToWebsite.setOnMouseClicked(e -> UrlUtils.openUrl(SystemInfo.getProperties().getProperty("provider.url")));
        licenseText.getChildren().add(new Text(SystemInfo.getLicence()));
        learnMore.setText(String.format("Learn more about %s", SystemInfo.getProperties().getProperty("app.license")));
        learnMore.setOnMouseClicked(e -> UrlUtils.openUrl(SystemInfo.getProperties().getProperty("app.license.url")));
    }
}
