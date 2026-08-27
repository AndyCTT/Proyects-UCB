import java.io.IOException;
 
public class CuentaInversion extends CuentaAhorros {
 
    private String tipoInversion;
    private int plazoMeses;
    private double montoMinimo;
 
    public CuentaInversion(int numeroCuenta, String titular, double saldoInicial,
    double tasaInteres, String tipoInversion, int plazoMeses, double montoMinimo) {
        super(numeroCuenta, titular, saldoInicial, tasaInteres);
        this.tipoInversion = tipoInversion;
        this.plazoMeses = plazoMeses;
        this.montoMinimo = montoMinimo;
    }
 
    public String getTipoInversion() {
        return tipoInversion;
    }
 
    public void setTipoInversion(String tipoInversion) {
        this.tipoInversion = tipoInversion;
    }
 
    public int getPlazoMeses() {
        return plazoMeses;
    }
 
    public void setPlazoMeses(int plazoMeses) {
        this.plazoMeses = plazoMeses;
    }
 
    public double getMontoMinimo() {
        return montoMinimo;
    }
 
    public void setMontoMinimo(double montoMinimo) {
        this.montoMinimo = montoMinimo;
    }
 
    @Override
    public boolean validarRetiro(double monto) {
        if (getSaldo() - monto < montoMinimo) {
            System.out.println("No puede retirar: el saldo quedaria bajo el minimo de "
            + montoMinimo);
            return false;
        }
        return super.validarRetiro(monto);
    }
 
    @Override
    public void aplicarInteres() throws IOException {
        double interes = getSaldo() * getTasaInteres() / 100 * plazoMeses / 12;
        double saldoAnterior = getSaldo();
        setSaldo(getSaldo() + interes);
        Movimiento mov = new Movimiento("Rendimiento inversion", getNumeroCuenta(),
        saldoAnterior, interes, getSaldo());
        getMovimientos().add(mov);
        Movimiento.agregarMovimiento(mov);
        System.out.println("Rendimiento aplicado: " + interes + ". Nuevo saldo: " + getSaldo());
    }
 
    @Override
    public String getTipoCuenta() {
        return "Cuenta Inversion (" + tipoInversion + ")";
    }
 
    @Override
    protected String datosExtra() {
        return getTasaInteres() + "|" + plazoMeses + "|" + montoMinimo + "|" + tipoInversion;
    }
 
    @Override
    public String toString() {
        return getNumeroCuenta() + " / " + getFechaApertura() + " " + getTitular() + " "
        + getSaldo() + " " + getTipoCuenta() + " | Tasa: " + getTasaInteres()
        + "% | Plazo: " + plazoMeses + " meses | Min: " + montoMinimo;
    }
}