package Segreteria_Command.ConcreteCommands;

import Segreteria_Command.Receiver.EsameManager;
import Segreteria_Command.Interfaces.ICommand;

import java.io.IOException;

/*public class EliminaEsameCommand implements ICommand {
    private Segreteria segreteria;
    //private Esame esame;
    private int idEsame;

    public EliminaEsameCommand(Segreteria segreteria,int id){
        //   this.server = ServerUniversitario.getInstance();
        //this.esame = esame;
        this.segreteria = segreteria;
        this.idEsame = id;
    }

    public void execute() throws IOException, ClassNotFoundException {
        segreteria.cancellaEsame(idEsame);
    }
}*/

public class EliminaEsameCommand implements ICommand {
    private final EsameManager esameManager;
    private final int idEsame;

    public EliminaEsameCommand(EsameManager esameManager, int idEsame) {
        this.esameManager = esameManager;
        this.idEsame = idEsame;
    }

    @Override
    public Object execute() throws IOException, ClassNotFoundException {
        return esameManager.cancellaEsame(idEsame);
    }
}

