package Segreteria_Command.ConcreteCommands;

import Segreteria_Command.Receiver.EsameManager;
import Segreteria_Command.Interfaces.ICommand;

import java.io.IOException;

/*public class ListaEsameCommand implements ICommand {
    private Segreteria segreteria;
    private Esame esame;

    public ListaEsameCommand(Segreteria segreteria){
        //   this.server = ServerUniversitario.getInstance();

        this.segreteria = segreteria;
    }

    public void execute() throws IOException, ClassNotFoundException {
        System.out.println(segreteria.getEsami());
    }
}*/

public class ListaEsameCommand implements ICommand {

    private final EsameManager esameManager;

    public ListaEsameCommand(EsameManager esameManager) {
        this.esameManager = esameManager;
    }

    @Override
    public Object execute() throws IOException, ClassNotFoundException {
        return esameManager.getEsami();
    }
}

