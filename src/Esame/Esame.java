package Esame;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Esame implements Serializable {

    private String nomeEsame;
    private LocalDateTime dataEsame;
    private String nomeDocente;
    private String descrizione;
    private int idEsame;

    public Esame(String nomeEsame, LocalDateTime dataEsame, String descrizione, String nomeDocente){
        this.nomeEsame = nomeEsame;
        this.dataEsame = dataEsame;
        this.descrizione = descrizione;
        this.nomeDocente = nomeDocente;
        this.idEsame = 0;
    }

    public void setNomeEsame(String nomeE){
        this.nomeEsame = nomeE;
    }

    public String getNomeEsame(){
        return nomeEsame;
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

    public int getIdEsame(){
        return this.idEsame;
    }

    @Override
    public String toString() {
        return "Esame.Esame: " + nomeEsame +
                "\nData: " + dataEsame +
                "\nDocente: " + nomeDocente +
                "\nDescrizione: " + descrizione;
    }


}
