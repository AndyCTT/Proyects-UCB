import java.io.IOException;
 
public class CuentaCorriente extends CuentaBancaria {
 
    private double limitesobregiro;
    private double comisionMensual;
 
    public CuentaCorriente(int numeroCuenta, String titular, double saldoInicial,
    double limitesobregiro, double comisionMensual) {
        super(numeroCuenta, titular, saldoInicial);
        this.limitesobregiro = limitesobregiro;
        this.comisionMensual = comisionMensual;
    }
 
    public double getLimitesobregiro() {
        return limitesobregiro;
    }
 
    public void setLimitesobregiro(double limitesobregiro) {
        this.limitesobregiro = limitesobregiro;
    }
 
    public double getComisionMensual() {
        return comisionMensual;
    }
 
    public void setComisionMensual(double comisionMensual) {
        this.comisionMensual = comisionMensual;
    }
 
    @Override
    public boolean validarRetiro(double monto) {
        if (monto > getSaldo() + limitesobregiro) {
            System.out.println("Supera el limite de sobregiro. Disponible: "
            + (getSaldo() + limitesobregiro));
            return false;
        }
        return true;
    }
 
    @Override
    public void aplicarInteres() throws IOException {
        double saldoAnterior = getSaldo();
        setSaldo(getSaldo() - comisionMensual);
        Movimiento mov = new Movimiento("Comision mensual", getNumeroCuenta(),
        saldoAnterior, comisionMensual, getSaldo());
        getMovimientos().add(mov);
        Movimiento.agregarMovimiento(mov);
        System.out.println("Comision mensual descontada: " + comisionMensual
        + ". Nuevo saldo: " + getSaldo());
    }
 
    @Override
    public String getTipoCuenta() {
        return "Cuenta Corriente";
    }
 
    @Override
    protected String datosExtra() {
        return limitesobregiro + "|" + comisionMensual;
    }
 
    @Override
    public String toString() {
        return super.toString() + " | Sobregiro: " + limitesobregiro
        + " | Comision: " + comisionMensual;
    }
}