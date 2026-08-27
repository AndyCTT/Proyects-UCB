
public class CuentaBloqueadaException extends Exception {
 
    public CuentaBloqueadaException(int numeroCuenta) {
        super("La cuenta " + numeroCuenta + " esta bloqueada y no puede operar.");
    }
}