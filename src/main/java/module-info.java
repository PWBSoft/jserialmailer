module com.pwbsoft.jserialmailer {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires handlebars;
    requires lombok;
    requires org.apache.commons.csv;
    requires org.eclipse.angus.mail;

    exports com.pwbsoft.jserialmailer;
    opens com.pwbsoft.jserialmailer.data to javafx.base;
    opens com.pwbsoft.jserialmailer to javafx.fxml;
    opens com.pwbsoft.jserialmailer.controllers to javafx.fxml;
}
