package Pacchetto;

import java.io.Serializable;

public class Packet implements Serializable {
    public String request; // Tipo di richiesta (es. "LOGIN", "INSERISCI_ESAME")
    public Object data;    // Dati associati alla richiesta
    public CustomError error;    // Oggetto che rappresenta eventuali errori

    // Costruttore di default
    public Packet() {
    }

    // Costruttore con parametri
    public Packet(String request, Object data, CustomError error) {
        this.request = request;
        this.data = data;
        this.error = error;
    }

    @Override
    public String toString() {
        return "Pacchetto.Packet{" +
                "request='" + request + '\'' +
                ", data=" + data +
                ", error=" + error +
                '}';
    }
}
