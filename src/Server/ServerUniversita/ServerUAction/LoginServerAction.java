package Server.ServerUniversita.ServerUAction;

import Client.Studente.IStudente;
import Client.Studente.StudenteUniversitario;
import Pacchetto.CustomError;
import Pacchetto.Packet;

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

        List<String> databaseUtenti = new ArrayList<>();
        databaseUtenti.add("Mario Rossi");
        databaseUtenti.add("Luigi Bianchi");
        databaseUtenti.add("Maria Verdi");

        boolean autenticato = databaseUtenti.contains(nome + " " + cognome);

        Packet response = new Packet();
        if (autenticato) {
            System.out.println("Autenticazione riuscita per l'utente: " + cognome);
            response.error = new CustomError("OK", "", "Autenticazione riuscita.");
        } else {
            System.out.println("Autenticazione fallita per l'utente: " + cognome);
            response.error = new CustomError("AUTH_ERROR", "Login", "Credenziali non valide.");
        }

        output.writeObject(response);
    }
}
