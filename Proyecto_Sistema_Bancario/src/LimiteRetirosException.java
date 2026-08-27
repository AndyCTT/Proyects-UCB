
public class LimiteRetirosException extends Exception {
 
    public LimiteRetirosException(int limite) {
        super("Se ha alcanzado el limite de " + limite + " retiros mensuales.");
    }
}
 