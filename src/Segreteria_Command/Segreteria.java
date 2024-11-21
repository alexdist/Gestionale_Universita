package Segreteria_Command;

import Esame.Esame;

import java.io.*;
import java.net.*;
import java.util.List;
import Server.Risposta;

public class Segreteria {

    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private final String serverAddress;
    private final int serverPort;

    // Costruttore
    public Segreteria(String serverAddress, int serverPort) throws IOException {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        connectToServer();
    }

    // Metodo per connettersi al server
    private void connectToServer() throws IOException {
        socket = new Socket(serverAddress, serverPort);
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        inputStream = new ObjectInputStream(socket.getInputStream());
        System.out.println("Connesso al server: " + serverAddress + ":" + serverPort);
    }

    // Metodo per aggiungere un esame
    public String aggiungiEsame(Esame esame) throws IOException, ClassNotFoundException {
        // Crea e invia la richiesta
        Richiesta richiesta = new Richiesta("AGGIUNGI_ESAME", esame);
        outputStream.writeObject(richiesta);
        outputStream.flush();

        // Riceve la risposta
        Risposta risposta = (Risposta) inputStream.readObject();
        return risposta.getMessaggio();
    }

    // Metodo per ottenere la lista degli esami
    public List<Esame> getEsami() throws IOException, ClassNotFoundException {
        // Crea e invia la richiesta
        Richiesta richiesta = new Richiesta("GET_ESAMI");
        outputStream.writeObject(richiesta);
        outputStream.flush();

        // Riceve la risposta
        Risposta risposta = (Risposta) inputStream.readObject();
        return risposta.getDati(); // Lista di esami
    }

    // Metodo per cancellare un esame
    public String cancellaEsame(int idEsame) throws IOException, ClassNotFoundException {
        // Crea e invia la richiesta
        Richiesta richiesta = new Richiesta("CANCELLA_ESAME", idEsame);
        outputStream.writeObject(richiesta);
        outputStream.flush();

        // Riceve la risposta
        Risposta risposta = (Risposta) inputStream.readObject();
        return risposta.getMessaggio();
    }

    // Metodo per chiudere la connessione
    public void chiudiConnessione() {
        try {
            if (outputStream != null) outputStream.close();
            if (inputStream != null) inputStream.close();
            if (socket != null) socket.close();
            System.out.println("Connessione chiusa.");
        } catch (IOException e) {
            System.err.println("Errore durante la chiusura della connessione: " + e.getMessage());
        }
    }
}
