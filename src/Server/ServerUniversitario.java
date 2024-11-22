package Server;//SINGLETON

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import Esame.Esame;

public class ServerUniversitario {

    // Istanza unica del Server (inizializzata solo quando necessario)
    private static ServerUniversitario instance;

    private List<Esame> esames = new CopyOnWriteArrayList<>();

    // Costruttore privato per impedire l'instanziazione esterna
    private ServerUniversitario() {
        System.out.println("Server Universitario Avviato!");
    }

    // Metodo pubblico per ottenere l'istanza con sincronizzazione
    public static synchronized ServerUniversitario getInstance() {
        if (instance == null) {
            instance = new ServerUniversitario();
        }
        return instance;
    }

    public void aggiungiEsame(Esame esame){
        esames.add(esame);
    }

    public List<Esame> getEsames(){
        return Collections.unmodifiableList(esames);
    }

    private boolean esisteEsamePerId(int id){
        return esames.stream().anyMatch(esame -> esame.getIdEsame() == id);
    }

    public void cancellaEsame(int id){
        boolean esisteEsame = esisteEsamePerId(id);
        if(esisteEsame){
            esames.removeIf(esame -> esame.getIdEsame() == id);
            System.out.println("Esame.Esame cancellato!");
        }
        else
            System.out.println("Esame.Esame non trovato!");
    }

    public List<Esame> getEsamiPerCorso(String nome){
        return esames.stream().filter(esame -> esame.getNomeCorso().equals(nome)).collect(Collectors.toList());
    }

    public synchronized String prenotaEsame(int idEsame) {
        // Controlla se l'esame esiste
        if (!esisteEsamePerId(idEsame)) {
            return "Errore: Esame non trovato.";
        }

        // Trova l'esame e tenta di prenotarlo
        for (Esame esame : esames) {
            if (esame.getIdEsame() == idEsame) {
                try {
                    esame.prenota();
                    return "Prenotazione effettuata con successo! Prenotazione n° " + esame.getNumeroPrenotazione();
                } catch (IllegalStateException e) {
                    return "Errore: " + e.getMessage();
                }
            }
        }

        return "Errore sconosciuto durante la prenotazione.";
    }



    // Esempio di un metodo per gestire richieste
    public void gestisciRichiesta(String richiesta) {
        System.out.println("Gestione richiesta: " + richiesta);
    }

    // Esempio di metodo per chiudere il server
    public void chiudiServer() {
        System.out.println("Server Universitario Chiuso!");
    }
}

