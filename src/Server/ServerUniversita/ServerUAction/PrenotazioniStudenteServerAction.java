package Server.ServerUniversita.ServerUAction;

import Client.Esame;
//import Pacchetto.CustomError;
import Pacchetto.CustomInfo;
import Pacchetto.Packet;
import Server.ServerUniversita.UniversityServer;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PrenotazioniStudenteServerAction implements IServerAction {

    @Override
    public void execute(Packet packet, ObjectOutputStream output) throws IOException {
        System.out.println("Tentativo di recupero delle prenotazioni per lo studente...");

        UniversityServer server = UniversityServer.getInstance();

        // Estrazione del numero di matricola dal pacchetto
        int matricola = (int) packet.data;

        // Recupero delle prenotazioni e della lista degli esami
        Map<Integer, Set<Long>> prenotazioni = server.getPrenotazioni(); // Metodo per ottenere la mappa delle prenotazioni
        List<Esame> esamiList = server.getEsamiList();

        Packet response = new Packet();

        // Verifica se lo studente ha prenotazioni
        if (prenotazioni.containsKey(matricola)) {
            // Recupera i codici degli esami prenotati per lo studente
            Set<Long> codiciEsamiPrenotati = prenotazioni.get(matricola);

            // Trova gli oggetti Esame associati ai codici
            List<Esame> esamiPrenotati = new ArrayList<>();
            for (Long codiceEsame : codiciEsamiPrenotati) {
                esamiList.stream()
                        .filter(esame -> esame.getCodiceEsame() == codiceEsame)
                        .findFirst()
                        .ifPresent(esamiPrenotati::add);
            }

            if (!esamiPrenotati.isEmpty()) {
                // Prenotazioni trovate, prepara la risposta
                System.out.println("Prenotazioni trovate per lo studente " + matricola + ": " + esamiPrenotati);
                response.info = new CustomInfo("OK", "PRENOTAZIONI", "Prenotazioni trovate con successo.");
                response.data = esamiPrenotati; // Restituisce la lista di Esame
            } else {
                // Caso improbabile in cui i codici non corrispondono ad esami
                System.err.println("Errore: Nessun appello valido trovato per i codici associati allo studente " + matricola);
                response.info = new CustomInfo("NOT_FOUND", "PRENOTAZIONI", "Appelli associati non trovati.");
                response.data = null;
            }
        } else {
            // Nessuna prenotazione trovata per la matricola
            System.err.println("Nessuna prenotazione trovata per lo studente con matricola " + matricola);
            response.info = new CustomInfo("NOT_FOUND", "PRENOTAZIONI", "Nessuna prenotazione trovata per questa matricola.");
            response.data = null;
        }
        // Invia la risposta al client
        output.writeObject(response);
    }
}


