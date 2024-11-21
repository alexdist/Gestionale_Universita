package Segreteria_Command;

import Esame.Esame;
import Segreteria_Command.Interfaces.ICommand;

import java.io.IOException;

public class ListaEsameCommand implements ICommand {
    private Segreteria segreteria;
    private Esame esame;

    public ListaEsameCommand(Segreteria segreteria){
        //   this.server = ServerUniversitario.getInstance();

        this.segreteria = segreteria;
    }

    public void execute() throws IOException, ClassNotFoundException {
        System.out.println(segreteria.getEsami());
    }
}
