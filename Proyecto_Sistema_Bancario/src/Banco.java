import java.io.IOException;
import java.util.ArrayList;
 
public class Banco {
 
    private String nombre;
    private String codigoBanco;
    private ArrayList<Cliente> clientes;
    private ArrayList<Empleado> empleados;
 
    public Banco(String nombre, String codigoBanco) {
        this.nombre = nombre;
        this.codigoBanco = codigoBanco;
        this.clientes = new ArrayList<>();
        this.empleados = new ArrayList<>();
    }
 
    public String getNombre() {
        return nombre;
    }
 
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
 
    public String getCodigoBanco() {
        return codigoBanco;
    }
 
    public void setCodigoBanco(String codigoBanco) {
        this.codigoBanco = codigoBanco;
    }
 
    public ArrayList<Cliente> getClientes() {
        return clientes;
    }
 
    public ArrayList<Empleado> getEmpleados() {
        return empleados;
    }
 
    public void registrarCliente(Cliente cliente) throws IOException {
        clientes.add(cliente);
        Cliente.guardarClientes(clientes);
        System.out.println("Cliente registrado: " + cliente.getNombreCompleto());
    }
 
    public void registrarEmpleado(Empleado empleado) throws IOException {
        empleados.add(empleado);
        Empleado.guardarEmpleados(empleados);
        System.out.println("Empleado registrado: " + empleado.getNombreCompleto()
        + " | Cargo: " + empleado.getCargo());
    }
 
    public Cliente buscarCliente(String identificacion) throws UsuarioNoEncontradoException {
        for (Cliente c : clientes) {
            if (c.getIdentificacion().equals(identificacion)) {
                return c;
            }
        }
        throw new UsuarioNoEncontradoException(identificacion);
    }
 
    public Cliente buscarClientePorNumero(String numerocliente) {
        for (Cliente c : clientes) {
            if (c.getNumerocliente().equals(numerocliente)) {
                return c;
            }
        }
        return null;
    }
 
    public Empleado buscarEmpleado(String codigoEmpleado) {
        for (Empleado e : empleados) {
            if (e.getCodigoEmpleado().equals(codigoEmpleado)) {
                return e;
            }
        }
        return null;
    }
 
    public void transferir(CuentaBancaria origen, CuentaBancaria destino, double monto)
    throws CuentaBloqueadaException, SaldoInsuficienteException, LimiteRetirosException, IOException {
        System.out.println("--- Transferencia: " + origen.getNumeroCuenta()
        + " -> " + destino.getNumeroCuenta() + " | Monto: " + monto + " ---");
        origen.retirar(monto);
        destino.depositar(monto);
        System.out.println("Transferencia completada.");
    }
 
    public void aplicarInteresATodas() throws IOException {
        System.out.println("=== Aplicando intereses y comisiones ===");
        for (Cliente c : clientes) {
            for (CuentaBancaria cuenta : c.getCuentas()) {
                cuenta.aplicarInteres();
            }
        }
    }
 
    public void cargarDatos() throws IOException {
        clientes = Cliente.cargarClientes();
        empleados = Empleado.cargarEmpleados();
        ArrayList<CuentaBancaria> todasLasCuentas = CuentaBancaria.cargarCuentas();
        for (CuentaBancaria cuenta : todasLasCuentas) {
            for (Cliente c : clientes) {
                if (c.getIdentificacion().equals(cuenta.getTitular())) {
                    c.getCuentas().add(cuenta);
                    break;
                }
            }
        }
        System.out.println("Datos cargados correctamente.");
    }
 
    public void guardarDatos() throws IOException {
        Cliente.guardarClientes(clientes);
        Empleado.guardarEmpleados(empleados);
        ArrayList<CuentaBancaria> todasLasCuentas = new ArrayList<>();
        for (Cliente c : clientes) {
            todasLasCuentas.addAll(c.getCuentas());
        }
        CuentaBancaria.guardarCuentas(todasLasCuentas);
        System.out.println("Datos guardados correctamente.");
    }
 
    public void mostrarTodosLosUsuarios() {
        System.out.println("===== USUARIOS DEL BANCO: " + nombre + " =====");
        System.out.println("-- CLIENTES --");
        for (Cliente c : clientes) {
            c.mostrarInfo();
            System.out.println();
        }
        System.out.println("-- EMPLEADOS --");
        for (Empleado e : empleados) {
            e.mostrarInfo();
            System.out.println();
        }
    }
 
    public double calcularSaldoTotal() {
        double total = 0;
        for (Cliente c : clientes) {
            total += c.getSaldoTotal();
        }
        return total;
    }
 
    @Override
    public String toString() {
        return codigoBanco + " / " + nombre + " / Clientes: " + clientes.size()
        + " / Empleados: " + empleados.size();
    }
}