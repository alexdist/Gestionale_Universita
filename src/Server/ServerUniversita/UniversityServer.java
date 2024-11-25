package Server.ServerUniversita;

import Client.Esame;
//import Server.ClientHandler;

import java.io.*;
//import java.lang.Pacchetto.Error;
import java.net.*;
import java.util.*;


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


    public List<Esame> getEsamiList() {
        return esamiList;
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



