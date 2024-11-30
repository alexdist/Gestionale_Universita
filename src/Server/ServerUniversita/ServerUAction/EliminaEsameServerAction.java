package Server.ServerUniversita.ServerUAction;

import Client.Esame;
//import Pacchetto.CustomError;
import Pacchetto.CustomInfo;
import Pacchetto.Packet;
import Server.ServerUniversita.UniversityServer;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;

public class EliminaEsameServerAction implements IServerAction {

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
             System.err.println("Appello con codice " + codiceEsame + " non trovato.");
             response.info = new CustomInfo("NOT_FOUND", "ELIMINAESAME", "Appello non trovato.");
             response.data = null;
         }else if (esameTrovato.getNumeroPrenotazione() >= 1){
             System.err.println("Appello con codice " + codiceEsame + " non puo' essere eliminato poiché ci sono studenti prenotati.");
             response.info = new CustomInfo("NOT_DELETE", "ELIMINAESAME", "Appello non puo essere eliminato con studenti prenotati.");
         }

             else {
             // Esame trovato e rimosso
             //esamiList.remove(esameTrovato);
             server.rimuoviEsame(esameTrovato);
             System.out.println("Appello di " + esameTrovato.getAttivitaDidattica() + " con codice " + codiceEsame + " eliminato con successo!");
             response.info = new CustomInfo("OK", "ELIMINAESAME", "Appello eliminato con successo.");
             response.data = null;
         }

         // Invio della risposta al client
         output.writeObject(response);
     }

}
