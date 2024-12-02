package Server.Segreteria.Action;

import Pacchetto.Packet;
import Server.HandleError;
import Server.ServerUniversita.UniversityServer;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;

//intermediario che si occupa di inoltrare la richiesta di prenotazione di un esame al server universitario
public class PrenotaEsameAction implements ISegreteriaServerAction {

    public void execute(Packet request, ObjectOutputStream output) throws IOException{

        try (
                //crea una connessione socket con il server universitario
                Socket universitySocket = new Socket("127.0.0.1", UniversityServer.UNI_SERVER_PORT);
                ObjectOutputStream uniOutput = new ObjectOutputStream(universitySocket.getOutputStream()); //stream invio datial server
                ObjectInputStream uniInput = new ObjectInputStream(universitySocket.getInputStream()) //stream per ricevere dati dal server
        ) {
            System.out.println("Inoltro richiesta di prenotazione di un esame al Server Universitario...");

            uniOutput.writeObject(request); //inoltra richiesta del client al server universitario

            Packet response = (Packet) uniInput.readObject();// Riceve la risposta dal server universitario
            System.out.println("Risposta ricevuta dal Server Universitario: " + response.info);

            output.writeObject(response); // Invia la risposta al client
            System.out.println("Risposta inviata al client.");

        } catch (ConnectException e) {
            // Gestisce il caso specifico in cui non è possibile connettersi al server universitario
            System.err.println("Impossibile connettersi al Server Universitario: " + e.getMessage());
            HandleError.handleError(output, "SERVER_UNREACHABLE", "Visualizza_Esami",
                    "Impossibile connettersi al Server Universitario. Verifica che il server sia attivo.");

        } catch (ClassNotFoundException e) {
            System.err.println("Errore durante la lettura della risposta dal Server Universitario.");
            e.printStackTrace();
            HandleError.handleError(output, "GENERIC", "PrenotaEsame", "Errore durante l'inoltro della richiesta.");

        } catch (IOException e) {
            System.err.println("Errore nella comunicazione con il Server Universitario.");
            e.printStackTrace();
            HandleError.handleError(output, "NETWORK_ERROR", "PrenotaEsame", "Errore di rete durante l'inoltro della richiesta.");
        }
    }
}
