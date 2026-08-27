import java.io.IOException;
 
public class CuentaAhorros extends CuentaCorriente {
 
    private double tasaInteres;
    private int retirosMensuales;
    private static final int LIMITE_RETIROS = 5;
 
    public CuentaAhorros(int numeroCuenta, String titular, double saldoInicial,
    double tasaInteres) {
        super(numeroCuenta, titular, saldoInicial, 0, 0);
        this.tasaInteres = tasaInteres;
        this.retirosMensuales = 0;
    }
 
    public double getTasaInteres() {
        return tasaInteres;
    }
 
    public void setTasaInteres(double tasaInteres) {
        this.tasaInteres = tasaInteres;
    }
 
    public int getRetirosMensuales() {
        return retirosMensuales;
    }
 
    public void setRetirosMensuales(int retirosMensuales) {
        this.retirosMensuales = retirosMensuales;
    }
 
    public void reiniciarRetirosMensuales() {
        this.retirosMensuales = 0;
        System.out.println("Retiros mensuales reiniciados en cuenta " + getNumeroCuenta());
    }
 
    @Override
    public boolean validarRetiro(double monto) {
        if (retirosMensuales >= LIMITE_RETIROS) {
            System.out.println("Limite de " + LIMITE_RETIROS
            + " retiros mensuales alcanzado.");
            return false;
        }
        if (monto > getSaldo()) {
            System.out.println("Saldo insuficiente. Saldo actual: " + getSaldo());
            return false;
        }
        retirosMensuales++;
        return true;
    }
 
    @Override
    public void aplicarInteres() throws IOException {
        double interes = getSaldo() * tasaInteres / 100;
        double saldoAnterior = getSaldo();
        setSaldo(getSaldo() + interes);
        Movimiento mov = new Movimiento("Interes", getNumeroCuenta(),
        saldoAnterior, interes, getSaldo());
        getMovimientos().add(mov);
        Movimiento.agregarMovimiento(mov);
        System.out.println("Interes aplicado: " + interes + ". Nuevo saldo: " + getSaldo());
    }
 
    @Override
    public String getTipoCuenta() {
        return "Cuenta de Ahorros";
    }
 
    @Override
    protected String datosExtra() {
        return tasaInteres + "|" + retirosMensuales;
    }
 
    @Override
    public String toString() {
        return getNumeroCuenta() + " / " + getFechaApertura() + " " + getTitular() + " "
        + getSaldo() + " " + getTipoCuenta() + " | Tasa: " + tasaInteres
        + "% | Retiros mes: " + retirosMensuales;
    }
}