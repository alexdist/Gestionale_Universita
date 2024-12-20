package Server.Segreteria;

//import Pacchetto.CustomError;
import Pacchetto.CustomInfo;
import Pacchetto.Packet;
import Server.Segreteria.Action.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;


public class SegreteriaClientHandler implements Runnable {
    private Socket clientSocket;
    private Map<String, ISegreteriaServerAction> actions;

    public SegreteriaClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
        initializeActions();
    }

    private void initializeActions() {
        actions = new HashMap<>();
        actions.put("LOGIN", new LoginAction());
        actions.put("VISUALIZZA_ESAME", new VisualizzaEsameAction());
        actions.put("VISUALIZZA_ESAME_CORSO", new VisualizzaEsameCorsoAction());
        actions.put("PRENOTA_ESAME", new PrenotaEsameAction());
        actions.put("VISUALIZZA_PRENOTAZIONI_STUDENTE", new PrenotazioniStudenteAction());
    }

    @Override
    public void run() {
        try (ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream());
             ObjectOutputStream output = new ObjectOutputStream(clientSocket.getOutputStream())) {

            Packet request = (Packet) input.readObject();
            System.out.println("Richiesta ricevuta: " + request.request);

            ISegreteriaServerAction action = actions.get(request.request);
            if (action != null) {
                action.execute(request, output);
            } else {
                sendError("Richiesta non valida", output);
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





