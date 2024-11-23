import java.io.Serializable;

public class Packet implements Serializable {
    public String request; // Tipo di richiesta (es. "LOGIN", "INSERISCI_ESAME")
    public Object data;    // Dati associati alla richiesta
    public Error error;    // Oggetto che rappresenta eventuali errori

    // Costruttore di default
    public Packet() {
    }

    // Costruttore con parametri
    public Packet(String request, Object data, Error error) {
        this.request = request;
        this.data = data;
        this.error = error;
    }

    @Override
    public String toString() {
        return "Packet{" +
                "request='" + request + '\'' +
                ", data=" + data +
                ", error=" + error +
                '}';
    }
}
