package Server.ServerUniversita.ServerUAction;

import Client.Studente.IStudente;
import Client.Studente.StudenteUniversitario;
import Pacchetto.*;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class LoginServerAction implements IServerAction {
    @Override
    public void execute(Packet packet, ObjectOutputStream output) throws IOException {
        System.out.println("Tentativo di login ricevuto.");

        IStudente studente = (StudenteUniversitario) packet.data;
        String nome = studente.getNome();
        String cognome = studente.getCognome();
        int matricola = studente.getMatricola();

        List<String> databaseUtenti = new ArrayList<>();
        databaseUtenti.add("Mario Rossi");
        databaseUtenti.add("Luigi Bianchi");
        databaseUtenti.add("Maria Verdi");

        boolean autenticato = databaseUtenti.contains(nome + " " + cognome);

        Packet response = new Packet();

        if (autenticato) {
            System.out.println("Autenticazione riuscita per l'utente: " + cognome +" " + nome + " matricola: "+ matricola);
            response.info = new CustomInfo("OK", "LOGIN", "Autenticazione riuscita.");

        } else {
            System.err.println("Autenticazione fallita per l'utente: " + cognome);
            response.info = new CustomInfo("AUTH_ERROR", "LOGIN", "Credenziali non valide.");
        }

        output.writeObject(response);
    }
}
