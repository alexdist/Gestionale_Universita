package Client.Segreteria;

import Client.Esame;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Scanner;

public class SegreteriaMenu {
    private final SegreteriaClient client;

    public SegreteriaMenu(SegreteriaClient client) {
        this.client = client;
    }

    public void avvia() throws IOException {
        Scanner scanner = new Scanner(System.in);
        int scelta;

        do {
            System.out.println("\n\nBenvenuto nel gestionale dell'università");
            System.out.println("Seleziona cosa vuoi fare:");
            System.out.println("1) Aggiungi un nuovo esame");
            System.out.println("2) Elimina un esame");

            System.out.println("0) Esci");

            scelta = scanner.nextInt();
            scanner.nextLine(); // Pulizia del buffer
            System.out.println();

            switch (scelta) {
                case 1:
                    creaEsame(scanner); // Chiamata al metodo rinominato
                    break;
                case 2:
                    eliminaEsame(scanner);
                    break;
                case 0:
                    System.out.println("Uscita...");
                    break;
                default:
                    System.out.println("Scelta non riconosciuta, riprova.");
            }
            System.out.println();
        } while (scelta != 0);

        scanner.close();
    }

    private void eliminaEsame(Scanner scanner) throws IOException {
        System.out.println("Inserisci il codice dell'esame: ");
        long codice = scanner.nextLong();
        client.eliminaEsame(codice);
    }

    private void creaEsame(Scanner scanner) {
        System.out.println("Inserisci il codice dell'esame:");
        long codiceEsame = scanner.nextLong();
        System.out.println("Inserisci l'attività didattica:");
        scanner.nextLine(); // Pulizia buffer
        String attivitaDidattica = scanner.nextLine();

        System.out.println("Inserisci la descrizione (default: vuota):");
        String descrizione = scanner.nextLine();

        System.out.println("Inserisci la data di appello (formato: yyyy-MM-ddTHH:mm):");
        LocalDateTime dataAppello = LocalDateTime.parse(scanner.nextLine());

        System.out.println("Inserisci il nome del Professore:");
        String nomeProfessore = scanner.nextLine();

        System.out.println("Inserisci il numero massimo di prenotati:");
        int numeroMassimoPrenotati = scanner.nextInt();
        scanner.nextLine(); // Pulizia del buffer

        if (descrizione.isEmpty()) {
            descrizione = "Nessuna descrizione";
        }

        // Crea l'oggetto Client.Esame
        Esame esame = new Esame(attivitaDidattica, dataAppello, descrizione, nomeProfessore, codiceEsame, numeroMassimoPrenotati);

        // Invia l'esame al client
        client.aggiungiEsame(esame);
    }
}