package Esame;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Esame implements Serializable {

    private String nomeCorso;
    private LocalDateTime dataEsame;
    private String nomeDocente;
    private String descrizione;
    private int idEsame;
    private int prenotazioniMassime;
    private int numeroPrenotazione;

    public Esame(String nomeCorso, LocalDateTime dataEsame, String descrizione, String nomeDocente){
        this.nomeCorso = nomeCorso;
        this.dataEsame = dataEsame;
        this.descrizione = descrizione;
        this.nomeDocente = nomeDocente;
        this.idEsame = 0;
    }

    public Esame(String nomeCorso, LocalDateTime dataEsame, String descrizione, String nomeDocente, int prenotazioniMassime){
        this.nomeCorso = nomeCorso;
        this.dataEsame = dataEsame;
        this.descrizione = descrizione;
        this.nomeDocente = nomeDocente;
        this.idEsame = 0;
        this.prenotazioniMassime = prenotazioniMassime;
        this.numeroPrenotazione = 0;
    }

    public void setNomeEsame(String nomeE){
        this.nomeCorso = nomeCorso;
    }

    public String getNomeCorso(){
        return nomeCorso;
    }

    public void setDataEsame(LocalDateTime data){
        this.dataEsame = data;
    }

    public String getDataEsame(){
        return this.dataEsame.toString();
    }

    public void setDescrizione(String descrizione){
        this.descrizione = descrizione;
    }

    public String getDescrizione(){
        return this.descrizione;
    }

    public void setNomeDocente(String nomeD)
    {this.nomeDocente = nomeD;}

    public String getNomeDocente(){
        return this.nomeDocente;
    }

    public synchronized void decrementaNumeroPrenotazioni(){
        //this.prenotazioniMassime--;
        this.numeroPrenotazione--;
    }

    public synchronized void incrementaNumeroPrenotazioni(){
       // this.prenotazioniMassime++;
        this.numeroPrenotazione++;
    }

    public int getNumeroPrenotazione(){
        return this.numeroPrenotazione;
    }

    public synchronized boolean isPrenotabile() {
        return numeroPrenotazione < prenotazioniMassime;
    }

    public synchronized void prenota() {
        if (!isPrenotabile()) {
            throw new IllegalStateException("Numero massimo di prenotazioni raggiunto.");
        }
        incrementaNumeroPrenotazioni();
    }

    public int getIdEsame(){
        return this.idEsame;
    }

    @Override
    public String toString() {
        return "Esame: " + nomeCorso +
                "\nData: " + dataEsame +
                "\nDocente: " + nomeDocente +
                "\nDescrizione: " + descrizione;
    }


}
