import java.io.*;
import java.net.*;

public class TestClient {
    public static void main(String[] args) {
        try (Socket socket = new Socket("127.0.0.1", SegreteriaServer.SEGRETERIA_SERVER_PORT);
             ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream input = new ObjectInputStream(socket.getInputStream())) {

            // Creazione di un pacchetto di richiesta
            Packet packet = new Packet();
            packet.request = "LOGIN"; // Tipo di richiesta (modifica per testare altri casi)
            packet.data = "SampleStudent";

            // Invia la richiesta al server
            output.writeObject(packet);

            // Legge la risposta dal server
            Packet response = (Packet) input.readObject();
            System.out.println("Response received: " + response.error);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
