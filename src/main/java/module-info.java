module com.cadm.feriados {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;
    
    opens model.classes to javafx.base;
    opens com.cadm.feriados to javafx.fxml; 
   // opens com.cadm.feriados to javafx.base;
    exports com.cadm.feriados;
    
}
