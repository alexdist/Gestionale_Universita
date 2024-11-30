package Client.Segreteria;

import Client.Esame;
import Pacchetto.Packet;
import java.io.*;
import java.net.ConnectException;
import java.net.Socket;

public class SegreteriaClient {
    private final String serverIp;
    private final int serverPort;

    //inizializza l'oggetto con l'indirizzo ip e la porta de server
    public SegreteriaClient(String serverIp, int serverPort) {
        this.serverIp = serverIp;
        this.serverPort = serverPort;
    }

    //metodo per aggiungere un nuovo esame
    public void aggiungiEsame(Esame esame) {
        try (Socket clientSocket = new Socket(serverIp, serverPort); //connessione al server
             ObjectOutputStream output = new ObjectOutputStream(clientSocket.getOutputStream()); //stream per inviare dati al server
             ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream())) { //stream per ricevere

            // Crea il pacchetto con il tipo di richiesta in questo caso quella di inserire un esame
            Packet richiesta = new Packet("INSERISCI_ESAME", esame, null);

            // Invia la richiesta al server
            output.writeObject(richiesta);

            // Riceve la risposta
            Packet risposta = (Packet) input.readObject();
            if ("OK".equals(risposta.info.getCode())) { //se la risposta è "OK" l'esame è stato inserito con successo
                System.out.println("Appello inserito con successo.");
            } else {
                System.err.println("Errore nell'inserimento dell'Appello: " + risposta.info.getDescription());
            }

        } catch (ConnectException e) {
            System.err.println("Ops sembra che non c'è alcuna connessione al server. Verifica che il server sia attivo.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Errore durante l'invio della richiesta: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //metodo per eliminare un appello usando il codice dell'appello
    public void eliminaEsame(long codiceEsame) throws IOException {
        try(Socket clientSocket2 = new Socket(serverIp, serverPort);
        ObjectOutputStream output = new ObjectOutputStream(clientSocket2.getOutputStream());
        ObjectInputStream input = new ObjectInputStream(clientSocket2.getInputStream())) {

            Packet richiesta = new Packet("ELIMINA_ESAME", Long.valueOf(codiceEsame), null);

            output.writeObject(richiesta);

            Packet risposta = (Packet) input.readObject();
            if ("OK".equals(risposta.info.getCode())) {
                System.out.println("Appello eliminato con successo.");

            } else {
                System.err.println("Errore nell'eliminazione dell'appello: " + risposta.info.getDescription());
            }
        }catch (ConnectException e) { //eccezione per problemi di connessione
            System.err.println("Ops sembra che non c'è alcuna connessione al server. Verifica che il server sia attivo.");
        }catch (IOException | ClassNotFoundException e) { //eccezioni generiche
            System.err.println("Errore durante l'invio della richiesta: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
