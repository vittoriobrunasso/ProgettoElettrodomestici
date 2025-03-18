package org.brunasso_elettrodomestici;

public class Prodotto {
    private String codice;  // Codice univoco del prodotto
    private String nome;    // Nome del prodotto
    private double prezzo;  // Prezzo del prodotto

    // Costruttore che include il codice
    public Prodotto(String codice, String nome, double prezzo) {
        this.codice = codice;
        this.nome = nome;
        this.prezzo = prezzo;
    }

    // Getter per il codice
    public String getCodice() {
        return this.codice;
    }

    // Getter per il nome
    public String getNome() {
        return this.nome;
    }

    // Getter per il prezzo
    public double getPrezzo() {
        return this.prezzo;
    }

    // Override del toString per una visualizzazione completa
    @Override
    public String toString() {
        return this.nome + "- €" + this.prezzo;
    }
}

