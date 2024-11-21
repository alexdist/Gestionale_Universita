package Segreteria_Command.Invoker;

import Segreteria_Command.Interfaces.ICommand;
import Segreteria_Command.Receiver.EsameManager;

import java.io.IOException;

/*public class Segreteria {

    private final ServerConnection connection;
    private ICommand command;

    public Segreteria(ServerConnection connection) {
        this.connection = connection;
    }

    // Metodo per impostare il comando specifico da eseguire.
    // Il comando viene passato dall'esterno, permettendo flessibilità.
    public void setCommand(ICommand command){
        this.command = command;
    }

    // Metodo per aggiungere un esame
    public String aggiungiEsame(Esame esame) throws IOException, ClassNotFoundException {
        Richiesta richiesta = new Richiesta("AGGIUNGI_ESAME", esame);
        connection.send(richiesta);
        Risposta risposta = (Risposta) connection.receive();
        return risposta.getMessaggio();
    }

    // Metodo per ottenere la lista degli esami
    public List<Esame> getEsami() throws IOException, ClassNotFoundException {
        Richiesta richiesta = new Richiesta("GET_ESAMI");
        connection.send(richiesta);
        Risposta risposta = (Risposta) connection.receive();
        return risposta.getDati();
    }

    // Metodo per cancellare un esame
    public String cancellaEsame(int idEsame) throws IOException, ClassNotFoundException {
        Richiesta richiesta = new Richiesta("CANCELLA_ESAME", idEsame);
        connection.send(richiesta);
        Risposta risposta = (Risposta) connection.receive();
        return risposta.getMessaggio();
    }
}*/

public class Segreteria {

    private ICommand command;
    private final EsameManager esameManager;

    public Segreteria(EsameManager esameManager){
        this.esameManager = esameManager;
    }

    // Metodo per impostare il comando
    public void setCommand(ICommand command) {
        this.command = command;
    }

    // Metodo per eseguire il comando
    public Object eseguiComando() throws IOException, ClassNotFoundException {
        if (command == null) {
            throw new IllegalStateException("Nessun comando impostato.");
        }
        return command.execute();
    }

    public EsameManager getEsameManager() {
        return esameManager;
    }
}


