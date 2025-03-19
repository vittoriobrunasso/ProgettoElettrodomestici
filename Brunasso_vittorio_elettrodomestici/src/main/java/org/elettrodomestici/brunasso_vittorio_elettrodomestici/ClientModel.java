package org.elettrodomestici.brunasso_vittorio_elettrodomestici;


import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientModel {

    private Connection connection;

    public ClientModel() {
        // Modifica la connessione al database
        try {
             connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/elettrodomestici", "root", "Vito.neuer33");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Prodotto> getProdottiDisponibili() {
        List<Prodotto> prodotti = new ArrayList<>();
        String query = "SELECT * FROM ricambi";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                String nome = rs.getString("Nome");
                double prezzo = rs.getDouble("Prezzo");
                String codice = rs.getString("Codice");

                prodotti.add(new Prodotto(codice, nome, prezzo));  // Assicurati che la classe Prodotto abbia il costruttore giusto
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return prodotti;
    }

    public void aggiungiProdottoAlCarrelloNelDatabase(String username, Prodotto prodotto) {
        String query = "INSERT INTO CARRELLO (USERNAME, PRODOTTO_ID) VALUES (?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, username);  // Inserisce l'username dell'utente
            ps.setString(2, prodotto.getCodice());  // Inserisce il codice del prodotto
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
