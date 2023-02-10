package com.pwbsoft.jserialmailer;

import com.pwbsoft.jserialmailer.controllers.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum Views {
    HOME ("views/HOME.fxml", HomeController.class),
    CSV_SELECT ("views/CSV_SELECT.fxml", CsvSelectController.class),
    HTML_IMPORT ("views/HTML_IMPORT.fxml", HtmlImportController.class),
    REVIEW ("views/REVIEW.fxml", ReviewController.class),
    PROCESS ("views/PROCESS.fxml", ProcessController.class),
    END ("views/END.fxml", EndController.class),
    ABOUT("views/ABOUT.fxml", CsvSelectController.class),
    ;
    private final String path;
    private final Class<? extends BaseController> controller;

    @SneakyThrows
    public BaseController getControllerInstance() {
        return (BaseController) Arrays.stream(controller.getDeclaredConstructors()).findFirst().get().newInstance();
    }
}
