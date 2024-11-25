package Server.Segreteria.Action;

import Pacchetto.CustomError;
import Pacchetto.Packet;
import Server.ServerUniversita.UniversityServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class PrenotaEsameAction implements ISegreteriaServerAction {

    public void execute(Packet request, ObjectOutputStream output) throws IOException{

        try (
                // Connessione al server universitario
                Socket universitySocket = new Socket("127.0.0.1", UniversityServer.UNI_SERVER_PORT);
                ObjectOutputStream uniOutput = new ObjectOutputStream(universitySocket.getOutputStream());
                ObjectInputStream uniInput = new ObjectInputStream(universitySocket.getInputStream())
        ) {
            System.out.println("Inoltro richiesta di login al Server Universitario...");

            // Invia la richiesta di login al server universitario
            uniOutput.writeObject(request);

            // Riceve la risposta dal server universitario
            Packet response = (Packet) uniInput.readObject();
            System.out.println("Risposta ricevuta dal Server Universitario: " + response.error);

            // Invia la risposta al client
            output.writeObject(response);
            System.out.println("Risposta inviata al client.");

        } catch (ClassNotFoundException e) {
            System.err.println("Errore durante la lettura della risposta dal Server Universitario.");
            e.printStackTrace();

            // Invia un errore al client
            Packet errorPacket = new Packet();
            errorPacket.error = new CustomError("GENERIC", "PrenotaEsame", "Errore durante l'inoltro della richiesta.");
            output.writeObject(errorPacket);
        } catch (IOException e) {
            System.err.println("Errore nella comunicazione con il Server Universitario.");
            e.printStackTrace();

            // Invia un errore al client
            Packet errorPacket = new Packet();
            errorPacket.error = new CustomError("NETWORK_ERROR", "PrenotaEsame", "Errore di rete durante l'inoltro della richiesta.");
            output.writeObject(errorPacket);
        }

    }
}
