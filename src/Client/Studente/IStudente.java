package Client.Studente;

public interface IStudente {
    // Metodi get
    String getNome();
    String getCognome();
    String getCodiceFiscale();
    int getMatricola();
    String getEmail();

    // Metodi set
    void setNome(String nome);
    void setCognome(String cognome);
    void setCodiceFiscale(String codiceFiscale);
    void setMatricola(int matricola);
    void setEmail(String email);
}
