package Segreteria_Command.ConcreteCommands;
import Esame.Esame;
import Segreteria_Command.Receiver.EsameManager;
import Segreteria_Command.Interfaces.ICommand;

import java.io.IOException;

/*public class AggiungiEsameCommand implements ICommand {

    //private ServerUniversitario server;
    private Segreteria segreteria;
    private Esame esame;

    public AggiungiEsameCommand(Segreteria segreteria,Esame esame){
     //   this.server = ServerUniversitario.getInstance();
        this.esame = esame;
        this.segreteria = segreteria;
    }

    public void execute() throws IOException, ClassNotFoundException {
        segreteria.aggiungiEsame(esame);
    }

}*/
public class AggiungiEsameCommand implements ICommand {
    private final EsameManager esameManager;
    private final Esame esame;

    public AggiungiEsameCommand(EsameManager esameManager, Esame esame) {
        this.esameManager = esameManager;
        this.esame = esame;
    }

    @Override
    public Object execute() throws IOException, ClassNotFoundException {
        return esameManager.aggiungiEsame(esame);
    }
}
