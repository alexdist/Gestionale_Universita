package Server.ServerUniversita;

import Pacchetto.CustomError;
import Pacchetto.Packet;
import Server.ServerUniversita.ServerUAction.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/*class ClientHandler implements Runnable {
    private Socket clientSocket;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try (ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream());
             ObjectOutputStream output = new ObjectOutputStream(clientSocket.getOutputStream())) {

            Packet packet = (Packet) input.readObject();
            System.out.println("Received request: " + packet.request);

            switch (packet.request) {
                case "LOGIN":
                    login(packet, output);
                    break;

                case "INSERISCI_ESAME":
                    inserisciEsame(packet, output);
                    break;

                case "VISUALIZZA_ESAME":
                    visualizzaEsami(packet, output);
                    break;

                case "VISUALIZZA_ESAME_CORSO":
                    visualizzaEsamiCorso(packet, output);
                    break;

                case "PRENOTA_ESAME":
                    prenotaEsame(packet, output);
                    break;

                case "ELIMINA_ESAME":
                    eliminaEsame(packet, output);
                    break;


                default:
                    sendError("Bad request", output);
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    private void login(Packet packet, ObjectOutputStream output) throws IOException {
        System.out.println("Tentativo di login ricevuto.");

        // Estrazione dell'oggetto Studente dal pacchetto
        IStudente studente = (StudenteUniversitario) packet.data;
        String nome = studente.getNome();
        String cognome = studente.getCognome();

        // Simulazione di un database con nomi e cognomi
        List<String> databaseUtenti = new ArrayList<>();
        databaseUtenti.add("Mario Rossi");
        databaseUtenti.add("Luigi Bianchi");

        // Verifica delle credenziali (solo nome e cognome)
        boolean autenticato = databaseUtenti.contains(nome + " " + cognome);

        // Preparazione della risposta
        Packet response = new Packet();
        if (autenticato) {
            System.out.println("Autenticazione riuscita per l'utente: " + cognome);
            response.error = new CustomError("OK", "", "Autenticazione riuscita.");
        } else {
            System.out.println("Autenticazione fallita per l'utente: " + cognome);
            response.error = new CustomError("AUTH_ERROR", "Login", "Credenziali non valide.");
        }

        // Invio della risposta al client
        output.writeObject(response);
    }


    private void prenotaEsame(Packet packet, ObjectOutputStream output) throws IOException {
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




    private void inserisciEsame(Packet packet, ObjectOutputStream output) throws IOException {
        UniversityServer server = UniversityServer.getInstance();
        List<Esame> esamiList = server.getEsamiList();

        Esame esame = (Esame) packet.data;
        esamiList.add(esame);
        System.out.println("Esame inserito: " + esame);

        Packet response = new Packet();
        response.error = new CustomError("OK", "", "Esame inserito correttamente.");
        output.writeObject(response);
    }


    //Devo far in modo che questa funzione possa essere attivata solo se le prenotazioni per quell'esame sono 0.
    private void eliminaEsame(Packet packet, ObjectOutputStream output) throws IOException {
        UniversityServer server = UniversityServer.getInstance();
        List<Esame> esamiList = server.getEsamiList();

        long codiceEsame = (long) packet.data;

        // Ricerca dell'esame
        Esame esameTrovato = null;
        for (Esame esame : esamiList) {
            if (esame.getCodiceEsame() == codiceEsame) {
                esameTrovato = esame;
                break;
            }
        }

        Packet response = new Packet();
        // response.request = "ELIMINA_ESAME";

        if (esameTrovato == null) {
            // Client.Esame non trovato
            System.out.println("Esame con codice " + codiceEsame + " non trovato.");
            response.error = new CustomError("NOT_FOUND", "EliminaEsame", "Esame non trovato.");
            response.data = null;
        } else {
            // Client.Esame trovato e rimosso
            esamiList.remove(esameTrovato);
            System.out.println("Esame di " + esameTrovato.getAttivitaDidattica() + " con codice " + codiceEsame + " eliminato con successo!");
            response.error = new CustomError("OK", "", "Esame eliminato con successo.");
            response.data = null;
        }

        // Invio della risposta al client
        output.writeObject(response);
    }


    private void visualizzaEsami(Packet packet, ObjectOutputStream output) throws IOException {
        UniversityServer server = UniversityServer.getInstance();
        List<Esame> esamiList = server.getEsamiList();

        Packet response = new Packet();
        response.data = esamiList;
        response.error = new CustomError("OK", "", "Esami visualizzati correttamente.");
        output.writeObject(response);
    }

    private void visualizzaEsamiCorso(Packet packet, ObjectOutputStream output) throws IOException {
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




    private void sendError(String message, ObjectOutputStream output) throws IOException {
        Packet errorPacket = new Packet();
        errorPacket.error = new CustomError("GENERIC", "Bad request", message);
        output.writeObject(errorPacket);
    }
}*/

import java.util.HashMap;
import java.util.Map;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private Map<String, IServerAction> actions;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
        initializeActions();
    }

    private void initializeActions() {
        actions = new HashMap<>();
        actions.put("LOGIN", new LoginServerAction());
        actions.put("INSERISCI_ESAME", new InserisciEsameServerAction());
        actions.put("PRENOTA_ESAME", new PrenotaEsameServerAction());
        actions.put("VISUALIZZA_ESAME", new VisualizzaEsameServerAction());
        actions.put("VISUALIZZA_ESAME_CORSO", new VisualizzaEsameCorsoServerAction());
        actions.put("ELIMINA_ESAME", new EliminaEsameServerAction());
    }

    @Override
    public void run() {
        try (ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream());
             ObjectOutputStream output = new ObjectOutputStream(clientSocket.getOutputStream())) {

            Packet request = (Packet) input.readObject();
            System.out.println("Received request: " + request.request);

            IServerAction action = actions.get(request.request);
            if (action != null) {
                action.execute(request, output);
            } else {
                sendError("Invalid request", output);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void sendError(String message, ObjectOutputStream output) throws IOException {
        Packet errorPacket = new Packet();
        errorPacket.error = new CustomError("GENERIC", "Bad request", message);
        output.writeObject(errorPacket);
    }
}

