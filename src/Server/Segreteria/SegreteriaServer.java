package Server.Segreteria;

import java.io.*;
import java.net.*;

public class SegreteriaServer {

    public static final int SEGRETERIA_SERVER_PORT = 54321; //porta su cui questo server è in ascolto
    public static final int UNI_SERVER_PORT = 12345;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(SEGRETERIA_SERVER_PORT)) {
            System.out.println(" Il Server della Segreteria e' in ascolto sulla porta " + SEGRETERIA_SERVER_PORT);

            while (true) { //loop per accettare connessioni dai client
                Socket clientSocket = serverSocket.accept(); //attende una connessione da parte di un client e l'accetta
                System.out.println("Nuovo Client Connesso");
                new Thread(new SegreteriaClientHandler(clientSocket)).start(); //crea un nuono thread per gestire la connessione con il client
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
