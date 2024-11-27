package Server.ServerUniversita.ServerUAction;

import Client.Esame;
import Pacchetto.CustomError;
import Pacchetto.Packet;
import Server.ServerUniversita.UniversityServer;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

public class VisualizzaEsameServerAction implements IServerAction {
    public void execute(Packet packet, ObjectOutputStream output) throws IOException {
        UniversityServer server = UniversityServer.getInstance();
        List<Esame> esamiList = server.getEsamiList();

        Packet response = new Packet();
        response.data = esamiList;
        response.error = new CustomError("OK", "", "Esami visualizzati correttamente.");
        output.writeObject(response);
    }
}
