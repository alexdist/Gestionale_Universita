package Server.ServerUniversita;

import Client.Esame;
import Pacchetto.CustomError;
import Pacchetto.Packet;
import Pacchetto.Prenotazione;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

public class PrenotaEsameServerAction implements IServerAction {

    public void execute(Packet packet, ObjectOutputStream output) throws IOException {
        System.out.println("Tentativo di prenotazione esame...");
        UniversityServer server = UniversityServer.getInstance();
        List<Esame> esamiList = server.getEsamiList();

        // Estrazione dell'oggetto Pacchetto.Prenotazione dal pacchetto
        Prenotazione prenotazione = (Prenotazione) packet.data;
        int matricola = prenotazione.getMatricola();
        long codiceEsame = prenotazione.getCodiceEsame();

        // Ricerca dell'esame
        Esame esameTrovato = null;
        for (Esame esame : esamiList) {
            if (esame.getCodiceEsame() == codiceEsame) {
                esameTrovato = esame;
                break;
            }
        }

        Packet response = new Packet();
        if (esameTrovato == null) {
            // Client.Esame non trovato
            System.out.println("Esame con codice " + codiceEsame + " non trovato.");
            response.error = new CustomError("NOT_FOUND", "Prenotazione", "Esame non trovato.");
            response.data = null;
        } else {
            // Controllo disponibilità posti
            if (esameTrovato.getNumeroPrenotazione() < esameTrovato.getNumeroMassimoPrenotati()) {
                // Lo studente può prenotarsi
                esameTrovato.incrementaNumeroPrenotazione();


                int numeroPrenotazione = esameTrovato.getNumeroPrenotazione();
                System.out.println("Studente con matricola " + matricola + " prenotato con successo per l'esame " + codiceEsame);
                response.error = new CustomError("OK", "", "Prenotazione effettuata con successo.");
                response.data = numeroPrenotazione;
            } else {
                // L'esame ha raggiunto il numero massimo di prenotati
                System.out.println("Esame " + codiceEsame + " pieno. Prenotazione non riuscita per studente con matricola " + matricola);
                response.error = new CustomError("FULL", "Prenotazione", "Numero massimo di prenotati raggiunto.");
                response.data = null;
            }
        }

        // Invia la risposta al client
        output.writeObject(response);
    }
}
