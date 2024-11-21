package Segreteria_Command.Interfaces;

import java.io.IOException;

public interface ICommand {

    Object execute() throws IOException, ClassNotFoundException;
}
