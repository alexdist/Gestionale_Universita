package Test;

import Client.Segreteria.SegreteriaClient;
import Client.Segreteria.SegreteriaMenu;
import Server.ServerUniversita.UniversityServer;

import java.io.IOException;

public class Segreteria {
    public static void main(String[] args) throws IOException {
        SegreteriaClient client = new SegreteriaClient("127.0.0.1", UniversityServer.UNI_SERVER_PORT);
        SegreteriaMenu menu = new SegreteriaMenu(client);
        menu.avvia();
    }
}
