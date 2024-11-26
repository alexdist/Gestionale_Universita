package Server.ServerUniversita;

import Client.Esame;
//import Server.ClientHandler;

import java.io.*;
//import java.lang.Pacchetto.Error;
import java.net.*;
import java.util.*;

/*
public class UniversityServer {

    public static void main(String[] args) {
        UniversityServer server = UniversityServer.getInstance();
        server.startServer();
    }
    public static final int UNI_SERVER_PORT = 12345;

    // Istanza unica del Server (inizializzata solo quando necessario)
    private static UniversityServer instance;

    private final List<Esame> esamiList = new ArrayList<>(); // Risorsa condivisa


    private UniversityServer() {
        // Costruttore privato per impedire istanze multiple
        System.out.println("Server Universitario Avviato!");
    }

    public static synchronized UniversityServer getInstance() {
        if (instance == null) {
            instance = new UniversityServer(); // Crea l'istanza solo la prima volta
        }
        return instance;
    }


   /* public List<Esame> getEsamiList() {
        return esamiList;
    }*/

/*    public void startServer() {
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

    public synchronized void aggiungiEsame(Esame esame) {
        esamiList.add(esame);
    }

    public synchronized void rimuoviEsame(Esame esame) {
        esamiList.remove(esame);
    }

    public synchronized List<Esame> getEsamiList() {
        return new ArrayList<>(esamiList); // Restituisce una copia per evitare modifiche simultanee
    }
}*/

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

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



/*
public class UniversityServer {

    public static void main(String[] args) {
        UniversityServer server = UniversityServer.getInstance();
        server.startServer();
    }

    private static UniversityServer instance;

    public static final int UNI_SERVER_PORT = 12345;

    // Mappa condivisa: matricola -> lista di codici esame
    private final Map<Integer, List<Long>> prenotazioni = new HashMap<>();

    // Lista di esami disponibili
    private final List<Esame> esamiList = new ArrayList<>();

    // Singleton
    public static synchronized UniversityServer getInstance() {
        if (instance == null) {
            instance = new UniversityServer();
        }
        return instance;
    }

    // Aggiungi una prenotazione in modo thread-safe
    public synchronized void aggiungiPrenotazione(int matricola, long codiceEsame) {
        List<Long> esamiPrenotati = prenotazioni.computeIfAbsent(matricola, k -> new ArrayList<>());
       // if (!esamiPrenotati.contains(codiceEsame)) {
            esamiPrenotati.add(codiceEsame);
            System.out.println("Prenotazione aggiunta: Studente " + matricola + " per esame " + codiceEsame);
        //} else {
        //    System.out.println("Studente " + matricola + " è già prenotato per l'esame " + codiceEsame);
        //}
    }

    // Visualizza le prenotazioni di uno studente
    public synchronized List<Long> visualizzaPrenotazioni(int matricola) {
        return prenotazioni.getOrDefault(matricola, new ArrayList<>());
    }

    // Metodo per ottenere la lista degli esami (non modificabile)
    public List<Esame> getEsamiList() {
        return esamiList;
    }

    // Metodo per aggiungere esami disponibili (per configurare i dati iniziali)
    public synchronized void aggiungiEsame(Esame esame) {
        esamiList.add(esame);
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
*/



