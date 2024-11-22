package Segreteria_Command.ConcreteCommands;

import Segreteria_Command.Interfaces.ICommand;
import Segreteria_Command.Receiver.EsameManager;

import java.io.IOException;

public class GetEsamiPerCorsoCommand implements ICommand {
    private final EsameManager esameManager;
    private final String corso;

    public GetEsamiPerCorsoCommand(EsameManager esameManager, String corso) {
        this.esameManager = esameManager;
        this.corso = corso;
    }

    @Override
    public Object execute() throws IOException, ClassNotFoundException {
        return esameManager.getEsamiPerCorso(corso);
    }
}

