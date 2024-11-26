package Client.Studente;

import Client.Esame;
import Pacchetto.Packet;
import Pacchetto.Prenotazione;

import java.io.*;
import java.net.*;


/*
public class Client.Studente.StudenteClient {

    private static final String SERVER_IP = "127.0.0.1";
    private static final int SEGRETERIA_SERVER_PORT = 12345;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Menu principale
        System.out.println("Benvenuto nel gestionale dell'università!");
        System.out.println("1) Visualizza gli esami disponibili");
        System.out.println("2) Visualizza gli esami disponibili per uno specifico Corso");
        System.out.println("0) Esci");
        System.out.print("Scelta: ");
        int scelta = scanner.nextInt();
        switch (scelta) {
            case 1:
                visualizzaEsami();
                break;
            case 2:
                visualizzaEsamiCorso();
                break;

            default:
                System.out.println("Scelta non riconosciuta, riprova");
                break;

        }

        scanner.close();
    }

    private static void visualizzaEsami() {


        System.out.println("Visualizza gli esami disponibili:");

        // Prepara il pacchetto di richiesta per il server
        Pacchetto.Packet richiesta = new Pacchetto.Packet();
        richiesta.request = "VISUALIZZA_ESAME";
        richiesta.data = null;

        try (Socket clientSocket = new Socket(SERVER_IP, SEGRETERIA_SERVER_PORT);
             ObjectOutputStream output = new ObjectOutputStream(clientSocket.getOutputStream());
             ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream())) {

            // Invia la richiesta al server
            output.writeObject(richiesta);

            // Ricevi la risposta dal server
            Pacchetto.Packet risposta = (Pacchetto.Packet) input.readObject();

            if ("OK".equals(risposta.error.getCode())) {
                // Elabora e stampa gli esami
                List<Client.Esame> esami = (List<Client.Esame>) risposta.data;
                printEsami(esami);
            } else {
                System.out.println("Errore nel caricamento degli esami: " + risposta.error.getDescription());
            }

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Errore di connessione: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void visualizzaEsamiCorso() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Visualizza gli esami disponibili per uno specifico Corso:");
        System.out.print("Corso: ");
        String corso = scanner.nextLine();


        // Prepara il pacchetto di richiesta per il server
        Pacchetto.Packet richiesta = new Pacchetto.Packet();
        richiesta.request = "VISUALIZZA_ESAME_CORSO";
        richiesta.data = corso;

        try (Socket clientSocket = new Socket(SERVER_IP, SEGRETERIA_SERVER_PORT);
             ObjectOutputStream output = new ObjectOutputStream(clientSocket.getOutputStream());
             ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream())) {

            // Invia la richiesta al server
            output.writeObject(richiesta);

            // Ricevi la risposta dal server
            Pacchetto.Packet risposta = (Pacchetto.Packet) input.readObject();

            if ("OK".equals(risposta.error.getCode())) {
                // Elabora e stampa gli esami
                List<Client.Esame> esami = (List<Client.Esame>) risposta.data;
                printEsami(esami);
            } else {
                System.out.println("Errore nel caricamento degli esami: " + risposta.error.getDescription());
            }

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Errore di connessione: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void printEsami(List<Client.Esame> esami) {
        if (esami == null || esami.isEmpty()) {
            System.out.println("Nessun esame disponibile.");
            return;
        }

        System.out.println("Esami disponibili:");
        for (Client.Esame esame : esami) {
            System.out.println("Codice esame: " + esame.getCodiceEsame());
            System.out.println("Attivita_Didattica: " + esame.getAttivitaDidattica());
            System.out.println("Data: " + esame.getDataAppello());
            System.out.println("Descrizione: " + esame.getDescrizione());
            System.out.println("Presidente " + esame.getPresidente());

        }
    }
}*/

import java.util.List;

public class StudenteClient {

    private  String serverIp;
    private  int serverPort;
    private IStudente studente;

   /* public StudenteClient(String serverIp, int serverPort, IStudente studente) {
        this.serverIp = "127.0.0.1";
        this.serverPort = 12345;
        this.studente = studente;
    }*/

    public StudenteClient(String serverIp, int serverPort) {
       this.serverIp = serverIp;
       this.serverPort = serverPort;
    }


    public boolean autenticaStudente(IStudente studente) throws IOException, ClassNotFoundException {
        this.studente = studente; // Salva l'oggetto studente
        Packet richiesta = new Packet();
        richiesta.request = "LOGIN";
        richiesta.data = this.studente;

        Packet risposta = inviaRichiestaGenerica(richiesta);

        if ("OK".equals(risposta.error.getCode())) {
            System.out.println("Autenticazione riuscita per lo studente.");
            return true;
        } else {
            System.err.println("Errore nell'autenticazione: " + risposta.error.getDescription());
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
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Errore durante la prenotazione: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Esame> visualizzaPrenotazioniStudente(int matricola) throws IOException, ClassNotFoundException {
        Packet richiesta = new Packet();
        richiesta.request = "VISUALIZZA_PRENOTAZIONI_STUDENTE";
        richiesta.data = matricola; // Invia solo il numero di matricola

        // Invia la richiesta generica e ottieni la risposta
        Packet risposta = inviaRichiestaGenerica(richiesta);

        if ("OK".equals(risposta.error.getCode())) {
            // Restituisce la lista di esami dalla risposta
            return (List<Esame>) risposta.data;
        } else {
            // Gestisce l'errore con un'eccezione
            throw new IOException("Errore durante il recupero delle prenotazioni: " + risposta.error.getDescription());
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
        }
    }

    public IStudente getStudente() {
        return studente;
    }
}





