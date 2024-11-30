package Server.Segreteria.Action;

//import Pacchetto.CustomError;
import Pacchetto.CustomInfo;
import Pacchetto.Packet;
import Server.ServerUniversita.UniversityServer;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class LoginAction implements ISegreteriaServerAction {

    @Override
    public void execute(Packet request, ObjectOutputStream output) throws IOException {
        try (
                Socket universitySocket = new Socket("127.0.0.1", UniversityServer.UNI_SERVER_PORT);
                ObjectOutputStream uniOutput = new ObjectOutputStream(universitySocket.getOutputStream());
                ObjectInputStream uniInput = new ObjectInputStream(universitySocket.getInputStream())
        ) {
            System.out.println("Inoltro richiesta di login al Server Universitario...");

            uniOutput.writeObject(request);

            Packet response = (Packet) uniInput.readObject();
            System.out.println("Risposta ricevuta dal Server Universitario: " + response.info);

            output.writeObject(response);
            System.out.println("Risposta inviata al client.");
        } catch (ClassNotFoundException e) {
            handleError(output, "GENERIC", "Login", "Errore durante l'inoltro della richiesta.");
        } catch (IOException e) {
            handleError(output, "NETWORK_ERROR", "Login", "Errore di rete durante l'inoltro della richiesta.");
        }
    }

    private void handleError(ObjectOutputStream output, String errorCode, String context, String message) throws IOException {
        Packet errorPacket = new Packet();
        errorPacket.info = new CustomInfo(errorCode, context, message);
        output.writeObject(errorPacket);
    }
}

