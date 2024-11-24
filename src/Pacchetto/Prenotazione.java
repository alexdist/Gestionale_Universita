package Pacchetto;

import java.io.Serializable;

public class Prenotazione implements Serializable {
    private int matricola;
    private long codiceEsame;

    public Prenotazione(int matricola, long codiceEsame) {
        this.matricola = matricola;
        this.codiceEsame = codiceEsame;
    }

    public int getMatricola() {
        return matricola;
    }

    public long getCodiceEsame() {
        return codiceEsame;
    }
}
