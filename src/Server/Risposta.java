package Server;

import Esame.Esame;

import java.io.Serializable;
import java.util.List;

public class Risposta implements Serializable {
    private static final long serialVersionUID = 1L;

    private String stato; // Stato della risposta ("SUCCESSO" o "ERRORE")
    private String messaggio; // Messaggio descrittivo
    private List<Esame> dati; // Dati opzionali (es. lista di esami)

    // Costruttore per risposte senza dati
    public Risposta(String stato, String messaggio) {
        this.stato = stato;
        this.messaggio = messaggio;
    }

    // Costruttore per risposte con dati
    public Risposta(String stato, List<Esame> dati) {
        this.stato = stato;
        this.dati = dati;
    }

    // Getters e Setters
    public String getStato() {
        return stato;
    }

    public String getMessaggio() {
        return messaggio;
    }

    public List<Esame> getDati() {
        return dati;
    }
}
