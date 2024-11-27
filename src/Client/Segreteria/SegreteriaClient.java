package Client.Segreteria;

import Client.Esame;
import Pacchetto.Packet;
import java.io.*;
import java.net.ConnectException;
import java.net.Socket;

public class SegreteriaClient {
    private final String serverIp;
    private final int serverPort;

    public SegreteriaClient(String serverIp, int serverPort) {
        this.serverIp = serverIp;
        this.serverPort = serverPort;
    }

    public void aggiungiEsame(Esame esame) {
        try (Socket clientSocket = new Socket(serverIp, serverPort);
             ObjectOutputStream output = new ObjectOutputStream(clientSocket.getOutputStream());
             ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream())) {

            // Crea il pacchetto per la richiesta
            Packet richiesta = new Packet("INSERISCI_ESAME", esame, null);

            // Invia la richiesta al server
            output.writeObject(richiesta);

            // Riceve la risposta
            Packet risposta = (Packet) input.readObject();
            if ("OK".equals(risposta.error.getCode())) {
                System.out.println("Appello inserito con successo.");
            } else {
                System.err.println("Errore nell'inserimento dell'Appello: " + risposta.error.getDescription());
            }

        } catch (ConnectException e) {
            System.err.println("Ops sembra che non c'è alcuna connessione al server. Verifica che il server sia attivo.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Errore durante l'invio della richiesta: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public void eliminaEsame(long codiceEsame) throws IOException {
        try(Socket clientSocket2 = new Socket(serverIp, serverPort);
        ObjectOutputStream output = new ObjectOutputStream(clientSocket2.getOutputStream());
        ObjectInputStream input = new ObjectInputStream(clientSocket2.getInputStream())) {

            Packet richiesta = new Packet("ELIMINA_ESAME", Long.valueOf(codiceEsame), null);

            output.writeObject(richiesta);

            Packet risposta = (Packet) input.readObject();
            if ("OK".equals(risposta.error.getCode())) {
                System.out.println("Appello eliminato con successo.");

            } else {
                System.err.println("Errore nell'eliminazione dell'appello: " + risposta.error.getDescription());
            }
        }catch (ConnectException e) {
            System.err.println("Ops sembra che non c'è alcuna connessione al server. Verifica che il server sia attivo.");
        }catch (IOException | ClassNotFoundException e) {
            System.err.println("Errore durante l'invio della richiesta: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
