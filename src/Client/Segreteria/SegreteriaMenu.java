package Client.Segreteria;

import Client.Esame;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
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
            System.out.println("\nBenvenuto nel gestionale dell'università");
            System.out.println("Seleziona cosa vuoi fare:");
            System.out.println("1) Aggiungi un nuovo appello");
            System.out.println("2) Elimina un appello");
            System.out.println("0) Esci");
            System.out.print("Scelta: ");

            try {
                scelta = scanner.nextInt();
                scanner.nextLine(); // Pulizia del buffer
                System.out.println();

                switch (scelta) {
                    case 1:
                        creaEsame(scanner);
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
            } catch (InputMismatchException e) {
                System.out.println("Errore: Inserisci un numero valido.");
                scanner.nextLine(); // Pulisce l'input errato
                scelta = -1; // Reset scelta per rimanere nel loop
            }
        } while (scelta != 0);

        scanner.close();
    }

    private void eliminaEsame(Scanner scanner) throws IOException {
        long codice = -1;
        while (codice < 0) {
            try {
                System.out.println("Inserisci il codice dell'appello (numero intero positivo): ");
                codice = scanner.nextLong();
                if (codice < 0) {
                    System.out.println("Errore: Il codice deve essere un numero intero positivo.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Errore: Inserisci un numero intero valido.");
                scanner.nextLine(); // Pulisce l'input errato
            }
        }
        client.eliminaEsame(codice);
    }

    private void creaEsame(Scanner scanner) {
        try {

            long codiceEsame = -1;

            while (codiceEsame < 0) {
                try {
                    System.out.println("Inserisci il codice dell'appello (numero intero): ");
                    codiceEsame = scanner.nextLong();
                    scanner.nextLine(); // Pulizia del buffer
                    if (codiceEsame < 0) {
                        System.out.println("Errore: Il codice deve essere un numero intero positivo. Riprova.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Errore: Inserisci un numero intero valido.");
                    scanner.nextLine(); // Pulizia del buffer per eliminare l'input errato
                }
            }
            String attivitaDidattica = null;
            while (attivitaDidattica == null || attivitaDidattica.trim().isEmpty() || attivitaDidattica.matches("\\d+")) {
                try {
                    System.out.println("Inserisci l'attività didattica (solo testo, non vuota):");
                    attivitaDidattica = scanner.nextLine().trim();

                    if (attivitaDidattica.isEmpty()) {
                        System.out.println("Errore: L'attività didattica non può essere vuota.");
                        attivitaDidattica = null; // Resetta per continuare il loop
                    } else if (attivitaDidattica.matches("\\d+")) {
                        System.out.println("Errore: L'attività didattica non può essere un numero.");
                        attivitaDidattica = null; // Resetta per continuare il loop
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Errore: Inserisci una stringa valida.");
                    scanner.nextLine(); // Pulizia del buffer in caso di errore
                }
            }

            System.out.println("Inserisci la descrizione (default: vuota):");
            String descrizione = scanner.nextLine();

            LocalDate data = null;
            while (data == null) {
                try {
                    System.out.println("Inserisci la data (formato: yyyy-MM-dd):");
                    data = LocalDate.parse(scanner.nextLine());
                } catch (DateTimeParseException e) {
                    System.out.println("Errore: Formato della data non valido. Usa il formato yyyy-MM-dd.");
                }
            }

            LocalTime orario = null;
            while (orario == null) {
                try {
                    System.out.println("Inserisci l'orario (formato: HH:mm):");
                    orario = LocalTime.parse(scanner.nextLine());
                } catch (DateTimeParseException e) {
                    System.out.println("Errore: Formato dell'orario non valido. Usa il formato HH:mm.");
                }
            }

            // Combina data e orario in LocalDateTime
            LocalDateTime dataAppello = LocalDateTime.of(data, orario);

            System.out.println("Inserisci il nome del Professore:");
            String nomeProfessore = scanner.nextLine();

            int numeroMassimoPrenotati = -1;
            while (numeroMassimoPrenotati <= 0) {
                try {
                    System.out.println("Inserisci il numero massimo di prenotati (numero intero positivo):");
                    numeroMassimoPrenotati = scanner.nextInt();
                    scanner.nextLine(); // Pulizia del buffer
                    if (numeroMassimoPrenotati <= 0) {
                        System.out.println("Errore: Il numero deve essere maggiore di zero.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Errore: Inserisci un numero intero valido.");
                    scanner.nextLine(); // Pulisce l'input errato
                }
            }

            if (descrizione.isEmpty()) {
                descrizione = "Nessuna descrizione";
            }

            // Crea l'oggetto Client.Esame
            Esame esame = new Esame(attivitaDidattica, dataAppello, descrizione, nomeProfessore, codiceEsame, numeroMassimoPrenotati);

            // Invia l'esame al client
            client.aggiungiEsame(esame);

        } catch (InputMismatchException e) {
            System.out.println("Errore: Input non valido. Operazione annullata.");
            scanner.nextLine(); // Pulisce l'input errato
        }
    }
}

