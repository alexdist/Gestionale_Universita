import Segreteria_Command.ConcreteCommands.AggiungiEsameCommand;
import Segreteria_Command.Interfaces.ICommand;
import Segreteria_Command.Invoker.Segreteria;
import Segreteria_Command.Receiver.EsameManager;
import Segreteria_Command.ServerConnection;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import Esame.Esame;
import Client.Studente;

public class Main3 {

    public static void main(String[] args) {
        // Scegli quale fase eseguire commentando l'altra
        //fase1_creaEsame(); // Fase 1: Creazione dell'esame
        fase2_prenotaEsame(); // Fase 2: Prenotazione dello stesso esame da parte di 3 studenti
    }

    // Fase 1: Creazione dell'esame
    public static void fase1_creaEsame() {
        try {
            // Configura il server e la connessione per aggiungere un esame
            ServerConnection connection = new ServerConnection("localhost", 12345);
            connection.connect();

            EsameManager esameManager = new EsameManager(connection);
            Segreteria segreteria = new Segreteria(esameManager);

            // Aggiunge un esame con posti limitati al server
            Esame nuovoEsame = new Esame("Matematica", LocalDateTime.now(), "Esame Scritto", "Prof. Bianchi", 5);
            ICommand aggiungiCommand = new AggiungiEsameCommand(esameManager, nuovoEsame);
            segreteria.setCommand(aggiungiCommand);
            System.out.println(segreteria.eseguiComando());

            // Chiude la connessione per questa operazione di configurazione
            connection.chiudiConnessione();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Fase 2: Prenotazione dell'esame da parte di 3 studenti
    public static void fase2_prenotaEsame() {
        ExecutorService executor = Executors.newFixedThreadPool(3); // Pool di thread per 3 studenti

        try {
            // Simula 3 studenti che tentano di prenotare lo stesso esame
            for (int i = 0; i < 3; i++) {
                int studenteId = i + 1;
                executor.execute(() -> {
                    ServerConnection studenteConnection = null;
                    try {
                        // Configura una nuova connessione per ciascuno studente
                        studenteConnection = new ServerConnection("localhost", 12345);
                        studenteConnection.connect();

                        EsameManager studenteEsameManager = new EsameManager(studenteConnection);
                        Segreteria studenteSegreteria = new Segreteria(studenteEsameManager);
                        Studente studente = new Studente(studenteSegreteria);

                        // Lo studente tenta di prenotare
                        System.out.println("Studente " + studenteId + " tenta di prenotare l'esame.");
                        studente.prenotaEsame(0); // Supponendo che l'ID dell'esame creato sia 0

                    } catch (Exception e) {
                        System.err.println("Errore per studente " + studenteId + ": " + e.getMessage());
                    } finally {
                        // Chiude la connessione per lo studente
                        if (studenteConnection != null) {
                            studenteConnection.chiudiConnessione();
                        }
                    }
                });

                // Introduce un ritardo tra le richieste dei thread
                try {
                    Thread.sleep(100); // Ritardo di 500 millisecondi
                } catch (InterruptedException e) {
                    System.err.println("Errore durante il ritardo: " + e.getMessage());
                }
            }

        } finally {
            // Chiude il pool di thread
            executor.shutdown();
        }
    }
}
