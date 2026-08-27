
public class SaldoInsuficienteException extends Exception {
 
    public SaldoInsuficienteException(double saldoActual, double montoSolicitado) {
        super("Saldo insuficiente. Saldo actual: " + saldoActual
        + " | Monto solicitado: " + montoSolicitado);
    }
}