package org.brunasso_elettrodomestici;

import java.util.ArrayList;
import java.util.List;

public class CarrelModel {
    private List<Prodotto> carrello;

    public CarrelModel() {
        this.carrello = new ArrayList<>();
    }

    public void aggiungiProdotto(Prodotto prodotto) {
        carrello.add(prodotto);
    }

    public void rimuoviProdotto(Prodotto prodotto) {
        carrello.remove(prodotto);
    }

    public List<Prodotto> getCarrello() {
        return carrello;
    }

    public double getTotale() {
        double totale = 0.0;
        for (Prodotto prodotto : carrello) {
            totale += prodotto.getPrezzo();
        }
        return totale;
    }
}
