import java.io.*;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
 
public class Cliente extends Usuario {
 
    private String numerocliente;
    private ArrayList<CuentaBancaria> cuentas;
 
    private static final String ARCHIVO = "usuarios.txt";
 
    public Cliente(String nombre, String apellido, String identificacion, String correo,
    String telefono, String contrasena, String numerocliente) {
        super(nombre, apellido, identificacion, correo, telefono, contrasena);
        this.numerocliente = numerocliente;
        this.cuentas = new ArrayList<>();
    }
 
    public String getNumerocliente() {
        return numerocliente;
    }
 
    public void setNumerocliente(String numerocliente) {
        this.numerocliente = numerocliente;
    }
 
    public ArrayList<CuentaBancaria> getCuentas() {
        return cuentas;
    }
 
    public void agregarCuenta(CuentaBancaria cuenta) {
        cuentas.add(cuenta);
        System.out.println("Cuenta " + cuenta.getNumeroCuenta() + " asignada a " + getNombreCompleto());
    }
 
    public void eliminarCuenta(int numeroCuenta) {
        cuentas.removeIf(c -> c.getNumeroCuenta() == numeroCuenta);
        System.out.println("Cuenta " + numeroCuenta + " eliminada.");
    }
 
    public CuentaBancaria buscarCuenta(int numeroCuenta) {
        for (CuentaBancaria c : cuentas) {
            if (c.getNumeroCuenta() == numeroCuenta) {
                return c;
            }
        }
        return null;
    }
 
    public double getSaldoTotal() {
        double total = 0;
        for (CuentaBancaria c : cuentas) {
            total += c.getSaldo();
        }
        return total;
    }
 
    public String toLinea() {
        return "CLIENTE|" + getNombre() + "|" + getApellido() + "|" + getIdentificacion()
        + "|" + getCorreo() + "|" + getTelefono() + "|" + getContrasena() + "|" + numerocliente;
    }
 
    public static void guardarClientes(ArrayList<Cliente> clientes) throws IOException {
        ArrayList<String> lineasEmpleados = new ArrayList<>();
        File archivo = new File(ARCHIVO);
        if (archivo.exists()) {
            BufferedReader br = new BufferedReader(new FileReader(ARCHIVO));
            try {
                String linea;
                while ((linea = br.readLine()) != null) {
                    if (linea.startsWith("EMPLEADO|")) {
                        lineasEmpleados.add(linea);
                    }
                }
            } finally {
                br.close();
            }
        }
        BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO, false));
        try {
            for (Cliente c : clientes) {
                bw.write(c.toLinea());
                bw.newLine();
            }
            for (String linea : lineasEmpleados) {
                bw.write(linea);
                bw.newLine();
            }
        } finally {
            bw.close();
        }
    }
 
    public static ArrayList<Cliente> cargarClientes() throws IOException {
        ArrayList<Cliente> lista = new ArrayList<>();
        File archivo = new File(ARCHIVO);
        if (!archivo.exists()) {
            return lista;
        }
        BufferedReader br = new BufferedReader(new FileReader(ARCHIVO));
        try {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.startsWith("CLIENTE|")) {
                    String[] p = linea.split("\\|");
                    Cliente c = new Cliente(p[1], p[2], p[3], p[4], p[5], p[6], p[7]);
                    lista.add(c);
                }
            }
        } finally {
            br.close();
        }
        return lista;
    }
 
    @Override
    public String getRol() {
        return "Cliente";
    }
 
    @Override
    public void mostrarInfo() {
        System.out.println("=== Cliente: " + getNombreCompleto() + " ===");
        System.out.println("ID: " + getIdentificacion() + " | N° Cliente: " + numerocliente);
        System.out.println("Correo: " + getCorreo() + " | Tel: " + getTelefono());
        System.out.println("Cuentas: " + cuentas.size() + " | Saldo total: " + getSaldoTotal());
        for (CuentaBancaria c : cuentas) {
            System.out.println("  -> " + c);
        }
    }
 
    @Override
    public String toString() {
        return numerocliente + " / " + getIdentificacion() + " / " + getNombreCompleto()
        + " / " + getCorreo() + " / " + getRol();
    }
}
 