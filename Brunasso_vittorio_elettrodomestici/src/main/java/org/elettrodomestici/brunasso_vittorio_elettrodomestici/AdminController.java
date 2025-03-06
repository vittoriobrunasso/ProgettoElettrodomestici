package org.elettrodomestici.brunasso_vittorio_elettrodomestici;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class AdminController implements Initializable {
    @FXML
    Button VisualizzaAggiungi,
            VisualizzaScorteRicambiPercentualeVendita;
    @FXML
    private void VisualizzaMenu() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/elettrodomestici/brunasso_vittorio_elettrodomestici/amministratore_menu.fxml"));
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        Stage stage = (Stage) VisualizzaAggiungi.getScene().getWindow();
        stage.setScene(scene);
        stage.setMaximized(true); // sets fullscreen
        initializeLoginInterfaceElements(scene); // initializes interface's elements
        stage.show(); // shows the interface
    }

    @FXML
    private void VisualizzaAggiungiRicambio() {

    }

    @FXML
    private void VisualizzaPrezzi() {

    }

    @FXML
    private void initializeLoginInterfaceElements(Scene scene) { // initializes LoginInterface's elements
        VisualizzaAggiungi = (Button) scene.lookup("#VisualizzaAggiungi");
        VisualizzaScorteRicambiPercentualeVendita = (Button) scene.lookup("#viewSparePartsButton");

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
