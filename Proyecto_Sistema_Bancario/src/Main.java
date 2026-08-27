import javafx.application.Application;
import java.io.IOException;
 
public class Main {
 
    public static void main(String[] args) {
        Banco banco = new Banco("Banco PROYECTO", "BP001");
 
        try {
            banco.cargarDatos();
        } catch (IOException e) {
            System.out.println("Iniciando banco sin datos previos. " + e.getMessage());
        }
 
        BancoSistema.setBanco(banco);
        Application.launch(BancoSistema.class, args);
    }
}
 