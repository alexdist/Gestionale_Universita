package Server.Segreteria.Action;

import Pacchetto.Packet;
import Server.HandleError;
import Server.ServerUniversita.UniversityServer;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;

public class PrenotazioniStudenteAction implements ISegreteriaServerAction {

    @Override
    public void execute(Packet request, ObjectOutputStream output) throws IOException {
        try (
                // Connessione al server universitario
                Socket universitySocket = new Socket("127.0.0.1", UniversityServer.UNI_SERVER_PORT);
                ObjectOutputStream uniOutput = new ObjectOutputStream(universitySocket.getOutputStream());
                ObjectInputStream uniInput = new ObjectInputStream(universitySocket.getInputStream())
        ) {
            System.out.println("Inoltro richiesta di recupero prenotazioni al Server Universitario...");

            // Invia la richiesta al server universitario
            uniOutput.writeObject(request);
            System.out.println("Richiesta inoltrata: " + request.request);

            // Riceve la risposta dal server universitario
            Packet response = (Packet) uniInput.readObject();
            System.out.println("Risposta ricevuta dal Server Universitario: " + response.info);

            // Invia la risposta al client
            output.writeObject(response);
            System.out.println("Risposta inviata al client.");

        } catch (ConnectException e) {
            // Gestisce il caso specifico in cui non è possibile connettersi al server universitario
            System.err.println("Impossibile connettersi al Server Universitario: " + e.getMessage());
            HandleError.handleError(output, "SERVER_UNREACHABLE", "Visualizza_Esami",
                    "Impossibile connettersi al Server Universitario. Verifica che il server sia attivo.");

        } catch (ClassNotFoundException e) {
            System.err.println("Errore durante la lettura della risposta dal Server Universitario.");
            e.printStackTrace();
            HandleError.handleError(output, "GENERIC", "Prenotazioni_Studente", "Errore durante l'inoltro della richiesta.");

        } catch (IOException e) {
            System.err.println("Errore nella comunicazione con il Server Universitario.");
            e.printStackTrace();

            HandleError.handleError(output, "NETWORK_ERROR", "Prenotazioni_Studente", "Errore di rete durante l'inoltro della richiesta.");
        }
    }
}

