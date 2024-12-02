package Pacchetto;

import java.io.Serializable;

// classe Prenotazione, viene utilizzata per creare un nuovo oggetto (incapsula matricola e codiceEsame all'interno) da inserire nel campo .data dell'oggetto Packet
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
