package Server.ServerUniversita.ServerUAction;

import Client.Esame;
import Pacchetto.CustomError;
import Pacchetto.Packet;
import Server.ServerUniversita.UniversityServer;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.List;

public class InserisciEsameServerAction implements IServerAction {

    /*public void execute(Packet packet, ObjectOutputStream output) throws IOException {
        UniversityServer server = UniversityServer.getInstance();
        List<Esame> esamiList = server.getEsamiList();

        Esame esame = (Esame) packet.data;
        esamiList.add(esame);
        System.out.println("Esame inserito: " + esame);

        Packet response = new Packet();
        response.error = new CustomError("OK", "", "Esame inserito correttamente.");
        output.writeObject(response);
    }*/
   /* @Override
    public void execute(Packet packet, ObjectOutputStream output) throws IOException {
        UniversityServer server = UniversityServer.getInstance();
        List<Esame> esamiList = server.getEsamiList();

        Esame esame = (Esame) packet.data;
        long codiceEsame = esame.getCodiceEsame();

        // Controllo se il codice esame esiste già
        boolean esameEsistente = esamiList.stream()
                .anyMatch(e -> e.getCodiceEsame() == codiceEsame);

        Packet response = new Packet();
        if (esameEsistente) {
            System.out.println("Errore: Esame con codice " + codiceEsame + " già presente nella lista.");
            response.error = new CustomError("DUPLICATE", "InserisciEsame",
                    "Un esame con questo codice esiste già.");
        } else {
            // Aggiunta del nuovo esame alla lista
            esamiList.add(esame);
            System.out.println("Esame inserito: " + esame);
            response.error = new CustomError("OK", "", "Esame inserito correttamente.");
        }

        // Invio della risposta al client
        output.writeObject(response);
    }*/





        @Override
        public void execute(Packet packet, ObjectOutputStream output) throws IOException {
            UniversityServer server = UniversityServer.getInstance();
            List<Esame> esamiList = server.getEsamiList();

            Esame esame = (Esame) packet.data;
            long codiceEsame = esame.getCodiceEsame();
            LocalDate dataEsame = esame.getDataAppello().toLocalDate(); // Estrai solo la data senza l'orario
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
                System.out.println("Errore: Esame con codice " + codiceEsame + " già presente nella lista.");
                response.error = new CustomError("DUPLICATE", "InserisciEsame",
                        "Un esame con questo codice esiste già.");
            } else if (esameStessoGiorno && esameStessoNome) {
                System.out.println("Errore: Un altro esame di " + esame.getAttivitaDidattica()+" è già programmato per il giorno " + dataEsame);
                response.error = new CustomError("DATE_CONFLICT", "InserisciEsame",
                        "Errore: Un altro esame di " + esame.getAttivitaDidattica()+" è già programmato per il giorno " + dataEsame);
            } else {
                // Aggiunta del nuovo esame alla lista
                esamiList.add(esame);
                System.out.println("Esame inserito: " + esame);
                response.error = new CustomError("OK", "", "Esame inserito correttamente.");
            }

            // Invio della risposta al client
            output.writeObject(response);
        }
    }


