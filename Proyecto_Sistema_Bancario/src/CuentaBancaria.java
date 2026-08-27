import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
 
public abstract class CuentaBancaria {
 
    private int numeroCuenta;
    private String titular;
    private LocalDate fechaApertura;
    private double saldo;
    private boolean bloqueada;
    private ArrayList<Movimiento> movimientos;
 
    private static final String ARCHIVO = "cuentas.txt";
 
    public CuentaBancaria(int numeroCuenta, String titular, double saldoInicial) {
        this.numeroCuenta = numeroCuenta;
        this.titular = titular;
        this.fechaApertura = LocalDate.now();
        this.saldo = saldoInicial;
        this.bloqueada = false;
        this.movimientos = new ArrayList<>();
    }
 
    public int getNumeroCuenta() {
        return numeroCuenta;
    }
 
    public void setNumeroCuenta(int numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }
 
    public String getTitular() {
        return titular;
    }
 
    public void setTitular(String titular) {
        this.titular = titular;
    }
 
    public LocalDate getFechaApertura() {
        return fechaApertura;
    }
 
    public void setFechaApertura(LocalDate fechaApertura) {
        this.fechaApertura = fechaApertura;
    }
 
    public double getSaldo() {
        return saldo;
    }
 
    protected void setSaldo(double saldo) {
        this.saldo = saldo;
    }
 
    public boolean isBloqueada() {
        return bloqueada;
    }
 
    public void setBloqueada(boolean bloqueada) {
        this.bloqueada = bloqueada;
    }
 
    public ArrayList<Movimiento> getMovimientos() {
        return movimientos;
    }
 
    public void depositar(double monto) throws CuentaBloqueadaException, IOException {
        if (bloqueada) {
            throw new CuentaBloqueadaException(numeroCuenta);
        }
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a cero.");
        }
        double saldoAnterior = this.saldo;
        this.saldo += monto;
        Movimiento mov = new Movimiento("Deposito", numeroCuenta, saldoAnterior, monto, this.saldo);
        movimientos.add(mov);
        Movimiento.agregarMovimiento(mov);
        System.out.println("Deposito exitoso. Nuevo saldo: " + this.saldo);
    }
 
    public void retirar(double monto) throws CuentaBloqueadaException, SaldoInsuficienteException,
    LimiteRetirosException, IOException {
        if (bloqueada) {
            throw new CuentaBloqueadaException(numeroCuenta);
        }
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor a cero.");
        }
        if (!validarRetiro(monto)) {
            throw new SaldoInsuficienteException(this.saldo, monto);
        }
        double saldoAnterior = this.saldo;
        this.saldo -= monto;
        Movimiento mov = new Movimiento("Retiro", numeroCuenta, saldoAnterior, monto, this.saldo);
        movimientos.add(mov);
        Movimiento.agregarMovimiento(mov);
        System.out.println("Retiro exitoso. Nuevo saldo: " + this.saldo);
    }
 
    public void mostrarMovimientos() {
        System.out.println("=== Movimientos de cuenta " + numeroCuenta + " ===");
        if (movimientos.isEmpty()) {
            System.out.println("Sin movimientos registrados.");
            return;
        }
        for (Movimiento m : movimientos) {
            System.out.println(m);
        }
    }
 
    public abstract boolean validarRetiro(double monto);
 
    public abstract void aplicarInteres() throws IOException;
 
    public abstract String getTipoCuenta();
 
    public String toLinea() {
        return getTipoCuenta() + "|" + numeroCuenta + "|" + titular + "|"
        + fechaApertura + "|" + saldo + "|" + bloqueada + "|" + datosExtra();
    }
 
    protected abstract String datosExtra();
 
    public static void guardarCuentas(ArrayList<CuentaBancaria> cuentas) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO, false));
        try {
            for (CuentaBancaria c : cuentas) {
                bw.write(c.toLinea());
                bw.newLine();
            }
        } finally {
            bw.close();
        }
    }
 
    public static ArrayList<CuentaBancaria> cargarCuentas() throws IOException {
        ArrayList<CuentaBancaria> lista = new ArrayList<>();
        java.io.File archivo = new java.io.File(ARCHIVO);
        if (!archivo.exists()) {
            return lista;
        }
        BufferedReader br = new BufferedReader(new FileReader(ARCHIVO));
        try {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (!linea.trim().isEmpty()) {
                    CuentaBancaria cuenta = desdeLinea(linea);
                    if (cuenta != null) {
                        lista.add(cuenta);
                    }
                }
            }
        } finally {
            br.close();
        }
        return lista;
    }
 
    public static CuentaBancaria desdeLinea(String linea) {
        String[] p = linea.split("\\|");
        String tipo = p[0];
        int numeroCuenta = Integer.parseInt(p[1]);
        String titular = p[2];
        LocalDate fecha = LocalDate.parse(p[3]);
        double saldo = Double.parseDouble(p[4]);
        boolean bloqueada = Boolean.parseBoolean(p[5]);
 
        CuentaBancaria cuenta = null;
 
        if (tipo.equals("Cuenta Corriente")) {
            double sobregiro = Double.parseDouble(p[6]);
            double comision = Double.parseDouble(p[7]);
            cuenta = new CuentaCorriente(numeroCuenta, titular, saldo, sobregiro, comision);
 
        } else if (tipo.equals("Cuenta de Ahorros")) {
            double tasa = Double.parseDouble(p[6]);
            int retiros = Integer.parseInt(p[7]);
            CuentaAhorros ca = new CuentaAhorros(numeroCuenta, titular, saldo, tasa);
            ca.setRetirosMensuales(retiros);
            cuenta = ca;
 
        } else if (tipo.startsWith("Cuenta Inversion")) {
            double tasa = Double.parseDouble(p[6]);
            int plazo = Integer.parseInt(p[7]);
            double minimo = Double.parseDouble(p[8]);
            String tipoInv = p[9];
            cuenta = new CuentaInversion(numeroCuenta, titular, saldo, tasa, tipoInv, plazo, minimo);
        }
 
        if (cuenta != null) {
            cuenta.setFechaApertura(fecha);
            cuenta.setBloqueada(bloqueada);
        }
        return cuenta;
    }
 
    @Override
    public String toString() {
        return numeroCuenta + " / " + fechaApertura + " " + titular + " " + saldo + " "
        + getTipoCuenta();
    }
}