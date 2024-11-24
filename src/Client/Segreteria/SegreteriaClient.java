package Client.Segreteria;

import Client.Esame;
import Pacchetto.Packet;

import java.io.*;

/*public class Client.Segreteria.SegreteriaClient {

    private static final String SERVER_IP = "127.0.0.1";
    private static final int UNI_SERVER_PORT = 12345;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int scelta;

        System.out.println("Benvenuto nel gestionale dell'università");

        do {
            System.out.println("Seleziona cosa vuoi fare:");

            System.out.println("1) Aggiungi un nuovo esame");
            System.out.println("2) Visualizza Esami");

            System.out.println("0) Esci");

            scelta = scanner.nextInt();
            scanner.nextLine(); // Pulizia del buffer
            System.out.println();

            switch (scelta) {
                case 1:
                    aggiungiEsame(scanner);
                    break;

                case 0:
                    System.out.println("Uscita...");
                    break;
                default:
                    System.out.println("Scelta non riconosciuta, riprova");
                    break;
            }
            System.out.println();
        } while (scelta != 0);

        scanner.close();
    }

    private static void aggiungiEsame(Scanner scanner) {
        try (Socket clientSocket = new Socket(SERVER_IP, UNI_SERVER_PORT);
             ObjectOutputStream output = new ObjectOutputStream(clientSocket.getOutputStream());
             ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream())) {

            System.out.println("Inserisci il codice dell'esame:");
            long codiceEsame = scanner.nextLong();
            System.out.println("Inserisci l'attivita didattica a cui è associato l'esame:");
            scanner.nextLine(); // Pulizia buffer
            String attivitaDidattica = scanner.nextLine();

            System.out.println("Inserisci la descrizione (default: vuota):");
            String descrizione = scanner.nextLine();

            System.out.println("Inserisci la data di appello:");
            LocalDateTime dataAppello = LocalDateTime.parse(scanner.nextLine());

            System.out.println("Inserisci il nome del Professore:");
            String nomeProfessore = scanner.nextLine();

            System.out.println("Inserisci il numero massimo di prenotati:");
            int numeroMassimoPrenotati = scanner.nextInt();
            scanner.nextLine(); // Pulizia del buffer


            if (descrizione.isEmpty()) {
                descrizione = "Nessuna descrizione";
            }



            // Crea l'oggetto Client.Esame
            Client.Esame esame = new Client.Esame(attivitaDidattica, dataAppello, descrizione, nomeProfessore, codiceEsame, numeroMassimoPrenotati);

            // Crea il pacchetto per la richiesta
            Pacchetto.Packet richiesta = new Pacchetto.Packet("INSERISCI_ESAME", esame, null);

            // Invia la richiesta e i dati al server
            output.writeObject(richiesta);
            output.writeObject(esame);

            // Riceve la risposta
            Pacchetto.Packet risposta = (Pacchetto.Packet) input.readObject();
            if (risposta.error.getCode().equals("OK")) {
                System.out.println("Client.Esame inserito con successo");
            } else {
                System.out.println("Errore nell'inserimento dell'esame: " + risposta.error.getDescription());
            }

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Errore durante l'invio della richiesta: " + e.getMessage());
            e.printStackTrace();
        }
    }
}*/

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
                System.out.println("Esame inserito con successo.");
            } else {
                System.out.println("Errore nell'inserimento dell'esame: " + risposta.error.getDescription());
            }

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Errore durante l'invio della richiesta: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void eliminaEsame(long codiceEsame) throws IOException {
        try(Socket clientSocket2 = new Socket(serverIp, serverPort);
        ObjectOutputStream output = new ObjectOutputStream(clientSocket2.getOutputStream());
        ObjectInputStream input = new ObjectInputStream(clientSocket2.getInputStream())){

            Packet richiesta = new Packet("ELIMINA_ESAME", Long.valueOf(codiceEsame), null);

            output.writeObject(richiesta);

            Packet risposta = (Packet) input.readObject();
            if ("OK".equals(risposta.error.getCode())) {
                System.out.println("Esame eliminato con successo.");

            }else {
                System.out.println("Errore nell'eliminazione dell'esame: " + risposta.error.getDescription());
            }

        }catch (IOException | ClassNotFoundException e) {
            System.err.println("Errore durante l'invio della richiesta: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
