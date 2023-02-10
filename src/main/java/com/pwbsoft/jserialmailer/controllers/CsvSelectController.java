package com.pwbsoft.jserialmailer.controllers;

import com.pwbsoft.jserialmailer.App;
import com.pwbsoft.jserialmailer.Views;
import com.pwbsoft.jserialmailer.data.Recipient;
import com.pwbsoft.jserialmailer.service.CSVService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import lombok.SneakyThrows;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CsvSelectController extends BaseController {

    @FXML
    TableView<Map<String, String>> table;
    @FXML
    TextField csvFileLabel;
    @FXML
    Button csvFileButton;
    @FXML
    Button nextButton;

    @FXML
    @Override
    void initialize() {
        var message = App.getMessage();
        csvFileLabel.setText(message.getCsvFileName());
        csvFileButton.setOnMouseClicked(this::selectFile);
        nextButton.setOnMouseClicked(e -> App.getHomeController().setSubScene(Views.HTML_IMPORT));
    }

    private void selectFile(Event event) {
        var chooser = new FileChooser();
        var filter = new FileChooser.ExtensionFilter("CSV Files", "*.csv");
        chooser.getExtensionFilters().add(filter);
        var file = chooser.showOpenDialog(App.getRootStage());
        if (file != null) {

            csvFileLabel.setText(file.getAbsolutePath());
            App.getMainApp().setRecipientsCSV(file.getAbsolutePath());
            loadCsv(file.getAbsolutePath());
        }
    }

    @SneakyThrows
    void loadCsv(String path) {
        CSVService csvService = new CSVService();
        List<Recipient> recipientsFromCSV = csvService.getRecipientsFromCSV(path);
        App.getMainApp().clearRecipients();
        App.getMainApp().addRecipients(recipientsFromCSV);
        resetTable();
        nextButton.setDisable(recipientsFromCSV.size() == 0);
    }

    void resetTable() {
        var message = App.getMessage();
        var data = message.getMessageDTO().getRecipients().stream().map(Recipient::getData).collect(Collectors.toList());
        table.getColumns().clear();

        message.getMessageDTO().getRecipients().stream().findFirst().ifPresent(
                recipient -> {
                    var keys = recipient.getData().keySet();
                    keys.stream().map(k -> {
                        var column = new TableColumn<Map<String, String>, String>(k);
                        column.setCellValueFactory(features -> {
                            var text = features.getTableColumn().getText();
                            var value = features.getValue().get(text);
                            return new SimpleStringProperty(value);
                        });
                        return column;
                    }).collect(Collectors.toList()).forEach(c -> table.getColumns().add(c));
                }
        );

        table.setItems(FXCollections.observableList(data));
    }
}
