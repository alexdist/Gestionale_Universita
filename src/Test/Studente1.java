package Test;

import Client.Studente.MenuStudente;
import Client.Studente.StudenteClient;
import Server.Segreteria.SegreteriaServer;

import java.io.IOException;

public class Studente1 {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        StudenteClient client = new StudenteClient("127.0.0.1", SegreteriaServer.SEGRETERIA_SERVER_PORT);
        MenuStudente menu = new MenuStudente(client);
        menu.mostraMenu();
    }
}
