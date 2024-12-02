package Server;

import Pacchetto.CustomInfo;
import Pacchetto.Packet;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class HandleError {
    public static void handleError(ObjectOutputStream output, String errorCode, String context, String message) throws IOException {
        Packet errorPacket = new Packet();
        errorPacket.info = new CustomInfo(errorCode, context, message);
        output.writeObject(errorPacket);
    }
}
