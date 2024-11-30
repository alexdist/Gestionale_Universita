package Server.ServerUniversita;


import Pacchetto.CustomInfo;
import Pacchetto.Packet;
import Server.ServerUniversita.ServerUAction.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
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
        actions.put("VISUALIZZA_PRENOTAZIONI_STUDENTE", new PrenotazioniStudenteServerAction());
    }

    @Override
    public void run() {
        try (ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream());
             ObjectOutputStream output = new ObjectOutputStream(clientSocket.getOutputStream())) {

            Packet request = (Packet) input.readObject();
            System.out.println("Received request: " + request.request);

            IServerAction action = actions.get(request.request);//ad esempio se request.request è "LOGIN" verrà restituisce
            // l'oggetto associato alla chiave "LOGIN", che in questo caso è un'istanza di LoginServerAction.
            //Quindi nella variabile "action" ci sarà un istanza di quest'ultima, e facendo action.execute eseguiremo
            //la funzione execute di LoginServerAction
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
        errorPacket.info = new CustomInfo("GENERIC", "Bad request", message);
        output.writeObject(errorPacket);
    }
}

