package Segreteria_Command;

import Esame.Esame;

import java.io.Serializable;

public class Richiesta implements Serializable {
    private static final long serialVersionUID = 1L; // Per garantire compatibilità tra versioni

    private String tipo; // Tipo della richiesta (es. "AGGIUNGI_ESAME", "GET_ESAMI")
    private Esame esame; // Esame.Esame coinvolto (opzionale)
    private int idEsame; // ID dell'esame (opzionale)

    // Costruttore per richieste semplici
    public Richiesta(String tipo) {
        this.tipo = tipo;
    }

    // Costruttore per richieste con esame
    public Richiesta(String tipo, Esame esame) {
        this.tipo = tipo;
        this.esame = esame;
    }

    // Costruttore per richieste con ID
    public Richiesta(String tipo, int idEsame) {
        this.tipo = tipo;
        this.idEsame = idEsame;
    }

    // Getters e Setters
    public String getTipo() {
        return tipo;
    }

    public Esame getEsame() {
        return esame;
    }

    public int getIdEsame() {
        return idEsame;
    }
}
