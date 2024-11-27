package Server.ServerUniversita.ServerUAction;

import Client.Esame;
import Pacchetto.CustomError;
import Pacchetto.Packet;
import Pacchetto.Prenotazione;
import Server.ServerUniversita.UniversityServer;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;


public class PrenotaEsameServerAction implements IServerAction {

    public void execute(Packet packet, ObjectOutputStream output) throws IOException {
        System.out.println("Tentativo di prenotazione esame...");
        UniversityServer server = UniversityServer.getInstance();
        List<Esame> esamiList = server.getEsamiList();

        // Estrazione dell'oggetto prenotazione
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
            // Esame non trovato
            System.err.println("Esame con codice " + codiceEsame + " non trovato.");
            response.error = new CustomError("NOT_FOUND", "Prenotazione", "Esame non trovato.");
            response.data = null;
        } else if (esameTrovato.getNumeroPrenotazione() < esameTrovato.getNumeroMassimoPrenotati()) {
            // Esame disponibile, tenta la prenotazione
            boolean prenotazioneEffettuata = server.prenotaEsame(matricola, codiceEsame);

            if (prenotazioneEffettuata) {
                esameTrovato.incrementaNumeroPrenotazione();
                int numeroPrenotazione = esameTrovato.getNumeroPrenotazione();
                System.out.println("Studente con matricola " + matricola + " prenotato con successo per l'esame con codice " + codiceEsame + "di " + esameTrovato.getAttivitaDidattica());
                response.error = new CustomError("OK", "", "Prenotazione effettuata con successo.");
                response.data = numeroPrenotazione;
            } else {
                // Studente già iscritto a questo esame
                System.err.println("Studente con matricola " + matricola + " già iscritto all'esame con codice " + codiceEsame);
                response.error = new CustomError("ALREADY_REGISTERED", "Prenotazione", "Studente già iscritto all'esame.");
                response.data = null;
            }
        } else {
            // Esame pieno
            System.err.println("Esame " + codiceEsame + " pieno. Prenotazione non riuscita per studente con matricola " + matricola);
            response.error = new CustomError("FULL", "Prenotazione", "Numero massimo di prenotati raggiunto.");
            response.data = null;
        }

        // Invia la risposta al client
        output.writeObject(response);
    }

}




