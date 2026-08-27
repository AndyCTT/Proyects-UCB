import java.io.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
 
public class Empleado extends Usuario {
 
    private String codigoEmpleado;
    private String cargo;
    private String departamento;
    private double salario;
    private LocalDate fechaIngreso;
 
    private static final String ARCHIVO = "usuarios.txt";
 
    public Empleado(String nombre, String apellido, String identificacion, String correo,
    String telefono, String contrasena, String codigoEmpleado, String cargo,
    String departamento, double salario) {
        super(nombre, apellido, identificacion, correo, telefono, contrasena);
        this.codigoEmpleado = codigoEmpleado;
        this.cargo = cargo;
        this.departamento = departamento;
        this.salario = salario;
        this.fechaIngreso = LocalDate.now();
    }
 
    public String getCodigoEmpleado() {
        return codigoEmpleado;
    }
 
    public void setCodigoEmpleado(String codigoEmpleado) {
        this.codigoEmpleado = codigoEmpleado;
    }
 
    public String getCargo() {
        return cargo;
    }
 
    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
 
    public String getDepartamento() {
        return departamento;
    }
 
    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }
 
    public double getSalario() {
        return salario;
    }
 
    public void setSalario(double salario) {
        this.salario = salario;
    }
 
    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }
 
    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }
 
    public void aprobarTransferencia(CuentaBancaria origen, CuentaBancaria destino, double monto)
    throws CuentaBloqueadaException, SaldoInsuficienteException, LimiteRetirosException, IOException {
        System.out.println("Empleado " + getNombreCompleto() + " aprobo transferencia de "
        + monto + " de cuenta " + origen.getNumeroCuenta()
        + " a cuenta " + destino.getNumeroCuenta());
        origen.retirar(monto);
        destino.depositar(monto);
    }
 
    public void bloquearCuenta(CuentaBancaria cuenta) {
        cuenta.setBloqueada(true);
        System.out.println("Empleado " + getNombreCompleto() + " bloqueo la cuenta "
        + cuenta.getNumeroCuenta());
    }
 
    public void desbloquearCuenta(CuentaBancaria cuenta) {
        cuenta.setBloqueada(false);
        System.out.println("Empleado " + getNombreCompleto() + " desbloqueo la cuenta "
        + cuenta.getNumeroCuenta());
    }
 
    public String toLinea() {
        return "EMPLEADO|" + getNombre() + "|" + getApellido() + "|" + getIdentificacion()
        + "|" + getCorreo() + "|" + getTelefono() + "|" + getContrasena() + "|"
        + codigoEmpleado + "|" + cargo + "|" + departamento + "|" + salario + "|" + fechaIngreso;
    }
 
    public static void guardarEmpleados(ArrayList<Empleado> empleados) throws IOException {
        ArrayList<String> lineasClientes = new ArrayList<>();
        File archivo = new File(ARCHIVO);
        if (archivo.exists()) {
            BufferedReader br = new BufferedReader(new FileReader(ARCHIVO));
            try {
                String linea;
                while ((linea = br.readLine()) != null) {
                    if (linea.startsWith("CLIENTE|")) {
                        lineasClientes.add(linea);
                    }
                }
            } finally {
                br.close();
            }
        }
        BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO, false));
        try {
            for (String linea : lineasClientes) {
                bw.write(linea);
                bw.newLine();
            }
            for (Empleado e : empleados) {
                bw.write(e.toLinea());
                bw.newLine();
            }
        } finally {
            bw.close();
        }
    }
 
    public static ArrayList<Empleado> cargarEmpleados() throws IOException {
        ArrayList<Empleado> lista = new ArrayList<>();
        File archivo = new File(ARCHIVO);
        if (!archivo.exists()) {
            return lista;
        }
        BufferedReader br = new BufferedReader(new FileReader(ARCHIVO));
        try {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.startsWith("EMPLEADO|")) {
                    String[] p = linea.split("\\|");
                    Empleado e = new Empleado(p[1], p[2], p[3], p[4], p[5], p[6],
                    p[7], p[8], p[9], Double.parseDouble(p[10]));
                    e.setFechaIngreso(LocalDate.parse(p[11]));
                    lista.add(e);
                }
            }
        } finally {
            br.close();
        }
        return lista;
    }
 
    @Override
    public String getRol() {
        return "Empleado";
    }
 
    @Override
    public void mostrarInfo() {
        System.out.println("=== Empleado: " + getNombreCompleto() + " ===");
        System.out.println("Codigo: " + codigoEmpleado + " | Cargo: " + cargo);
        System.out.println("Departamento: " + departamento + " | Salario: " + salario);
        System.out.println("Ingreso: " + fechaIngreso + " | Correo: " + getCorreo());
    }
 
    @Override
    public String toString() {
        return codigoEmpleado + " / " + getIdentificacion() + " / " + getNombreCompleto()
        + " / " + cargo + " / " + departamento;
    }
}