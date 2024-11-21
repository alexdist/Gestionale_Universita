package Server;

import Segreteria_Command.Richiesta;

import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketServerUniversitario {

    private static final int PORT = 12345; // Porta su cui il server ascolta
    private final ServerUniversitario serverUniversitario; // Singleton del Server
    private final ExecutorService threadPool; // Thread pool per gestire più client

    public SocketServerUniversitario() {
        this.serverUniversitario = ServerUniversitario.getInstance();
        this.threadPool = Executors.newFixedThreadPool(10); // Gestisce fino a 10 client simultaneamente
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server Universitario in ascolto sulla porta " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept(); // Accetta connessione del client
                System.out.println("Nuovo client connesso: " + clientSocket.getInetAddress());

                // Gestione del client in un thread separato
                threadPool.execute(new ClientHandler(clientSocket, serverUniversitario));
            }
        } catch (IOException e) {
            System.err.println("Errore nel server socket: " + e.getMessage());
        } finally {
            threadPool.shutdown(); // Chiude il thread pool al termine
        }
    }

    // Classe per gestire ogni client in un thread separato
    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;
        private final ServerUniversitario serverUniversitario;

        public ClientHandler(Socket clientSocket, ServerUniversitario serverUniversitario) {
            this.clientSocket = clientSocket;
            this.serverUniversitario = serverUniversitario;
        }

        @Override
        public void run() {
            try (
                    ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream());
                    ObjectOutputStream output = new ObjectOutputStream(clientSocket.getOutputStream())
            ) {
                // Legge la richiesta del client
                Richiesta richiesta = (Richiesta) input.readObject();
                System.out.println("Segreteria_Command.Richiesta ricevuta: " + richiesta);

                // Elabora la richiesta
                Risposta risposta = processaRichiesta(richiesta);

                // Invia la risposta al client
                output.writeObject(risposta);
                output.flush();

            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Errore nella gestione del client: " + e.getMessage());
            } finally {
                try {
                    clientSocket.close(); // Chiude la connessione con il client
                } catch (IOException e) {
                    System.err.println("Errore nella chiusura del socket client: " + e.getMessage());
                }
            }
        }

        // Metodo per processare la richiesta e restituire una risposta
        private Risposta processaRichiesta(Richiesta richiesta) {
            switch (richiesta.getTipo()) {
                case "AGGIUNGI_ESAME":
                    serverUniversitario.aggiungiEsame(richiesta.getEsame());
                    return new Risposta("SUCCESSO", "Esame.Esame aggiunto con successo!");

                case "GET_ESAMI":
                    return new Risposta("SUCCESSO", serverUniversitario.getEsames());

                case "CANCELLA_ESAME":
                    serverUniversitario.cancellaEsame(richiesta.getIdEsame());
                    return new Risposta("SUCCESSO", "Esame.Esame cancellato con successo!");

                default:
                    return new Risposta("ERRORE", "Tipo di richiesta non riconosciuto.");
            }
        }
    }

    public static void main(String[] args) {
        SocketServerUniversitario server = new SocketServerUniversitario();
        server.start();
    }
}
