package Client.Studente;

import Client.Esame;
import Pacchetto.Packet;
import Pacchetto.Prenotazione;
import java.io.*;
import java.net.*;
import java.util.Collections;
import java.util.List;

public class StudenteClient {

    private  String serverIp;
    private  int serverPort;
    private IStudente studente;


    public StudenteClient(String serverIp, int serverPort) {
       this.serverIp = serverIp;
       this.serverPort = serverPort;
    }

    public boolean autenticaStudente(IStudente studente) throws IOException, ClassNotFoundException {
        this.studente = studente; // Salva l'oggetto studente
        Packet richiesta = new Packet();
        richiesta.request = "LOGIN";
        richiesta.data = this.studente;

        try {
            Packet risposta = inviaRichiestaGenerica(richiesta);

            if ("OK".equals(risposta.error.getCode())) {
                System.out.println("Autenticazione riuscita per lo studente.");
                return true;
            } else {
                System.err.println("Errore nell'autenticazione: " + risposta.error.getDescription());
                return false;
            }
        } catch (ConnectException e) {
            System.err.println("Ops sembra che non c'è alcuna connessione al server. Verifica che il server sia attivo.");
            return false;
        }
    }


    public void prenotaAppello(long codiceEsame) throws IOException, ClassNotFoundException {
        Packet richiesta = new Packet();
        richiesta.request = "PRENOTA_ESAME";
        richiesta.data = new Prenotazione(studente.getMatricola(), codiceEsame);

        try {
            Packet risposta = inviaRichiestaGenerica(richiesta);
            if ("OK".equals(risposta.error.getCode())) {
                int numeroProgressivo = (int) risposta.data;
                System.out.println("Prenotazione effettuata con successo! Numero progressivo prenotazione: " + numeroProgressivo);
            } else {
                System.err.println("Errore nella prenotazione: " + risposta.error.getDescription());
            }
        } catch (ConnectException e) {
            System.err.println("Ops sembra che non c'è alcuna connessione al server. Verifica che il server sia attivo.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Errore durante la prenotazione: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Esame> visualizzaPrenotazioniStudente(int matricola) {
        Packet richiesta = new Packet();
        richiesta.request = "VISUALIZZA_PRENOTAZIONI_STUDENTE";
        richiesta.data = matricola; // Invia solo il numero di matricola

        try {
            // Invia la richiesta generica e ottieni la risposta
            Packet risposta = inviaRichiestaGenerica(richiesta);

            if ("OK".equals(risposta.error.getCode())) {
                // Restituisce la lista di esami dalla risposta
                return (List<Esame>) risposta.data;
            } else if ("NOT_FOUND".equals(risposta.error.getCode())) {
                // Gestisce il caso in cui non ci sono prenotazioni
                System.out.println("Non ci sono prenotazioni per la matricola: " + matricola);
                return Collections.emptyList(); // Restituisce una lista vuota
            } else {
                // Gestisce errori generici del server
                System.err.println("Errore dal server: " + risposta.error.getDescription());
                return Collections.emptyList();
            }
        } catch (ConnectException e) {
            System.err.println("Ops, sembra che non c'è alcuna connessione al server. Verifica che il server sia attivo.");
            return Collections.emptyList(); // Restituisce una lista vuota in caso di errore di connessione
        } catch (ClassNotFoundException e) {
            System.err.println("Errore durante la lettura della risposta: " + e.getMessage());
            e.printStackTrace();
            return Collections.emptyList();
        } catch (IOException e) {
            System.err.println("Errore durante il recupero delle prenotazioni: " + e.getMessage());
            e.printStackTrace();
            return Collections.emptyList();
        }
    }


    // Metodo per inviare richieste generiche al server
    private Packet inviaRichiestaGenerica(Packet richiesta) throws IOException, ClassNotFoundException {
        try (Socket clientSocket = new Socket(serverIp, serverPort);
             ObjectOutputStream output = new ObjectOutputStream(clientSocket.getOutputStream());
             ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream())) {

            // Invia il pacchetto al server
            output.writeObject(richiesta);
            // Riceve la risposta dal server
            return (Packet) input.readObject();

        } catch (ConnectException e) {
            //System.err.println("Ops sembra che non c'è alcuna connessione al server. Verifica che il server sia attivo.");
            throw e; // Rilancia l'eccezione per la gestione a livello superiore
        }
    }


    public List<Esame> visualizzaEsami() throws IOException, ClassNotFoundException {
        Packet richiesta = new Packet();
        richiesta.request = "VISUALIZZA_ESAME";
        richiesta.data = null;

        return inviaRichiesta(richiesta);
    }

    public List<Esame> visualizzaEsamiCorso(String corso) throws IOException, ClassNotFoundException {
        Packet richiesta = new Packet();
        richiesta.request = "VISUALIZZA_ESAME_CORSO";
        richiesta.data = corso;

        return inviaRichiesta(richiesta);
    }

    // Metodo per inviare richieste specifiche (Esami) al server
    private List<Esame> inviaRichiesta(Packet richiesta) throws IOException, ClassNotFoundException {
        try (Socket clientSocket = new Socket(serverIp, serverPort);
             ObjectOutputStream output = new ObjectOutputStream(clientSocket.getOutputStream());
             ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream())) {

            output.writeObject(richiesta);
            Packet risposta = (Packet) input.readObject();

            if ("OK".equals(risposta.error.getCode())) {
                return (List<Esame>) risposta.data;
            } else {
                throw new IOException("Errore dal server: " + risposta.error.getDescription());
            }
        } catch (ConnectException e) {
            System.err.println("Ops sembra che non c'è alcuna connessione al server. Verifica che il server sia attivo.");
            return Collections.emptyList(); // Restituisce una lista vuota in caso di errore
        }
    }


    public IStudente getStudente() {
        return studente;
    }
}





