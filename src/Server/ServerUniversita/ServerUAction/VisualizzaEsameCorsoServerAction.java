package Server.ServerUniversita.ServerUAction;

import Client.Esame;
import Pacchetto.CustomError;
import Pacchetto.Packet;
import Server.ServerUniversita.UniversityServer;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class VisualizzaEsameCorsoServerAction implements IServerAction {

    public void execute(Packet packet, ObjectOutputStream output) throws IOException {

        UniversityServer server = UniversityServer.getInstance();
        List<Esame> esamiList = server.getEsamiList();

        // Estrai il nome del corso dal pacchetto
        String corso = (String) packet.data;

        // Filtra gli esami associati al corso
        List<Esame> esamiFiltrati = new ArrayList<>();
        for (Esame esame : esamiList) {
            if (esame.getAttivitaDidattica().equalsIgnoreCase(corso)) {
                esamiFiltrati.add(esame);
            }
        }

        // Prepara il pacchetto di risposta
        Packet response = new Packet();
        if (!esamiFiltrati.isEmpty()) {
            response.data = esamiFiltrati;
            response.error = new CustomError("OK", "", "Esami del corso '" + corso + "' visualizzati correttamente.");
        } else {
            response.data = null;
            response.error = new CustomError("EMPTY", "Visualizza Esami", "Nessun esame trovato per il corso '" + corso + "'.");
        }

        // Invia la risposta al client
        output.writeObject(response);
    }

}
