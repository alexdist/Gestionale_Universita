package Server.ServerUniversita;

import Client.Esame;
import Pacchetto.CustomError;
import Pacchetto.Packet;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

public class EliminaEsameServerAction implements IServerAction{

     public void execute(Packet packet, ObjectOutputStream output) throws IOException {

         UniversityServer server = UniversityServer.getInstance();
         List<Esame> esamiList = server.getEsamiList();

         long codiceEsame = (long) packet.data;

         // Ricerca dell'esame
         Esame esameTrovato = null;
         for (Esame esame : esamiList) {
             if (esame.getCodiceEsame() == codiceEsame) {
                 esameTrovato = esame;
                 break;
             }
         }

         Packet response = new Packet();
         // response.request = "ELIMINA_ESAME";

         if (esameTrovato == null) {
             // Client.Esame non trovato
             System.out.println("Esame con codice " + codiceEsame + " non trovato.");
             response.error = new CustomError("NOT_FOUND", "EliminaEsame", "Esame non trovato.");
             response.data = null;
         }else if (esameTrovato.getNumeroPrenotazione() >= 1){
             System.out.println("Esame con codice " + codiceEsame + " non puo' essere eliminato poiché ci sono studenti prenotati.");
             response.error = new CustomError("NOT_DELETE", "EliminaEsame", "Esame non puo essere eliminato.");
         }

             else {
             // Esame trovato e rimosso
             esamiList.remove(esameTrovato);
             System.out.println("Esame di " + esameTrovato.getAttivitaDidattica() + " con codice " + codiceEsame + " eliminato con successo!");
             response.error = new CustomError("OK", "", "Esame eliminato con successo.");
             response.data = null;
         }

         // Invio della risposta al client
         output.writeObject(response);
     }

}