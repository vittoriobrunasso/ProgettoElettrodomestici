package org.elettrodomestici.brunasso_vittorio_elettrodomestici;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarrelModel {
    private String dbUrl = "jdbc:mysql://localhost:3306/elettrodomestici";
    private String dbUser = "root";
    private String dbPassword = "Vito.neuer33";

    // Aggiunge un prodotto al carrello


    // Recupera tutti i prodotti nel carrello di un utente
    public List<Prodotto> getCarrello(String username) throws SQLException {
        List<Prodotto> carrello = new ArrayList<>();
        String query = "SELECT r.Codice, r.Nome, r.Prezzo, c.QUANTITA " +
                "FROM CARRELLO c JOIN ricambi r ON c.PRODOTTO_ID = r.Codice " +
                "WHERE c.USERNAME = ?";

        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String codice = resultSet.getString("Codice");
                    String nome = resultSet.getString("Nome");
                    double prezzo = resultSet.getDouble("Prezzo");
                    int quantita = resultSet.getInt("QUANTITA");

                    carrello.add(new Prodotto(codice, nome, prezzo, quantita));
                }
            }
        }
        return carrello;
    }

    // Rimuove un prodotto dal carrello
    public void rimuoviProdotto(String username, String codiceProdotto) throws SQLException {
        String query = "DELETE FROM CARRELLO WHERE USERNAME = ? AND PRODOTTO_ID = ?";

        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, username);
            statement.setString(2, codiceProdotto);

            statement.executeUpdate();
        }
    }

    // Ottiene il totale del carrello
    public double getTotaleCarrello(String username) throws SQLException {
        String query = "SELECT SUM(r.Prezzo * c.QUANTITA) AS Totale " +
                "FROM CARRELLO c JOIN ricambi r ON c.PRODOTTO_ID = r.Codice " +
                "WHERE c.USERNAME = ?";

        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getDouble("Totale");
                }
            }
        }
        return 0.0;
    }
}
