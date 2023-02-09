package com.pwbsoft.jserialmailer;

import com.pwbsoft.jserialmailer.controllers.BaseController;
import com.pwbsoft.jserialmailer.controllers.CsvSelectController;
import com.pwbsoft.jserialmailer.controllers.HomeController;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum Views {
    HOME ("views/HOME.fxml", HomeController.class),
    CSV_SELECT ("views/CSV_SELECT.fxml", CsvSelectController.class),
    HTML_IMPORT ("views/HTML_IMPORT.fxml", CsvSelectController.class),
    REVIEW ("views/REVIEW.fxml", CsvSelectController.class),
    PROCESS ("views/PROCESS.fxml", CsvSelectController.class),
    END ("views/END.fxml", CsvSelectController.class),
    ABOUT("views/ABOUT.fxml", CsvSelectController.class),
    ;
    private final String path;
    private final Class<? extends BaseController> controller;

    @SneakyThrows
    public BaseController getControllerInstance() {
        return (BaseController) Arrays.stream(controller.getDeclaredConstructors()).findFirst().get().newInstance();
    }
}
