package Client.Studente;

import Client.Esame;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class MenuStudente {

    private final StudenteClient client;

    private boolean autenticato = false; // Flag per il login

    public MenuStudente(StudenteClient client) {
        this.client = client;
    }

    public void mostraMenu() throws IOException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);

        // Esegui il login all'inizio
        while (!autenticato) {
            System.out.println("\nEsegui il login per accedere al sistema...\n");
            effettuaLogin();
        }

        while (true) {
            System.out.println("\n\nBenvenuto " + client.getStudente().getNome() +" " +client.getStudente().getCognome()+" nel gestionale dell'università!");
            System.out.println("1) Visualizza gli appelli disponibili");
            System.out.println("2) Visualizza gli appelli disponibili per uno specifico Corso");
            System.out.println("3) Prenota un Appello");
            System.out.println("4) Visualizza le prenotazioni");
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

                case 4:
                    visualizzaPrenotazioniStudente();
                    break;
                case 0:
                    System.out.println("Uscita...");
                    return;
                default:
                    System.out.println("Scelta non riconosciuta, riprova.");
            }
        }
    }


    private void visualizzaPrenotazioniStudente() {
        // Estrazione della matricola dallo studente
        int matricola = client.getStudente().getMatricola();

        // Chiamata al metodo per ottenere la lista di esami prenotati
        List<Esame> esamiPrenotati = client.visualizzaPrenotazioniStudente(matricola);

        // Stampa la lista di esami usando il metodo printEsami
        printPrenotazioniEsami(esamiPrenotati);

    }


    private void effettuaLogin() throws IOException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);

        while (true) {

            System.out.println("Inserisci i dati per effettuare il login:");
            //scanner.nextLine();
            System.out.print("");
            System.out.print("Nome: ");
            String nome = scanner.nextLine();
            System.out.print("Cognome: ");
            String cognome = scanner.nextLine();

            System.out.print("Matricola: ");
            int matricola = scanner.nextInt();
            scanner.nextLine();


            IStudente studente = new StudenteUniversitario(nome, cognome, matricola);

            if (client.autenticaStudente(studente)) {
                System.out.println("Login avvenuto con successo!");
                autenticato = true;
                break;
            } else {
                System.out.println("Login fallito. Riprova.\n");

            }
        }
    }


    private void visualizzaEsami() {
        try {
            List<Esame> esami = client.visualizzaEsami();
            printEsami(esami);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Errore durante il caricamento degli appelli: " + e.getMessage());
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
            System.err.println("Errore durante il caricamento degli appelli per il corso: " + e.getMessage());
        }
    }

    private void prenotaEsame() throws IOException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Inserisci il codice dell'appello a cui vuoi prenotarti:");
        long codiceEsame = scanner.nextLong();
        try {
             client.prenotaAppello(codiceEsame);
            //printEsami(esami);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Errore durante la prenotazione dell'appello " + e.getMessage());
        }

    }

    private void printEsami(List<Esame> esami) {
        if (esami == null || esami.isEmpty()) {
            System.out.println("Nessun appello disponibile.");
            return;
        }

        System.out.println("\nAppelli disponibili:\n");
        for (Esame esame : esami) {
            System.out.println("Codice appello: " + esame.getCodiceEsame());
            System.out.println("Attività Didattica: " + esame.getAttivitaDidattica());
            System.out.println("Data: " + esame.getDataAppello());
            System.out.println("Descrizione: " + esame.getDescrizione());
            System.out.println("Presidente: " + esame.getPresidente());
            System.out.println();
        }
    }

    private void printPrenotazioniEsami(List<Esame> esami) {
        if (esami == null || esami.isEmpty()) {
            System.out.println("Nessuna prenotazione disponibile.");
            return;
        }

        System.out.println("\nPrenotazioni:\n");
        for (Esame esame : esami) {
            System.out.println("Codice appello: " + esame.getCodiceEsame());
            System.out.println("Attività Didattica: " + esame.getAttivitaDidattica());
            System.out.println("Data: " + esame.getDataAppello());
            System.out.println("Descrizione: " + esame.getDescrizione());
            System.out.println("Presidente: " + esame.getPresidente());
            System.out.println();
        }
    }
}
