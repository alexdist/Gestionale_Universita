package Segreteria_Command.ConcreteCommands;

import Segreteria_Command.Interfaces.ICommand;
import Segreteria_Command.Receiver.EsameManager;

import java.io.IOException;

public class PrenotaEsameCommand implements ICommand {

    private final EsameManager esameManager;
    private final int idEsame;

    public PrenotaEsameCommand(EsameManager esameManager, int idEsame){
        this.esameManager = esameManager;
        this.idEsame = idEsame;
    }

    public Object execute() throws IOException, ClassNotFoundException {
       return esameManager.prenotaEsame(idEsame);
    }
}
