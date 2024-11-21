package Segreteria_Command;

import java.io.*;
import java.net.Socket;

public class ServerConnection {

    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    private final String serverAddress;
    private final int serverPort;

    public ServerConnection(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    // Metodo per connettersi al server
    public void connect() throws IOException {
        socket = new Socket(serverAddress, serverPort);
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        inputStream = new ObjectInputStream(socket.getInputStream());
        System.out.println("Connesso al server: " + serverAddress + ":" + serverPort);
    }

    // Metodo per inviare oggetti al server
    public void send(Object obj) throws IOException {
        outputStream.writeObject(obj);
        outputStream.flush();
    }

    // Metodo per ricevere oggetti dal server
    public Object receive() throws IOException, ClassNotFoundException {
        return inputStream.readObject();
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
