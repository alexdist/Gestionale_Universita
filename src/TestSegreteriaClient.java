import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDateTime;

public class TestSegreteriaClient {
    public static void main(String[] args) {
        // Avvia il server in un thread separato
        new Thread(() -> {
            UniversityServer server = UniversityServer.getInstance();
            server.startServer();
        }).start();

        // Aspetta che il server si avvii
        try {
            Thread.sleep(2000); // Aspetta 2 secondi per il completamento dell'avvio
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Testa il client per aggiungere un esame
        try {
            testAggiungiEsame();
        } catch (Exception e) {
            System.err.println("Errore durante il test: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void testAggiungiEsame() {
        // Dati del nuovo esame da aggiungere
        String attivitaDidattica = "Programmazione 1";
        LocalDateTime dataAppello = LocalDateTime.now().plusDays(7); // Data una settimana da oggi
        String descrizione = "Esame finale del corso";
        String nomeProfessore = "Prof. Rossi";
        long codiceEsame = 101;
        int numeroMassimoPrenotati = 50;

        // Usa SegreteriaClient per aggiungere l'esame
        try (Socket clientSocket = new Socket("127.0.0.1", UniversityServer.UNI_SERVER_PORT);
             ObjectOutputStream output = new ObjectOutputStream(clientSocket.getOutputStream());
             ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream())) {

            // Crea il pacchetto per la richiesta
            Packet richiesta = new Packet("INSERISCI_ESAME", null, null);

            // Crea l'oggetto Esame
            Esame esame = new Esame(attivitaDidattica, dataAppello, descrizione, nomeProfessore, codiceEsame, numeroMassimoPrenotati);

            // Invia la richiesta e i dati al server
            System.out.println("Inviando richiesta di inserimento esame al server...");
            output.writeObject(richiesta);
            output.writeObject(esame);

            // Riceve la risposta
            Packet risposta = (Packet) input.readObject();
            if (risposta.error.getCode().equals("OK")) {
                System.out.println("Esame inserito con successo!");
            } else {
                System.out.println("Errore nell'inserimento dell'esame: " + risposta.error.getDescription());
            }

        } catch (Exception e) {
            System.err.println("Errore durante la comunicazione con il server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
