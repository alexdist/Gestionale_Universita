import Esame.Esame;
import Segreteria_Command.*;
import Segreteria_Command.ConcreteCommands.AggiungiEsameCommand;
import Segreteria_Command.ConcreteCommands.ListaEsameCommand;
import Segreteria_Command.Interfaces.ICommand;
import Segreteria_Command.Invoker.Segreteria;
import Segreteria_Command.Receiver.EsameManager;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import Client.Studente;

public class Main {
    public static void main(String[] args) {
        try {
            // Configurazione della connessione
            ServerConnection connection = new ServerConnection("localhost", 12345);
            connection.connect();

            // Receiver
            EsameManager esameManager = new EsameManager(connection);

            // Invoker
            Segreteria segreteria = new Segreteria(esameManager);

            // Aggiungi un esame
            /*Esame nuovoEsame = new Esame("Matematica", LocalDateTime.now(), "EsameOrale", "Prof. Bianchi");
            ICommand aggiungiCommand = new AggiungiEsameCommand(esameManager, nuovoEsame);
            segreteria.setCommand(aggiungiCommand);
            System.out.println(segreteria.eseguiComando());*/

            // Client: Studente
            Studente studente = new Studente(segreteria);

            // Lo studente richiede esami per un corso
            studente.visualizzaEsamiPerCorso("Matematica");

            // Ottieni la lista degli esami
            /*ICommand getEsamiCommand = new ListaEsameCommand(esameManager);
            segreteria.setCommand(getEsamiCommand);
            List<Esame> esami = (List<Esame>) segreteria.eseguiComando();
            esami.forEach(System.out::println);*/

            // Cancella un esame
            /*ICommand cancellaCommand = new EliminaEsameCommand(esameManager, 1);
            segreteria.setCommand(cancellaCommand);
            System.out.println(segreteria.eseguiComando());*/

            // Chiudi la connessione
            connection.chiudiConnessione();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
