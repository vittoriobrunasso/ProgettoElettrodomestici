package org.elettrodomestici.brunasso_vittorio_elettrodomestici;

public class Prodotto {
    private String codice;
    private String nome;
    private double prezzo;
    private int quantita;  // Nuovo campo

    // Costruttore per prodotto senza quantità
    public Prodotto(String codice, String nome, double prezzo) {
        this.codice = codice;
        this.nome = nome;
        this.prezzo = prezzo;
        this.quantita = 1;  // Default a 1 se non specificato
    }

    // Costruttore per prodotto con quantità (usato nel carrello)
    public Prodotto(String codice, String nome, double prezzo, int quantita) {
        this.codice = codice;
        this.nome = nome;
        this.prezzo = prezzo;
        this.quantita = quantita;
    }

    // Getter
    public String getCodice() { return codice; }
    public String getNome() { return nome; }
    public double getPrezzo() { return prezzo; }
    public int getQuantita() { return quantita; }

    // Setter per quantità
    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    @Override
    public String toString() {
        return STR."\{this.nome} - €\{this.prezzo} x \{this.quantita}";
    }
}
