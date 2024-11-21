import Esame.Esame;
import Segreteria_Command.*;
import Segreteria_Command.Interfaces.ICommand;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {


       /* Esame.Esame esame = new Esame.Esame("Matematica", LocalDateTime.now(), "Esame.Esame finale", "Prof. Rossi");
        System.out.println(esame);*/

        try {
            Segreteria segreteria = new Segreteria("localhost", 12345);
            Esame esame = new Esame("Matematica", LocalDateTime.now(), "Esame.Esame di base", "Prof. Rossi");
            ICommand command = new AggiungiEsameCommand(segreteria, esame);
            command.execute();

            // Aggiungi un esame
           // Esame.Esame esame = new Esame.Esame("Matematica", LocalDateTime.now(), "Esame.Esame di base", "Prof. Rossi");
           // System.out.println(segreteria.aggiungiEsame(esame));



            // Ottieni la lista degli esami

          //  ICommand command2 = new ListaEsameCommand(segreteria);
          //  command2.execute();

           // int id = 1;
           // ICommand command3 = new EliminaEsameCommand(segreteria, id);
           // command3.execute();

            //List<Esame> esami = segreteria.getEsami();
            //esami.forEach(System.out::println);

            // Cancella un esame
            //System.out.println(segreteria.cancellaEsame(0));

            // Chiudi la connessione
            segreteria.chiudiConnessione();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}