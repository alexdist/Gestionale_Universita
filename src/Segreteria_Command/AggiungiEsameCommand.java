package Segreteria_Command;
import Esame.Esame;
import Segreteria_Command.Interfaces.ICommand;

import java.io.IOException;

public class AggiungiEsameCommand implements ICommand {

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

}
