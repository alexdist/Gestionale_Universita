import java.io.*;
import java.net.*;
import java.util.*;

import java.io.*;
import java.net.*;
import java.util.*;

public class StudenteClient {

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
        Packet richiesta = new Packet();
        richiesta.request = "VISUALIZZA_ESAME";
        richiesta.data = null;

        try (Socket clientSocket = new Socket(SERVER_IP, SEGRETERIA_SERVER_PORT);
             ObjectOutputStream output = new ObjectOutputStream(clientSocket.getOutputStream());
             ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream())) {

            // Invia la richiesta al server
            output.writeObject(richiesta);

            // Ricevi la risposta dal server
            Packet risposta = (Packet) input.readObject();

            if ("OK".equals(risposta.error.getCode())) {
                // Elabora e stampa gli esami
                List<Esame> esami = (List<Esame>) risposta.data;
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
        Packet richiesta = new Packet();
        richiesta.request = "VISUALIZZA_ESAME_CORSO";
        richiesta.data = corso;

        try (Socket clientSocket = new Socket(SERVER_IP, SEGRETERIA_SERVER_PORT);
             ObjectOutputStream output = new ObjectOutputStream(clientSocket.getOutputStream());
             ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream())) {

            // Invia la richiesta al server
            output.writeObject(richiesta);

            // Ricevi la risposta dal server
            Packet risposta = (Packet) input.readObject();

            if ("OK".equals(risposta.error.getCode())) {
                // Elabora e stampa gli esami
                List<Esame> esami = (List<Esame>) risposta.data;
                printEsami(esami);
            } else {
                System.out.println("Errore nel caricamento degli esami: " + risposta.error.getDescription());
            }

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Errore di connessione: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void printEsami(List<Esame> esami) {
        if (esami == null || esami.isEmpty()) {
            System.out.println("Nessun esame disponibile.");
            return;
        }

        System.out.println("Esami disponibili:");
        for (Esame esame : esami) {
            System.out.println("Codice esame: " + esame.getCodiceEsame());
            System.out.println("Attivita_Didattica: " + esame.getAttivitaDidattica());
            System.out.println("Data: " + esame.getDataAppello());
            System.out.println("Descrizione: " + esame.getDescrizione());
            System.out.println("Presidente " + esame.getPresidente());

        }
    }
}




