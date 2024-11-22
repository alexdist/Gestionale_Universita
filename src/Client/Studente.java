package Client;

import Segreteria_Command.ConcreteCommands.GetEsamiPerCorsoCommand;
import Segreteria_Command.ConcreteCommands.PrenotaEsameCommand;
import Segreteria_Command.Interfaces.ICommand;
import Segreteria_Command.Invoker.Segreteria;

import java.io.IOException;
import java.util.List;
import Esame.Esame;

public class Studente {

    private final Segreteria segreteria;

    public Studente(Segreteria segreteria) {
        this.segreteria = segreteria;
    }

    public void prenotaEsame(int idEsame) throws IOException, ClassNotFoundException{
        try {
            ICommand prenotaCommand = new PrenotaEsameCommand(segreteria.getEsameManager(), idEsame);
            segreteria.setCommand(prenotaCommand);
            String risultato = (String) segreteria.eseguiComando();
            System.out.println("Risultato prenotazione: " + risultato);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void visualizzaEsamiPerCorso(String corso) throws IOException, ClassNotFoundException{
        try {
            ICommand esamiPerCorsoCommand = new GetEsamiPerCorsoCommand(segreteria.getEsameManager(), corso);
            segreteria.setCommand(esamiPerCorsoCommand);
            List<Esame> esami = (List<Esame>) segreteria.eseguiComando();
            System.out.println("Esami disponibili per il corso " + corso + ":");
            esami.forEach(System.out::println);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

