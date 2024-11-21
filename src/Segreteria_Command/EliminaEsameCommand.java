package Segreteria_Command;

import Segreteria_Command.Interfaces.ICommand;

import java.io.IOException;

public class EliminaEsameCommand implements ICommand {
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
}
