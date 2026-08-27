
public class UsuarioNoEncontradoException extends Exception {
 
    public UsuarioNoEncontradoException(String identificacion) {
        super("No se encontro ningun usuario con identificacion: " + identificacion);
    }
}