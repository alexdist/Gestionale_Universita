package Server.ServerUniversita.ServerUAction;

import Client.Esame;
//import Pacchetto.CustomError;
import Pacchetto.CustomInfo;
import Pacchetto.Packet;
//import Pacchetto.SuccessInfo;
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
        response.info = new CustomInfo("OK", "VISUALIZZAESAME", "Appelli visualizzati correttamente.");

        output.writeObject(response);
    }
}
