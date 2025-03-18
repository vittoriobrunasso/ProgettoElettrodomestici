package org.brunasso_elettrodomestici;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.util.Objects;

public class ClientController {

    @FXML
    private ListView<Prodotto> listViewProdotti;  // ListView dei prodotti
    @FXML
    private Button btnAggiungi, btnCarrello, btnRichiediPreventivo; // Aggiunto pulsante per il preventivo
    @FXML
    private Label lblTotale;

    private ClientModel clientModel = new ClientModel();  // Model per il client
    private CarrelModel carrelModel = new CarrelModel(); // Model per il carrello

    @FXML
    public void initialize() {
        // Inizializza i pulsanti e le azioni per ogni pulsante
        btnAggiungi.setOnAction(new AggiungiHandler());
        btnCarrello.setOnAction(new VaiAlCarrelloHandler());
        btnRichiediPreventivo.setOnAction(new VaiAlPreventivoHandler());  // Aggiunto handler per il preventivo

        // Visualizza i prodotti
        aggiornaProdotti();
    }

    // Aggiorna la lista dei prodotti visualizzati nella ListView
    private void aggiornaProdotti() {
        listViewProdotti.getItems().setAll(clientModel.getProdottiDisponibili());
    }

    // Aggiungi un prodotto al carrello
    class AggiungiHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent event) {
            Prodotto prodotto = listViewProdotti.getSelectionModel().getSelectedItem();
            if (prodotto != null) {
                // Esegui l'inserimento nel database
                clientModel.aggiungiProdottoAlCarrelloNelDatabase(prodotto);
                System.out.println("Prodotto aggiunto al carrello: " + prodotto.getNome());
            }
        }
    }

    // Cambio scena per andare al carrello
    class VaiAlCarrelloHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent event) {
            try {
                Stage stage = (Stage) btnCarrello.getScene().getWindow();
                stage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/org/elettrodomestici/brunasso_vittorio_elettrodomestici/carrello_view.fxml")))));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Cambio scena per andare al preventivo
    class VaiAlPreventivoHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent event) {
            try {
                Stage stage = (Stage) btnRichiediPreventivo.getScene().getWindow();
                stage.setScene(new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/org/elettrodomestici/brunasso_vittorio_elettrodomestici/preventivo_view.fxml")))));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
