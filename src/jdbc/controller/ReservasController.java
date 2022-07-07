package jdbc.controller;

import java.sql.Connection;
import java.util.List;

import jdbc.dao.ReservaDAO;
import jdbc.factory.ConnectionFactory;
import jdbc.modelo.Reserva;

public class ReservasController {
	private ReservaDAO reservaDAO;
	
	public ReservasController() {
		Connection connection = new ConnectionFactory().recuperaConexion();
		this.reservaDAO = new ReservaDAO(connection);		
	}
	
	public void guardar(Reserva reserva) {
		this.reservaDAO.guardar(reserva);
	}


	
    public int modificar(Reserva reserva) {
        return reservaDAO.modificar(reserva);
    }

    public int eliminar(String id) {
        return reservaDAO.eliminar(id);
    }


    public List<Reserva> listar() {
        return reservaDAO.listar();
    }
    
 public List<Reserva> buscarId(int id) {
    	
        return reservaDAO.buscarId(id);
    }
   	
	
	
	
}
