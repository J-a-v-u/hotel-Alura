package jdbc.controller;
import java.sql.Connection;
import java.util.List;
import jdbc.dao.RegistroHuespedDAO;
import jdbc.factory.ConnectionFactory;
import jdbc.modelo.Huesped;

public class HuespedController {
	private RegistroHuespedDAO registroHuespedDAO;
	
	public HuespedController() {
		Connection connection = new ConnectionFactory().recuperaConexion();
		this.registroHuespedDAO = new RegistroHuespedDAO(connection);		
	}
	
	public void guardar(Huesped huesped) {
		this.registroHuespedDAO.guardar(huesped);
	}
	
	
	
    public int modificar(Huesped huesped) {
        return registroHuespedDAO.modificar(huesped);
    }

    public int eliminar(Integer id) {
        return registroHuespedDAO.eliminar(id);
    }

    public List<Huesped> listar() {
        return this.registroHuespedDAO.listar();
    }
    
   
	public List<Huesped> buscarApellido(String apellidoABUscar) {
	    	
	        return registroHuespedDAO.buscarApellido(apellidoABUscar);
	}
  
	

}
