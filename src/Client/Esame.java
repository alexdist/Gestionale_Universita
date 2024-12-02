package Client;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Esame implements Serializable {

    private static final long serialVersionUID = 1L; // Versione seriale
    private String attivitaDidattica;
    private LocalDateTime dataAppello;
    private String descrizione;
    private String presidente;
    private long codiceEsame;
    private int numeroMassimoPrenotati;
    private int numeroPrenotazione;

    public Esame(String attivitaDidattica, LocalDateTime dataAppello, String descrizione, String presidente, long codiceEsame, int numeroMassimoPrenotati) {
        this.attivitaDidattica = attivitaDidattica;
        this.dataAppello = dataAppello;
        this.descrizione = descrizione;
        this.presidente = presidente;
        this.codiceEsame = codiceEsame;
        this.numeroMassimoPrenotati = numeroMassimoPrenotati;
        this.numeroPrenotazione = 0;
    }

    public int getNumeroMassimoPrenotati() {
        return numeroMassimoPrenotati;
    }

    public int getNumeroPrenotazione(){
        return numeroPrenotazione;
    }

    public String getAttivitaDidattica() {
        return attivitaDidattica;
    }

    public void setAttivitaDidattica(String attivitaDidattica) {
        this.attivitaDidattica = attivitaDidattica;
    }

    public LocalDateTime getDataAppello() {
        return dataAppello;
    }

    public void setDataAppello(LocalDateTime dataAppello) {
        this.dataAppello = dataAppello;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getPresidente() {
        return presidente;
    }

    public void setPresidente(String presidente) {
        this.presidente = presidente;
    }

    public long getCodiceEsame() {
        return codiceEsame;
    }

    public void setCodiceEsame(long codiceEsame) {
        this.codiceEsame = codiceEsame;
    }

    public void incrementaNumeroPrenotazione(){
        numeroPrenotazione++;
    }
}


