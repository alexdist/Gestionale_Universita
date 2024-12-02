package Server.ServerUniversita.ServerUAction;

import Client.Esame;
import Pacchetto.CustomInfo;
import Pacchetto.Packet;
import Server.ServerUniversita.UniversityServer;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.List;

public class InserisciEsameServerAction implements IServerAction {

    @Override
        public void execute(Packet packet, ObjectOutputStream output) throws IOException {
            UniversityServer server = UniversityServer.getInstance();
            List<Esame> esamiList = server.getEsamiList();

            Esame esame = (Esame) packet.data;
            long codiceEsame = esame.getCodiceEsame();
            LocalDate dataEsame = esame.getDataAppello().toLocalDate(); // Estrae solo la data senza l'orario
            String nomeEsame = esame.getAttivitaDidattica();

            // Controllo se il codice esame esiste già
            boolean esameEsistente = esamiList.stream()
                    .anyMatch(e -> e.getCodiceEsame() == codiceEsame);

            // Controllo se un altro esame è già programmato nello stesso giorno
            boolean esameStessoGiorno = esamiList.stream()
                    .anyMatch(e -> e.getDataAppello().toLocalDate().isEqual(dataEsame));

            boolean esameStessoNome = esamiList.stream()
                    .anyMatch(e -> e.getAttivitaDidattica().equals(nomeEsame));

            Packet response = new Packet();
            if (esameEsistente) {
                System.err.println("Errore: Appello con codice " + codiceEsame + " già presente nella lista.");
                response.info = new CustomInfo("DUPLICATE", "INSERISCIESAME",
                        "Un appello con questo codice esiste già.");

            } else if (esameStessoGiorno && esameStessoNome) {
                System.err.println("Errore: Un altro appello di " + esame.getAttivitaDidattica()+" è già programmato per il giorno " + dataEsame);
                response.info = new CustomInfo("DATE_CONFLICT", "INSERISCIESAME",
                        "Errore: Un altro appello di " + esame.getAttivitaDidattica()+" è già programmato per il giorno " + dataEsame);

            } else {
                server.aggiungiEsame(esame);
                System.out.println("Appello di " + esame.getAttivitaDidattica() + " con codice " + codiceEsame + " inserito con successo!");
                response.info = new CustomInfo("OK", "INSERISCIESAME", "Appello inserito correttamente.");
            }
            // Invio della risposta al client
            output.writeObject(response);
        }
    }


