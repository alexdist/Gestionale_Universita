package Server.ServerUniversita;

import Client.Esame;
import java.util.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class UniversityServer {

    public static void main(String[] args) {
        UniversityServer server = UniversityServer.getInstance();
        server.startServer();
    }


    public static final int UNI_SERVER_PORT = 12345;
    private static UniversityServer instance;

    private final List<Esame> esamiList = new ArrayList<>(); // Lista di esami
    private final Map<Integer, Set<Long>> prenotazioni = new HashMap<>(); // Matricola -> Set di codici esami



    private UniversityServer() {
        System.out.println("Server Universitario Avviato!");
    }

    public static synchronized UniversityServer getInstance() {
        if (instance == null) {
            instance = new UniversityServer();
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

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nuovo Client connesso");
                new Thread(new ClientHandler(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}






