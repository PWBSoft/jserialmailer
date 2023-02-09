module com.pwbsoft.jserialmailer {
    requires javafx.controls;
    requires javafx.fxml;
    requires lombok;
    requires org.apache.commons.csv;

    exports com.pwbsoft.jserialmailer;
    opens com.pwbsoft.jserialmailer to javafx.fxml;
    opens com.pwbsoft.jserialmailer.controllers to javafx.fxml;
}
