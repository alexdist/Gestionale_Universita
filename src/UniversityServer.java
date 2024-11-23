import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

import java.io.*;
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

class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try (ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream());
             ObjectOutputStream output = new ObjectOutputStream(clientSocket.getOutputStream())) {

            Packet packet = (Packet) input.readObject();
            System.out.println("Received request: " + packet.request);

            switch (packet.request) {
                case "LOGIN":
                    login(packet, output);
                    break;

                case "INSERISCI_ESAME":
                    inserisciEsame(packet, output);
                    break;

                case "VISUALIZZA_ESAME":
                    visualizzaEsami(packet, output);
                    break;

                case "VISUALIZZA_ESAME_CORSO":
                    visualizzaEsamiCorso(packet, output);
                    break;

                case "PRENOTA_ESAME":
                    prenotaEsame(packet, output);
                    break;

                default:
                    sendError("Bad request", output);
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void login(Packet packet, ObjectOutputStream output) throws IOException {
        // Implementazione della logica del login
    }

    private void inserisciEsame(Packet packet, ObjectOutputStream output) throws IOException {
        UniversityServer server = UniversityServer.getInstance();
        List<Esame> esamiList = server.getEsamiList();

        Esame esame = (Esame) packet.data;
        esamiList.add(esame);
        System.out.println("Esame inserito: " + esame);

        Packet response = new Packet();
        response.error = new Error("OK", "", "Esame inserito correttamente.");
        output.writeObject(response);
    }

    private void visualizzaEsami(Packet packet, ObjectOutputStream output) throws IOException {
        UniversityServer server = UniversityServer.getInstance();
        List<Esame> esamiList = server.getEsamiList();

        Packet response = new Packet();
        response.data = esamiList;
        response.error = new Error("OK", "", "Esami visualizzati correttamente.");
        output.writeObject(response);
    }

    private void visualizzaEsamiCorso(Packet packet, ObjectOutputStream output) throws IOException {
        UniversityServer server = UniversityServer.getInstance();
        List<Esame> esamiList = server.getEsamiList();

        // Estrai il nome del corso dal pacchetto
        String corso = (String) packet.data;

        // Filtra gli esami associati al corso
        List<Esame> esamiFiltrati = new ArrayList<>();
        for (Esame esame : esamiList) {
            if (esame.getAttivitaDidattica().equalsIgnoreCase(corso)) {
                esamiFiltrati.add(esame);
            }
        }

        // Prepara il pacchetto di risposta
        Packet response = new Packet();
        if (!esamiFiltrati.isEmpty()) {
            response.data = esamiFiltrati;
            response.error = new Error("OK", "", "Esami del corso '" + corso + "' visualizzati correttamente.");
        } else {
            response.data = null;
            response.error = new Error("EMPTY", "Visualizza Esami", "Nessun esame trovato per il corso '" + corso + "'.");
        }

        // Invia la risposta al client
        output.writeObject(response);
    }


    private void prenotaEsame(Packet packet, ObjectOutputStream output) throws IOException {
        // Implementazione per PRENOTA_ESAME
    }

    private void sendError(String message, ObjectOutputStream output) throws IOException {
        Packet errorPacket = new Packet();
        errorPacket.error = new Error("GENERIC", "Bad request", message);
        output.writeObject(errorPacket);
    }
}

