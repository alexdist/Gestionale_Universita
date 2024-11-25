package Server.Segreteria;

import Pacchetto.CustomError;
import Pacchetto.Packet;
import Server.Segreteria.Action.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

//Gestisce ogni client connesso al server della segreteria.
/*class SegreteriaClientHandler implements Runnable {
    private Socket clientSocket;

    public SegreteriaClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try (ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream());
             ObjectOutputStream output = new ObjectOutputStream(clientSocket.getOutputStream())) {

            Packet request = (Packet) input.readObject();
            System.out.println("Received request: " + request.request);

            switch (request.request) {
                case "LOGIN":
                    handleLogin(request, output);
                    break;
                case "INSERISCI_ESAME":
                    handleInserisciEsame(request, output);
                    break;

                case "VISUALIZZA_ESAME":
                    handleVisualizzaEsami(request, output);
                    break;

                case "VISUALIZZA_ESAME_CORSO":
                    handleVisualizzaEsamiCorso(request, output);
                    break;

                case "PRENOTA_ESAME":
                    handlePrenotaEsami(request, output);
                    break;
                default:
                    sendError("Invalid request", output);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void handleLogin(Packet request, ObjectOutputStream output) throws IOException {
        try (
                // Connessione al server universitario
                Socket universitySocket = new Socket("127.0.0.1", UniversityServer.UNI_SERVER_PORT);
                ObjectOutputStream uniOutput = new ObjectOutputStream(universitySocket.getOutputStream());
                ObjectInputStream uniInput = new ObjectInputStream(universitySocket.getInputStream())
        ) {
            System.out.println("Inoltro richiesta di login al Server Universitario...");

            // Invia la richiesta di login al server universitario
            uniOutput.writeObject(request);

            // Riceve la risposta dal server universitario
            Packet response = (Packet) uniInput.readObject();
            System.out.println("Risposta ricevuta dal Server Universitario: " + response.error);

            // Invia la risposta al client
            output.writeObject(response);
            System.out.println("Risposta inviata al client.");

        } catch (ClassNotFoundException e) {
            System.err.println("Errore durante la lettura della risposta dal Server Universitario.");
            e.printStackTrace();

            // Invia un errore al client
            Packet errorPacket = new Packet();
            errorPacket.error = new CustomError("GENERIC", "Login", "Errore durante l'inoltro della richiesta.");
            output.writeObject(errorPacket);
        } catch (IOException e) {
            System.err.println("Errore nella comunicazione con il Server Universitario.");
            e.printStackTrace();

            // Invia un errore al client
            Packet errorPacket = new Packet();
            errorPacket.error = new CustomError("NETWORK_ERROR", "Login", "Errore di rete durante l'inoltro della richiesta.");
            output.writeObject(errorPacket);
        }
    }

    private void handlePrenotaEsami(Packet request, ObjectOutputStream output) throws IOException {
        try (
                // Connessione al server universitario
                Socket universitySocket = new Socket("127.0.0.1", UniversityServer.UNI_SERVER_PORT);
                ObjectOutputStream uniOutput = new ObjectOutputStream(universitySocket.getOutputStream());
                ObjectInputStream uniInput = new ObjectInputStream(universitySocket.getInputStream())
        ) {
            System.out.println("Inoltro richiesta di login al Server Universitario...");

            // Invia la richiesta di login al server universitario
            uniOutput.writeObject(request);

            // Riceve la risposta dal server universitario
            Packet response = (Packet) uniInput.readObject();
            System.out.println("Risposta ricevuta dal Server Universitario: " + response.error);

            // Invia la risposta al client
            output.writeObject(response);
            System.out.println("Risposta inviata al client.");

        } catch (ClassNotFoundException e) {
            System.err.println("Errore durante la lettura della risposta dal Server Universitario.");
            e.printStackTrace();

            // Invia un errore al client
            Packet errorPacket = new Packet();
            errorPacket.error = new CustomError("GENERIC", "PrenotaEsame", "Errore durante l'inoltro della richiesta.");
            output.writeObject(errorPacket);
        } catch (IOException e) {
            System.err.println("Errore nella comunicazione con il Server Universitario.");
            e.printStackTrace();

            // Invia un errore al client
            Packet errorPacket = new Packet();
            errorPacket.error = new CustomError("NETWORK_ERROR", "PrenotaEsame", "Errore di rete durante l'inoltro della richiesta.");
            output.writeObject(errorPacket);
        }
    }

    private void handleInserisciEsame(Packet request, ObjectOutputStream output) throws IOException {
        try (
                // Connessione al server universitario
                Socket universitySocket = new Socket("127.0.0.1", UniversityServer.UNI_SERVER_PORT);
                ObjectOutputStream uniOutput = new ObjectOutputStream(universitySocket.getOutputStream());
                ObjectInputStream uniInput = new ObjectInputStream(universitySocket.getInputStream())
        ) {
            System.out.println("Inoltro richiesta di inserimento esame al Server Universitario...");

            // Invia la richiesta al server universitario
            uniOutput.writeObject(request);

            // Riceve la risposta dal server universitario
            Packet response = (Packet) uniInput.readObject();
            System.out.println("Risposta ricevuta dal Server Universitario: " + response.error);

            // Invia la risposta al client
            output.writeObject(response);
            System.out.println("Risposta inviata al client.");

        } catch (ClassNotFoundException e) {
            System.err.println("Errore durante la lettura della risposta dal Server Universitario.");
            e.printStackTrace();

            // Invia un errore al client
            Packet errorPacket = new Packet();
            errorPacket.error = new CustomError("GENERIC", "Inserisci Esame", "Errore durante l'inoltro della richiesta.");
            output.writeObject(errorPacket);
        } catch (IOException e) {
            System.err.println("Errore nella comunicazione con il Server Universitario.");
            e.printStackTrace();

            // Invia un errore al client
            Packet errorPacket = new Packet();
            errorPacket.error = new CustomError("NETWORK_ERROR", "Inserisci Esame", "Errore di rete durante l'inoltro della richiesta.");
            output.writeObject(errorPacket);
        }
    }


    private void handleVisualizzaEsami(Packet request, ObjectOutputStream output) throws IOException {
        try (
                // Connessione al server universitario
                Socket universitySocket = new Socket("127.0.0.1", UniversityServer.UNI_SERVER_PORT);
                ObjectOutputStream uniOutput = new ObjectOutputStream(universitySocket.getOutputStream());
                ObjectInputStream uniInput = new ObjectInputStream(universitySocket.getInputStream())
        ) {
            System.out.println("Inoltro richiesta di visualizzazione esami al Server Universitario...");

            // Invia la richiesta al server universitario
            uniOutput.writeObject(request);
            System.out.println("riuchiesta:" +request.request);

            // Riceve la risposta dal server universitario
            Packet response = (Packet) uniInput.readObject();
            System.out.println("Risposta ricevuta dal Server Universitario: " + response.error);

            // Invia la risposta al client
            output.writeObject(response);
            System.out.println("Risposta inviata al client.");

        } catch (ClassNotFoundException e) {
            System.err.println("Errore durante la lettura della risposta dal Server Universitario.");
            e.printStackTrace();

            // Invia un errore al client
            Packet errorPacket = new Packet();
            errorPacket.error = new CustomError("GENERIC", "Visualizza Esami", "Errore durante l'inoltro della richiesta.");
            output.writeObject(errorPacket);
        } catch (IOException e) {
            System.err.println("Errore nella comunicazione con il Server Universitario.");
            e.printStackTrace();

            // Invia un errore al client
            Packet errorPacket = new Packet();
            errorPacket.error = new CustomError("NETWORK_ERROR", "Visualizza Esami", "Errore di rete durante l'inoltro della richiesta.");
            output.writeObject(errorPacket);
        }
    }

    private void handleVisualizzaEsamiCorso(Packet request, ObjectOutputStream output) throws IOException {
        try (
                // Connessione al server universitario
                Socket universitySocket = new Socket("127.0.0.1", UniversityServer.UNI_SERVER_PORT);
                ObjectOutputStream uniOutput = new ObjectOutputStream(universitySocket.getOutputStream());
                ObjectInputStream uniInput = new ObjectInputStream(universitySocket.getInputStream())
        ) {
            System.out.println("Inoltro richiesta di visualizzazione esami al Server Universitario...");

            // Invia la richiesta al server universitario
            uniOutput.writeObject(request);
            System.out.println("riuchiesta:" +request.request);

            // Riceve la risposta dal server universitario
            Packet response = (Packet) uniInput.readObject();
            System.out.println("Risposta ricevuta dal Server Universitario: " + response.error);

            // Invia la risposta al client
            output.writeObject(response);
            System.out.println("Risposta inviata al client.");

        } catch (ClassNotFoundException e) {
            System.err.println("Errore durante la lettura della risposta dal Server Universitario.");
            e.printStackTrace();

            // Invia un errore al client
            Packet errorPacket = new Packet();
            errorPacket.error = new CustomError("GENERIC", "Visualizza Esami", "Errore durante l'inoltro della richiesta.");
            output.writeObject(errorPacket);
        } catch (IOException e) {
            System.err.println("Errore nella comunicazione con il Server Universitario.");
            e.printStackTrace();

            // Invia un errore al client
            Packet errorPacket = new Packet();
            errorPacket.error = new CustomError("NETWORK_ERROR", "Visualizza Esami", "Errore di rete durante l'inoltro della richiesta.");
            output.writeObject(errorPacket);
        }
    }




    private void sendError(String message, ObjectOutputStream output) throws IOException {
        Packet errorPacket = new Packet();
        errorPacket.error = new CustomError("GENERIC", "Bad request", message);
        output.writeObject(errorPacket);
    }
}*/

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
        //actions.put("INSERISCI_ESAME", new InserisciEsameAction());
        actions.put("VISUALIZZA_ESAME", new VisualizzaEsameAction());
        actions.put("VISUALIZZA_ESAME_CORSO", new VisualizzaEsameCorsoAction());
        actions.put("PRENOTA_ESAME", new PrenotaEsameAction());
    }

    @Override
    public void run() {
        try (ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream());
             ObjectOutputStream output = new ObjectOutputStream(clientSocket.getOutputStream())) {

            Packet request = (Packet) input.readObject();
            System.out.println("Received request: " + request.request);

            ISegreteriaServerAction action = actions.get(request.request);
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





