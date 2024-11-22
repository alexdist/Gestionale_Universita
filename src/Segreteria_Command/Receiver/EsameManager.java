package Segreteria_Command.Receiver;

import Segreteria_Command.Richiesta;
import Segreteria_Command.ServerConnection;
import Server.Risposta;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import Esame.Esame;

public class EsameManager implements Serializable {

    private final ServerConnection connection;
   // private Esame esame;

    public EsameManager(ServerConnection connection) {
        this.connection = connection;
    }

    public String aggiungiEsame(Esame esame) throws IOException, ClassNotFoundException {
        Richiesta richiesta = new Richiesta("AGGIUNGI_ESAME", esame);
        connection.send(richiesta);
        Risposta risposta = (Risposta) connection.receive();
        return risposta.getMessaggio();
    }

    public List<Esame> getEsami() throws IOException, ClassNotFoundException {
        Richiesta richiesta = new Richiesta("GET_ESAMI");
        connection.send(richiesta);
        Risposta risposta = (Risposta) connection.receive();
        return risposta.getDati();
    }

    public String cancellaEsame(int idEsame) throws IOException, ClassNotFoundException {
        Richiesta richiesta = new Richiesta("CANCELLA_ESAME", idEsame);
        connection.send(richiesta);
        Risposta risposta = (Risposta) connection.receive();
        return risposta.getMessaggio();
    }

    public List<Esame> getEsamiPerCorso(String corso) throws IOException, ClassNotFoundException {
        Richiesta richiesta = new Richiesta("GET_ESAMI_PER_CORSO", corso);
        connection.send(richiesta);
        Risposta risposta = (Risposta) connection.receive();
        return risposta.getDati();
    }

    public String prenotaEsame(int idEsame) throws IOException, ClassNotFoundException {
        Richiesta richiesta = new Richiesta("PRENOTA_ESAME", idEsame);
        connection.send(richiesta);
        Risposta risposta = (Risposta) connection.receive();
        return risposta.getMessaggio();
    }
}
