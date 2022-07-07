package jdbc.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import jdbc.modelo.Huesped;


public class RegistroHuespedDAO {
	
	 final private Connection connection;

	    public RegistroHuespedDAO(Connection connection) {
	        this.connection = connection;
	    }
	    public void guardar(Huesped huesped) {
	    	try {
				String sql = "INSERT INTO huespedes (nombre, apellido, fechaDeNacimiento, nacionalidad, telefono, idReserva) VALUES (?, ?, ?, ?, ?, ?)";
				try(PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
					
					pstm.setString(1,  huesped.getNombre());
					pstm.setString(2,  huesped.getApellido());
					pstm.setDate(3,  huesped.getFechaDeNacimientoSQL());
					pstm.setString(4,  huesped.getNacionalidad());
					pstm.setInt(5,  huesped.getTelefono());
					pstm.setString(6,  huesped.getIdReserva());
					
					pstm.executeUpdate();
					
					try(ResultSet rst = pstm.getGeneratedKeys()){
						while(rst.next()) {
							huesped.setId(rst.getInt(1));
							
						}
						
					}
					
					
					}
				} catch(SQLException e) {
					throw new RuntimeException(e);
				}
	    	
	    	}
	    
	    
	    
	    public List<Huesped> listar() {
	        List<Huesped> resultado = new ArrayList<>();

	        try {
	            final PreparedStatement statement = connection
	                    .prepareStatement("SELECT ID, NOMBRE, APELLIDO, FECHADENACIMIENTO, NACIONALIDAD, TELEFONO, IDRESERVA FROM HUESPEDES");
	    
	            try (statement) {
	                statement.execute();
	    
	               ResultSet resultSet = statement.getResultSet();
	    
	                try (resultSet) {
	                    while (resultSet.next()) {
	                    	Huesped row = new Huesped(
	                        		resultSet.getInt("ID"),
	                                resultSet.getString("NOMBRE"),
	                        		resultSet.getString("APELLIDO"),
	                        		resultSet.getDate("FECHADENACIMIENTO"),
	                        		resultSet.getString("NACIONALIDAD"),
	                        		resultSet.getInt("TELEFONO"),
	                        		resultSet.getString("IDRESERVA"));
	                    	resultado.add(row);
	                    	
	
	                    }
	                }
	            }
	        } catch (SQLException e) {
	            throw new RuntimeException(e);
	        }

	        return resultado;
	    }

	    public int eliminar(Integer id) {
	        try {
	            final PreparedStatement statement = connection.prepareStatement("DELETE FROM HUESPEDES WHERE ID = ?");

	            try (statement) {
	                statement.setInt(1, id);
	                statement.execute();

	                int updateCount = statement.getUpdateCount();

	                return updateCount;
	            }
	        } catch (SQLException e) {
	            throw new RuntimeException(e);
	        }
	    }

	    public int modificar(Huesped huesped) {
	    	//SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
	    	int updateCount;
	    	//java.util.Date fN;
	    	//java.sql.Date fNSQL;
	        try {
	            final PreparedStatement statement = connection.prepareStatement(
	            		"UPDATE HUESPEDES SET "
	        					+ " NOMBRE = ?,"
	        					+ " APELLIDO = ?,"
	        					+ " FECHADENACIMIENTO = ?,"
	        					+ " NACIONALIDAD = ?,"
	        					+ " TELEFONO = ?"
	        					+ " WHERE ID = ?");

	            try (statement) {
	            	
//				    fN = formato.parse(huesped.getFechaDeNacimiento());
//					
//	            	long milisSN = fN.getTime();
//	            	fNSQL = new java.sql.Date(milisSN);
//	            	
//	            	System.out.println("FECHAN parseada " + fNSQL);
	            	
	            	System.out.println("NOMBRE " + huesped.getNombre() + "APELLIDO " + huesped.getApellido());
	            	
	                statement.setString(1, huesped.getNombre());
	                statement.setString(2, huesped.getApellido());
	                statement.setDate(3, huesped.getFechaDeNacimientoSQL());
	                statement.setString(4, huesped.getNacionalidad());
	                statement.setInt(5, huesped.getTelefono());
	               // statement.setString(6, huesped.getIdReserva());
	                statement.setInt(6, huesped.getId());
	                
	                statement.execute();

	                updateCount = statement.getUpdateCount();
					
					System.out.println("contador " + updateCount);
	                return updateCount;
	            }
	        } catch (SQLException e) {
	        	
	            throw new RuntimeException(e);
	        }
	        
	    }


	    public List<Huesped> buscarApellido(String apellidoABUscar) {
	        List<Huesped> resultado = new ArrayList<>();

	        try {
	        	
	            final PreparedStatement statement = connection
	                    .prepareStatement("SELECT ID, NOMBRE, APELLIDO, FECHADENACIMIENTO, NACIONALIDAD, TELEFONO, IDRESERVA FROM HUESPEDES WHERE APELLIDO = ?");
	         
	    
	            try (statement) {
	            	String apellido =  apellidoABUscar.toUpperCase();
	            	statement.setString(1, apellido);
	                statement.execute();
	    
	                final ResultSet resultSet = statement.getResultSet();
	                System.out.println("en buscarape " + resultSet);
	               // try (resultSet) {
	                    while (resultSet.next()) {
	                    //	if(id == resultSet.getString("ID")) {
	                    	
	                    	Huesped row = new Huesped(
	                        		resultSet.getInt("ID"),
	                        		resultSet.getString("NOMBRE"),
	                        		resultSet.getString("APELLIDO"),
	                        		resultSet.getDate("FECHADENACIMIENTO"),
	                        		resultSet.getString("NACIONALIDAD"),
	                        		resultSet.getInt("TELEFONO"),
	                        		resultSet.getString("IDRESERVA"));
	                    	resultado.add(row);
	                    	}
	                     return resultado;
	                    }
	
	        } catch (SQLException e) {
	            throw new RuntimeException(e);
	        }

	       
	    }
	    

}
