module org.romeo_biblioteca.brunasso_elettrodomestici {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.brunasso_elettrodomestici to javafx.fxml;
    exports org.brunasso_elettrodomestici;
}