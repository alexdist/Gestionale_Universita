package Test;

import Client.Studente.MenuStudente;
import Client.Studente.StudenteClient;

import java.io.IOException;

public class Main2 {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        StudenteClient client = new StudenteClient("127.0.0.1", 12345);
        MenuStudente menu = new MenuStudente(client);
        menu.mostraMenu();
    }

    /*public static void main(String[] args) {
        Client.Studente.IStudente studente = new Client.Studente.StudenteUniversitario("Mario", "Rossi");
        Client.Studente.StudenteClient client = new Client.Studente.StudenteClient("127.0.0.1", 54321, studente);

        try {
            boolean autenticato = client.autenticaStudente();
            if (autenticato) {
                System.out.println("Accesso consentito!");
            } else {
                System.out.println("Accesso negato.");
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }*/


    /*public static void main(String[] args) throws IOException {
        Client.Segreteria.SegreteriaClient client = new Client.Segreteria.SegreteriaClient("127.0.0.1", 12345);
        Client.Segreteria.SegreteriaMenu menu = new Client.Segreteria.SegreteriaMenu(client);
        menu.avvia();
    }*/



   /* public static void main(String[] args) throws IOException, ClassNotFoundException {
        // Crea un oggetto Studente per il test
        Client.Studente.IStudente studente = new Client.Studente.StudenteUniversitario("Mario", "Rossi", 67890);

        // Crea il client studente
        Client.Studente.StudenteClient studenteClient = new Client.Studente.StudenteClient("127.0.0.1", 54321, studente);

        // Menu dello studente per interagire con il sistema
        Client.Studente.MenuStudente menu = new Client.Studente.MenuStudente(studenteClient);

        // Test: Aggiunta di esami sul server universitario


        // Test: Pacchetto.Prenotazione esame
        menu.mostraMenu();
    }*/
}
