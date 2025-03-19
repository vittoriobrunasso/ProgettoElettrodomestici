package org.elettrodomestici.brunasso_vittorio_elettrodomestici;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

import java.sql.SQLException;
import java.util.List;

public class CarrelController {
    private String username;  // Utente loggato
    private CarrelModel model = new CarrelModel();

    @FXML private ListView<Prodotto> listViewCarrello;
    @FXML private Label lblTotaleCarrello;
    @FXML private Button btnRimuoviCarrello;
    @FXML private Button btnPagamento;

    @FXML private RadioButton btnContante;
    @FXML private RadioButton btnCartaCredito;
    @FXML private RadioButton btnBancomat;

    @FXML
    private ToggleGroup metodoPagamento; // ToggleGroup per i RadioButton

    @FXML
    public void initialize() {
        // Assegna il ToggleGroup ai RadioButton
        metodoPagamento = new ToggleGroup();
        btnContante.setToggleGroup(metodoPagamento);
        btnCartaCredito.setToggleGroup(metodoPagamento);
        btnBancomat.setToggleGroup(metodoPagamento);
        listViewCarrello.setItems(FXCollections.observableArrayList());
    }

    // Metodo per caricare il carrello con i prodotti dell'utente
    public void caricaCarrello(String username) {
        this.username = username;
        // listViewCarrello.getItems().clear();
        try {
            List<Prodotto> prodotti = model.getCarrello(username);
            listViewCarrello.getItems().addAll(prodotti);
            aggiornaTotale();
        } catch (SQLException e) {
            mostraErrore("Errore nel caricamento del carrello!");
        }
    }

    // Metodo per rimuovere un prodotto selezionato
    @FXML
    private void handleRimuoviProdotto() {
        Prodotto selected = listViewCarrello.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                model.rimuoviProdotto(username, selected.getCodice());
                listViewCarrello.getItems().remove(selected);
                aggiornaTotale();
            } catch (SQLException e) {
                mostraErrore("Errore nella rimozione del prodotto!");
            }
        }
    }

    // Metodo per gestire il pagamento
    @FXML
    private void handlePagamento() {
        RadioButton selectedPayment = (RadioButton) metodoPagamento.getSelectedToggle();
        if (selectedPayment == null) {
            mostraErrore("Seleziona un metodo di pagamento!");
            return;
        }

        String metodo = selectedPayment.getText();
        mostraMessaggio("Pagamento effettuato con " + metodo);
    }

    // Metodo per aggiornare il totale del carrello
    private void aggiornaTotale() {
        try {
            double totale = model.getTotaleCarrello(username);
            lblTotaleCarrello.setText(String.format("Totale: €%.2f", totale));
        } catch (SQLException e) {
            mostraErrore("Errore nel calcolo del totale!");
        }
    }

    // Metodo per mostrare errori
    private void mostraErrore(String messaggio) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Errore");
        alert.setHeaderText(null);
        alert.setContentText(messaggio);
        alert.showAndWait();
    }

    // Metodo per mostrare messaggi di successo
    private void mostraMessaggio(String messaggio) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(null);
        alert.setContentText(messaggio);
        alert.showAndWait();
    }
}
