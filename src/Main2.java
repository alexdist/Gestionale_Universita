import java.io.IOException;

public class Main2 {
    /* public static void main(String[] args) {
        StudenteClient client = new StudenteClient("127.0.0.1", 12345);
        MenuStudente menu = new MenuStudente(client);
        menu.mostraMenu();
    }*/

    /*public static void main(String[] args) {
        IStudente studente = new StudenteUniversitario("Mario", "Rossi");
        StudenteClient client = new StudenteClient("127.0.0.1", 54321, studente);

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


   /* public static void main(String[] args) {
        SegreteriaClient client = new SegreteriaClient("127.0.0.1", 12345);
        SegreteriaMenu menu = new SegreteriaMenu(client);
        menu.avvia();
    }*/

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // Crea un oggetto Studente per il test
        IStudente studente = new StudenteUniversitario("Mario", "Rossi", 67890);

        // Crea il client studente
        StudenteClient studenteClient = new StudenteClient("127.0.0.1", 54321, studente);

        // Menu dello studente per interagire con il sistema
        MenuStudente menu = new MenuStudente(studenteClient);

        // Test: Aggiunta di esami sul server universitario


        // Test: Prenotazione esame
        menu.mostraMenu();
    }
}
