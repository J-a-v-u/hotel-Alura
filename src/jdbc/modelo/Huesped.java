package jdbc.modelo;

import java.sql.Date;

public class Huesped {
	private int id;
	private String nombre;
	private String apellido;
	private Date fechaDeNacimiento;
	private String nacionalidad;
    private int telefono;
    private String idReserva;
 	
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getIdReserva() {
		return idReserva;
	}





	public void setIdReserva(String idReserva) {
		this.idReserva = idReserva;
	}





	public String getNombre() {
		return nombre;
	}





	public String getApellido() {
		return apellido;
	}





	public String getFechaDeNacimiento() {
		return fechaDeNacimiento.toString();
	}

	public Date getFechaDeNacimientoSQL() {
		return fechaDeNacimiento;
	}



	public String getNacionalidad() {
		return nacionalidad;
	}





	public int getTelefono() {
		return telefono;
	}





	public Huesped(String nombre, String apellido, Date fechaDeNacimiento, String nacionalidad, int telefono,
			String idReserva) {
		//super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.fechaDeNacimiento = fechaDeNacimiento;
		this.nacionalidad = nacionalidad;
		this.telefono = telefono;
		this.idReserva = idReserva;
	}
	
	public Huesped(int id, String nombre, String apellido, Date fechaDeNacimiento, String nacionalidad, int telefono,
			String idReserva) {
		//super();
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.fechaDeNacimiento = fechaDeNacimiento;
		this.nacionalidad = nacionalidad;
		this.telefono = telefono;
		this.idReserva = idReserva;
	}
	
	public Huesped(int id, String nombre, String apellido, Date fechaDeNacimiento, String nacionalidad, int telefono) {
		//super();
		this.id = id;
		this.nombre = nombre;
		this.apellido = apellido;
		this.fechaDeNacimiento = fechaDeNacimiento;
		this.nacionalidad = nacionalidad;
		this.telefono = telefono;
	}
	
	
	 @Override
	    public String toString() {
	        return String.format(
	                "{ id: %d, nombre: %s, apellido: %s, fechaDeNacimiento: %s, nacionalidad: %s, telefono: %d, idReserva: %s }",
	                this.id, this.nombre, this.apellido, this.fechaDeNacimiento, this.nacionalidad, this.telefono, this.idReserva);
	    }
	
}
