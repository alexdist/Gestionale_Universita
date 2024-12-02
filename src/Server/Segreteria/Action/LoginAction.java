package Server.Segreteria.Action;

import Pacchetto.Packet;
import Server.HandleError;
import Server.ServerUniversita.UniversityServer;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;

//intermediario che si occupa di inoltrare la richiesta di login al server universitario
public class LoginAction implements ISegreteriaServerAction {

    @Override
    public void execute(Packet request, ObjectOutputStream output) throws IOException {
        try (
                //crea una connessione Socket con il server universitario
                Socket universitySocket = new Socket("127.0.0.1", UniversityServer.UNI_SERVER_PORT);
                ObjectOutputStream uniOutput = new ObjectOutputStream(universitySocket.getOutputStream()); //stream per inviare dati al server
                ObjectInputStream uniInput = new ObjectInputStream(universitySocket.getInputStream()) //stream per riceverli
        ) {
            System.out.println("Inoltro richiesta di login al Server Universitario...");

            uniOutput.writeObject(request); //invia la richiesta ricevuta dal client al server universitario

            Packet response = (Packet) uniInput.readObject(); //riceve la risposta dal server universitario
            System.out.println("Risposta ricevuta dal Server Universitario: " + response.info);

            output.writeObject(response); //invia la risposta dal server universitario al client
            System.out.println("Risposta inviata al client.");
        } catch (ConnectException e) {
            // Gestisce il caso specifico in cui non è possibile connettersi al server universitario
            System.err.println("Impossibile connettersi al Server Universitario: " + e.getMessage());
            HandleError.handleError(output, "SERVER_UNREACHABLE", "Visualizza_Esami",
                    "Impossibile connettersi al Server Universitario. Verifica che il server sia attivo.");

        } catch (ClassNotFoundException e) {
            HandleError.handleError(output, "GENERIC", "Login", "Errore durante l'inoltro della richiesta.");
        } catch (IOException e) {
            HandleError.handleError(output, "NETWORK_ERROR", "Login", "Errore di rete durante l'inoltro della richiesta.");
        }
    }
}

