package Test;

import Client.Studente.MenuStudente;
import Client.Studente.StudenteClient;

import java.io.IOException;

public class Studente3 {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        StudenteClient client = new StudenteClient("127.0.0.1", 54321);
        MenuStudente menu = new MenuStudente(client);
        menu.mostraMenu();
    }
}