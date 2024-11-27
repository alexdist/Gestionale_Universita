package Server.ServerUniversita;

import Client.Esame;
import java.util.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;

public class UniversityServer implements Serializable {

    public static void main(String[] args) {
        UniversityServer server = UniversityServer.getInstance();
        server.startServer();
    }

    public static final int UNI_SERVER_PORT = 12345;
    private static UniversityServer instance;

    private final List<Esame> esamiList = new ArrayList<>(); // Lista di esami
    private final Map<Integer, Set<Long>> prenotazioni = new HashMap<>(); // Matricola -> Set di codici esami

    private static final String FILE_DATI = "university_data.ser"; // File per la serializzazione

    private UniversityServer() {
        System.out.println("Server Universitario Avviato!");
    }

    public static synchronized UniversityServer getInstance() {
        if (instance == null) {
            instance = new UniversityServer();
            instance.caricaDati(); // Carica i dati all'avvio
        }
        return instance;
    }

    public Map<Integer, Set<Long>> getPrenotazioni() {
        return prenotazioni;
    }

    public synchronized void aggiungiEsame(Esame esame) {
        esamiList.add(esame);
    }

    public synchronized void rimuoviEsame(Esame esame) {
        esamiList.remove(esame);
    }

    public synchronized List<Esame> getEsamiList() {
        return new ArrayList<>(esamiList); // Copia per evitare modifiche concorrenti
    }

    public synchronized boolean prenotaEsame(int matricola, long codiceEsame) {
        // Ottieni o crea il set di esami prenotati per questa matricola
        Set<Long> esamiPrenotati = prenotazioni.computeIfAbsent(matricola, k -> new HashSet<>());

        // Controlla se lo studente è già iscritto
        if (esamiPrenotati.contains(codiceEsame)) {
            return false; // Studente già iscritto all'esame
        }

        // Effettua la prenotazione aggiungendo l'esame al set
        esamiPrenotati.add(codiceEsame);
        return true; // Prenotazione effettuata con successo
    }

    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(UNI_SERVER_PORT)) {
            System.out.println("Il Server Universitario è in ascolto sulla porta " + UNI_SERVER_PORT);

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("Salvataggio dei dati in corso...");
                salvaDati(); // Salva i dati prima dell'arresto
            }));

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nuovo Client connesso");
                new Thread(new ClientHandler(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Metodo per salvare i dati su file
    public synchronized void salvaDati() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_DATI))) {
            oos.writeObject(esamiList);
            oos.writeObject(prenotazioni);
            System.out.println("Dati salvati correttamente su " + FILE_DATI);
        } catch (IOException e) {
            System.err.println("Errore durante il salvataggio dei dati: " + e.getMessage());
        }
    }

    // Metodo per caricare i dati da file
    public synchronized void caricaDati() {
        File file = new File(FILE_DATI);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_DATI))) {
                List<Esame> caricatiEsamiList = (List<Esame>) ois.readObject();
                Map<Integer, Set<Long>> caricatePrenotazioni = (Map<Integer, Set<Long>>) ois.readObject();

                esamiList.clear();
                esamiList.addAll(caricatiEsamiList);

                prenotazioni.clear();
                prenotazioni.putAll(caricatePrenotazioni);

                System.out.println("Dati caricati correttamente da " + FILE_DATI);
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Errore durante il caricamento dei dati: " + e.getMessage());
            }
        } else {
            System.out.println("Nessun file di dati trovato, inizializzazione del server con dati vuoti.");
        }
    }
}






