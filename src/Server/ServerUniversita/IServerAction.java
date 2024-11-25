package Server.ServerUniversita;

import Pacchetto.Packet;

import java.io.IOException;
import java.io.ObjectOutputStream;

public interface IServerAction {
    void execute(Packet packet, ObjectOutputStream output) throws IOException;
}
