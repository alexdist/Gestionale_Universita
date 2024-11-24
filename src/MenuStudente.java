import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class MenuStudente {

    private final StudenteClient client;

    public MenuStudente(StudenteClient client) {
        this.client = client;
    }

    public void mostraMenu() throws IOException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Benvenuto nel gestionale dell'università!");
            System.out.println("1) Visualizza gli esami disponibili");
            System.out.println("2) Visualizza gli esami disponibili per uno specifico Corso");
            System.out.println("3) Prenota un Esame");
            System.out.println("0) Esci");
            System.out.print("Scelta: ");
            int scelta = scanner.nextInt();

            switch (scelta) {
                case 1:
                    visualizzaEsami();
                    break;
                case 2:
                    visualizzaEsamiCorso();
                    break;

                    case 3:
                    prenotaEsame();
                    break;
                case 0:
                    System.out.println("Uscita...");
                    return;
                default:
                    System.out.println("Scelta non riconosciuta, riprova.");
            }
        }
    }

    private void visualizzaEsami() {
        try {
            List<Esame> esami = client.visualizzaEsami();
            printEsami(esami);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Errore durante il caricamento degli esami: " + e.getMessage());
        }
    }

    private void visualizzaEsamiCorso() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Inserisci il nome del corso: ");
        String corso = scanner.nextLine();

        try {
            List<Esame> esami = client.visualizzaEsamiCorso(corso);
            printEsami(esami);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Errore durante il caricamento degli esami per il corso: " + e.getMessage());
        }
    }

    private void prenotaEsame() throws IOException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Inserisci il codice dell'esame a cui vuoi prenotarti:");
        long codiceEsame = scanner.nextLong();
        try {
             client.prenotaAppello(codiceEsame);
            //printEsami(esami);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Errore durante la prenotazione dell'esame " + e.getMessage());
        }

    }

    private void printEsami(List<Esame> esami) {
        if (esami == null || esami.isEmpty()) {
            System.out.println("Nessun esame disponibile.");
            return;
        }

        System.out.println("Esami disponibili:");
        for (Esame esame : esami) {
            System.out.println("Codice esame: " + esame.getCodiceEsame());
            System.out.println("Attività Didattica: " + esame.getAttivitaDidattica());
            System.out.println("Data: " + esame.getDataAppello());
            System.out.println("Descrizione: " + esame.getDescrizione());
            System.out.println("Presidente: " + esame.getPresidente());
            System.out.println();
        }
    }
}
