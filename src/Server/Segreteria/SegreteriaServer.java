package Server.Segreteria;

import java.io.*;
import java.net.*;

public class SegreteriaServer {

    public static final int SEGRETERIA_SERVER_PORT = 54321;
    public static final int UNI_SERVER_PORT = 12345;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(SEGRETERIA_SERVER_PORT)) {
            System.out.println(" Il Server della Segreteria e' in ascolto sulla porta " + SEGRETERIA_SERVER_PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Nuovo Client Connesso");
                new Thread(new SegreteriaClientHandler(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
