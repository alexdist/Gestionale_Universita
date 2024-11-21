package Segreteria_Command;

import Esame.Esame;

import java.io.Serializable;

/*public class Richiesta implements Serializable {
    private static final long serialVersionUID = 1L; // Per garantire compatibilità tra versioni

    private String tipo; // Tipo della richiesta (es. "AGGIUNGI_ESAME", "GET_ESAMI")
    private Esame esame; // Esame.Esame coinvolto (opzionale)
    private int idEsame; // ID dell'esame (opzionale)
    private String corso;

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

    public Richiesta(String tipo, String corso){
        this.tipo = tipo;
        this.corso = corso;
    }

    // Getters e Setters
    public String getTipo() {
        return tipo;
    }

    public Esame getEsame() {
        return esame;
    }

    public String getNomeEsame(){
        return esame.getNomeEsame();
    }

    public int getIdEsame() {
        return idEsame;
    }
}*/

import java.io.Serializable;

public class Richiesta implements Serializable {
    private static final long serialVersionUID = 1L; // Compatibilità tra versioni

    private String tipo; // Tipo della richiesta (es. "AGGIUNGI_ESAME", "GET_ESAMI")
    private Esame esame; // Esame coinvolto (opzionale)
    private int idEsame; // ID dell'esame (opzionale)
    private String corso; // Corso (opzionale)

    // Costruttore per richieste semplici
    public Richiesta(String tipo) {
        this.tipo = tipo;
        this.esame = null;
        this.idEsame = -1; // Valore di default per un ID non definito
        this.corso = null;
    }

    // Costruttore per richieste con esame
    public Richiesta(String tipo, Esame esame) {
        this.tipo = tipo;
        this.esame = esame;
        this.idEsame = -1; // Non è specificato
        this.corso = null;
    }

    // Costruttore per richieste con ID
    public Richiesta(String tipo, int idEsame) {
        this.tipo = tipo;
        this.idEsame = idEsame;
        this.esame = null;
        this.corso = null;
    }

    // Costruttore per richieste con corso
    public Richiesta(String tipo, String corso) {
        this.tipo = tipo;
        this.corso = corso;
        this.esame = null;
        this.idEsame = -1; // Non è specificato
    }

    // Getters
    public String getTipo() {
        return tipo;
    }

    public Esame getEsame() {
        return esame;
    }

    public int getIdEsame() {
        return idEsame;
    }

    public String getCorso() {
        return corso;
    }

    // Override del toString per debugging
    @Override
    public String toString() {
        return "Richiesta{" +
                "tipo='" + tipo + '\'' +
                ", esame=" + esame +
                ", idEsame=" + idEsame +
                ", corso='" + corso + '\'' +
                '}';
    }
}






