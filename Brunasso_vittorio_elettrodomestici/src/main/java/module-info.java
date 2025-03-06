module org.elettrodomestici.brunasso_vittorio_elettrodomestici {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.elettrodomestici.brunasso_vittorio_elettrodomestici to javafx.fxml;
    exports org.elettrodomestici.brunasso_vittorio_elettrodomestici;
}