package Server.Segreteria.Action;

import Pacchetto.Packet;
import java.io.IOException;
import java.io.ObjectOutputStream;

public interface ISegreteriaServerAction {
    void execute(Packet request, ObjectOutputStream output) throws IOException;
}