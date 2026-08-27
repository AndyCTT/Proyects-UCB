import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
 
public class Movimiento {
 
    private String nombre;
    private int numeroCuenta;
    private LocalDate fecha;
    private double saldoInicial;
    private double monto;
    private double saldoFinal;
 
    private static final String ARCHIVO = "movimientos.txt";
 
    public Movimiento(String nombre, int numeroCuenta, double saldoInicial, double monto, double saldoFinal) {
        this.nombre = nombre;
        this.numeroCuenta = numeroCuenta;
        this.fecha = LocalDate.now();
        this.saldoInicial = saldoInicial;
        this.monto = monto;
        this.saldoFinal = saldoFinal;
    }
 
    public String getNombre() {
        return nombre;
    }
 
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
 
    public int getNumeroCuenta() {
        return numeroCuenta;
    }
 
    public void setNumeroCuenta(int numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }
 
    public LocalDate getFecha() {
        return fecha;
    }
 
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
 
    public double getSaldoInicial() {
        return saldoInicial;
    }
 
    public void setSaldoInicial(double saldoInicial) {
        this.saldoInicial = saldoInicial;
    }
 
    public double getMonto() {
        return monto;
    }
 
    public void setMonto(double monto) {
        this.monto = monto;
    }
 
    public double getSaldoFinal() {
        return saldoFinal;
    }
 
    public void setSaldoFinal(double saldoFinal) {
        this.saldoFinal = saldoFinal;
    }
 
    public String toLinea() {
        return nombre + "|" + numeroCuenta + "|" + fecha + "|"
        + saldoInicial + "|" + monto + "|" + saldoFinal;
    }
 
    public static Movimiento desdeLinea(String linea) {
        String[] p = linea.split("\\|");
        Movimiento m = new Movimiento(p[0], Integer.parseInt(p[1]), Double.parseDouble(p[3]), Double.parseDouble(p[4]), Double.parseDouble(p[5])); 
        m.setFecha(LocalDate.parse(p[2]));
        return m;
    }
 
    public static void guardarMovimientos(ArrayList<Movimiento> movimientos) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO, false));
        try {
            for (Movimiento m : movimientos) {
                bw.write(m.toLinea());
                bw.newLine();
            }
        } finally {
            bw.close();
        }
    }
 
    public static void agregarMovimiento(Movimiento m) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO, true));
        try {
            bw.write(m.toLinea());
            bw.newLine();
        } finally {
            bw.close();
        }
    }
 
    public static ArrayList<Movimiento> cargarMovimientos() throws IOException {
        ArrayList<Movimiento> lista = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(ARCHIVO));
        try {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (!linea.trim().isEmpty()) {
                    lista.add(desdeLinea(linea));
                }
            }
        } finally {
            br.close();
        }
        return lista;
    }
 
    public static ArrayList<Movimiento> cargarMovimientosPorCuenta(int numeroCuenta) throws IOException {
        ArrayList<Movimiento> todos = cargarMovimientos();
        ArrayList<Movimiento> resultado = new ArrayList<>();
        for (Movimiento m : todos) {
            if (m.getNumeroCuenta() == numeroCuenta) {
                resultado.add(m);
            }
        }
        return resultado;
    }
 
    @Override
    public String toString() {
        return numeroCuenta + " / " + fecha + " " + saldoInicial + " " + monto + " "
        + saldoFinal;
    }
}