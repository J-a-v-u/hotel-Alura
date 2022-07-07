package jdbc.modelo;

import java.sql.Date;


public class Reserva{
	public String id;	
	private Date fechaEntrada;
	private Date fechaSalida;
	private int valor;
	private String formaPago;
	
	
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getFechaEntrada() {
		return fechaEntrada.toString();
	}


	public String getFechaSalida() {
		return fechaSalida.toString();
	}


	public int getValor() {
		return valor;
	}


	public String getFormaPago() {
		return formaPago;
	}

	
	public Reserva(String id, java.sql.Date fechaEntrada, java.sql.Date fechaSalida, int valor, String formaPago) {
		this.id = id;
		this.fechaEntrada = fechaEntrada;
		this.fechaSalida = fechaSalida;
		this.valor = valor;
		this.formaPago = formaPago;
	}
	

	
	 public Reserva(Date fechaEntrada, Date fechaSalida, int valor, String formaPago) {
		 	this.fechaEntrada = fechaEntrada;
			this.fechaSalida = fechaSalida;
			this.valor = valor;
			this.formaPago = formaPago;
	}


	@Override
	    public String toString() {
	        return String.format(
	                "{ id: %s, fechaEntrada: %s, fechaSalida: %s, valor: %d, formaPago: %s }",
	                this.id, this.fechaEntrada, this.fechaSalida, this.valor, this.formaPago);
	    }

}

