package com.pwbsoft.jserialmailer;

import com.pwbsoft.jserialmailer.controllers.AboutController;
import com.pwbsoft.jserialmailer.controllers.BaseController;
import com.pwbsoft.jserialmailer.controllers.HomeController;
import com.pwbsoft.jserialmailer.controllers.LicenseController;
import com.pwbsoft.jserialmailer.data.Recipient;
import com.pwbsoft.jserialmailer.data.SettingsDTO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * JavaFX App
 */
public class App extends Application {

    @Getter
    private static final HomeController homeController = new HomeController();
    private static Scene scene;
    @Getter
    private static App mainApp;
    @Getter
    private static Stage rootStage;
    @Getter
    private static SettingsDTO message = new SettingsDTO();

    static void setStageIcon(Stage stage) {
        stage.getIcons().add(new Image(Objects.requireNonNull(App.class.getClassLoader().getResourceAsStream("icon.png"))));
    }

    static void setView(Views view) throws IOException {
        scene.setRoot(loadFXML(view));
    }

    @SneakyThrows
    public static Parent loadFXML(Views view) throws IOException {
        return loadFXML(view, view.getControllerInstance());
    }

    @SneakyThrows
    public static void openAbout() {
        var aboutCtrl = new AboutController();
        var parent = loadFXML(Views.ABOUT, aboutCtrl);

        var scene = new Scene(parent);
        var stage = popup("About", scene);
        aboutCtrl.setStage(stage);
    }

    @SneakyThrows
    public static void openLicense() {
        var ctrl = new LicenseController();
        var parent = loadFXML(Views.LICENSE, ctrl);
        var scene = new Scene(parent);
        var stage = popup("License", scene);
        ctrl.setStage(stage);
    }

    private static Stage popup(String title, Scene scene) {
        Stage stage = new Stage();
        stage.setTitle(title);
        setStageIcon(stage);
        stage.setResizable(false);
        stage.setAlwaysOnTop(true);
        stage.setScene(scene);
        stage.show();

        return stage;
    }

    @SneakyThrows
    public static Parent loadFXML(Views view, BaseController controller) throws IOException {
        System.out.printf("Loading %s", view.getPath());
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(view.getPath()));
        if (Views.HOME == view)
            fxmlLoader.setController(homeController);
        else
            fxmlLoader.setController(controller);
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        mainApp = this;
        rootStage = stage;
        scene = new Scene(loadFXML(Views.HOME), 640, 480);
        stage.setScene(scene);
        stage.setTitle("JSerialMailer");
        setStageIcon(stage);
        stage.show();
    }

    public void startAnew() {
        message = new SettingsDTO();
        homeController.initialize();
    }

    public void setTemplate(String template, String path) {
        message.setTemplateFileName(path);
        message.getMessageDTO().setTemplate(template);
    }

    public void setRecipientsCSV(String path) {
        message.setCsvFileName(path);
    }

    public void addRecipients(List<Recipient> recipients) {
        message.getMessageDTO().getRecipients().addAll(recipients);
    }

    public void clearRecipients() {
        message.getMessageDTO().getRecipients().clear();
    }

}