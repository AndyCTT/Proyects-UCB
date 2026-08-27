import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class Movimiento implements Serializable 
{
	
	private int numeroCuenta;
	private LocalDate fecha;
	private double saldoInicial;
	private double monto;
	private double saldoFinal;
	
	public Movimiento(int numeroCuenta, double saldoInicial, double monto, double saldoFinal)
	{
		
		this.numeroCuenta = numeroCuenta;
		this.fecha = LocalDate.now();
		this.saldoInicial = saldoInicial;
		this.monto = monto;
		this.saldoFinal = saldoFinal;
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
	
	
	public void registrarMovimiento() 
	{
	    try 
	    {
	        BufferedWriter bw = new BufferedWriter(new FileWriter("movimientos.txt", true));
	        bw.write(numeroCuenta + "," + fecha + "," + saldoInicial + "," + monto + "," + saldoFinal);
	        bw.newLine();
	        bw.close();

	    } catch (IOException e) 
	    {
	        System.out.println("Error al escribir en el archivo");
	    } 
	}
	
	public static ArrayList<Movimiento> leerMovimientos()
	{
		ArrayList<Movimiento> lista = new ArrayList<>();
		
		File archivo = new File("movimientos.txt");
		if (!archivo.exists())
			return lista;
		
		try
		{
			BufferedReader br = new BufferedReader(new FileReader(archivo));
			String linea;
			
			while ((linea=br.readLine())!= null)
			{
				String[] partes = linea.split(",");
				
				int numeroCuenta = Integer.parseInt(partes[0]);
				LocalDate fecha = LocalDate.parse(partes[1]);
				double saldoInicial = Double.parseDouble(partes[2]);
				double monto = Double.parseDouble(partes[3]);
				double saldoFinal = Double.parseDouble(partes[4]);
				
				Movimiento m =new Movimiento(numeroCuenta, saldoInicial, monto, saldoFinal);
				m.setFecha(fecha);
				lista.add(m);
			}
					
			br.close();
		}catch(IOException e) 
		{
			System.out.println("Error al leer en el archivo");
		}
		return lista;
		
	}

	@Override
	public String toString() {
		return "Movimiento [numeroCuenta=" + numeroCuenta + ", fecha=" + fecha + ", saldoInicial=" + saldoInicial
				+ ", monto=" + monto + ", saldoFinal=" + saldoFinal + "]";
	}
	

	
	

}


























