//SINGLETON

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

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
            System.out.println("Esame cancellato!");
        }
        else
            System.out.println("Esame non trovato!");
    }

/*
    // Esempio di un metodo per gestire richieste
    public void gestisciRichiesta(String richiesta) {
        System.out.println("Gestione richiesta: " + richiesta);
    }

    // Esempio di metodo per chiudere il server
    public void chiudiServer() {
        System.out.println("Server Universitario Chiuso!");
    }*/
}

