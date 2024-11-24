package Client.Studente;

import java.io.Serializable;

public class StudenteUniversitario implements IStudente, Serializable {

    private String nome;
    private String cognome;
    private String codiceFiscale;
    private int matricola;
    private String email;


    public StudenteUniversitario(String nome, String cognome, String codiceFiscale, int matricola, String email){
        this.nome = nome;
        this.cognome = cognome;
        this.codiceFiscale = codiceFiscale;
        this.matricola = matricola;
        this.email = email;
    }

    public StudenteUniversitario(String nome, String cognome) {
        this.nome = nome;
        this.cognome = cognome;
        this.codiceFiscale = null;
        this.matricola = 0;
        this.email = null;
    }

    public StudenteUniversitario(String nome, String cognome, int matricola) {
        this.nome = nome;
        this.cognome = cognome;
        this.codiceFiscale = null;
        this.matricola = 0;
        this.email = null;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public int getMatricola() {
        return matricola;
    }

    public void setMatricola(int matricola) {
        this.matricola = matricola;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}