import java.io.*;
import java.util.ArrayList;

public class Cuenta implements Serializable {
	
	private int numeroCuenta;
	private double saldo;
	private ArrayList <Movimiento> movimientos;
	
	public Cuenta(int numeroCuenta, double saldo) 
	{
		this.numeroCuenta = numeroCuenta;
		this.saldo = saldo;
		this.movimientos = new ArrayList<Movimiento>();
	}
	public int getNumeroCuenta() {
		return numeroCuenta;
	}
	public void setNumeroCuenta(int numeroCuenta) {
		this.numeroCuenta = numeroCuenta;
	}
	public double getSaldo() {
		return saldo;
	}
	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}
	public ArrayList<Movimiento> getMovimientos() {
		return movimientos;
	}
	public void setMovimientos(ArrayList<Movimiento> movimientos) {
		this.movimientos = movimientos;
	}
	
	public void cargarMovimientos() throws IOException
	{
		movimientos.clear();
		ArrayList<Movimiento> todos = Movimiento.leerMovimientos();
		for(Movimiento m : todos)
		{
			if (m.getNumeroCuenta() == this.numeroCuenta)
			{
				movimientos.add(m);
			}
		}
	}
	
	public boolean depositar(double monto) throws IOException
	{
		if(monto<0)
			return false;
		
		double saldoFinal = this.saldo + monto;
		Movimiento m = new Movimiento(numeroCuenta, this.saldo, monto, saldoFinal );
		m.registrarMovimiento();
		cargarMovimientos();
		this.saldo = saldoFinal;
		actualizarCuentas(leerCuentas());
		return true;
	}
	
	public boolean retirar(double monto) throws IOException
	{
		if(monto < 0 ||monto > this.saldo)
			return false;
		double saldoFinal = this.saldo - monto;
		Movimiento m = new Movimiento(numeroCuenta, this.saldo, -monto, saldoFinal);
		m.registrarMovimiento();
		cargarMovimientos();
		this.saldo = saldoFinal;
		actualizarCuentas(leerCuentas());
		return true;
	}
	
	public void registrar() throws IOException
	{
		ArrayList<Cuenta> cuentas = leerCuentas();
		cuentas.add(this);
		actualizarCuentas(cuentas);			
	}
	
	public static ArrayList<Cuenta> leerCuentas() throws IOException
	{
		ArrayList <Cuenta> lista = new ArrayList<Cuenta>();
		File archivo = new File("Cuentas.bin");
		if (!archivo.exists())
			return lista;
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo)))
		{
			
			lista = (ArrayList<Cuenta>) ois.readObject();
			
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	public static void actualizarCuentas(ArrayList<Cuenta> cuentas) throws IOException 
	{
	    ObjectOutputStream oos = null;

	    try 
	    {
	        oos = new ObjectOutputStream(new FileOutputStream("Cuentas.bin"));
	        oos.writeObject(cuentas);
	    } 
	    finally 
	    {
	        if (oos != null)
	        {
	            oos.close();
	        }
	    }
	}
}




















