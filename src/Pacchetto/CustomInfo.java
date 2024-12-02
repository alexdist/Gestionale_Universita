package Pacchetto;

import java.io.Serializable;

public class CustomInfo implements Serializable {
    private String code;        // Codice (es. "OK", "GENERIC")
    private String source;      // Origine (es. "LOGIN", "PRENOTAZIONE")
    private String description; // Descrizione (es. "Utente autenticato con successo)

    // Costruttore
    public CustomInfo(String code, String source, String description) {
        this.code = code;
        this.source = source;
        this.description = description;
    }

    // Getter e Setter
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Pacchetto.Info{" +
                "code='" + code + '\'' +
                ", source='" + source + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
