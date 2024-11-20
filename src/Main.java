import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {


        Esame esame = new Esame("Matematica", LocalDateTime.now(), "Esame finale", "Prof. Rossi");
        System.out.println(esame);

    }
}