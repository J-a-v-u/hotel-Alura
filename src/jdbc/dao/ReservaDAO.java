package jdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import jdbc.modelo.Reserva;


public class ReservaDAO {
	 private Connection connection;
	 
	    public ReservaDAO(Connection connection) {
	        this.connection = connection;
	    }
	    public void guardar(Reserva reserva) {
	    	try {
				String sql = "INSERT INTO reservas (fechaEntrada,fechaSalida, valor, formaPago) VALUES (?, ?, ?, ?)";
				try(PreparedStatement pstm = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
					
					pstm.setString(1,  reserva.getFechaEntrada());
					pstm.setString(2,  reserva.getFechaSalida());
					pstm.setInt(3,  reserva.getValor());
					pstm.setString(4,  reserva.getFormaPago());
					
					pstm.executeUpdate();
					
					try(ResultSet rst = pstm.getGeneratedKeys()){
						while(rst.next()) {
							reserva.setId(rst.getString(1));
							System.out.println("idreserva " + rst.getInt(1));
							
						}
						
					}
					
					
					}
				} catch(SQLException e) {
					throw new RuntimeException(e);
				}
	    	
	    	} 
	    
	    

	    
	    public List<Reserva> listar() {
	        List<Reserva> resultado = new ArrayList<>();

	        try {
	            final PreparedStatement statement = connection
	                    .prepareStatement("SELECT ID, FECHAENTRADA, FECHASALIDA, VALOR, FORMAPAGO FROM RESERVAS");
	    
	            try (statement) {
	                statement.execute();
	    
	                final ResultSet resultSet = statement.getResultSet();
	    
	                try (resultSet) {
	                    while (resultSet.next()) {
	                        resultado.add(new Reserva(
	                        		resultSet.getString("ID"),
	                        		resultSet.getDate("FECHAENTRADA"),
	                        		resultSet.getDate("FECHASALIDA"),
	                        		resultSet.getInt("VALOR"),
	                        		resultSet.getString("FORMAPAGO")));
	                    }
	                }
	            }
	        } catch (SQLException e) {
	            throw new RuntimeException(e);
	        }

	        return resultado;
	    }

	    public int eliminar(String id) {
	        try {
	            final PreparedStatement statement = connection.prepareStatement("DELETE FROM RESERVAS WHERE ID = ?");

	            try (statement) {
	                statement.setString(1, id);
	                statement.execute();

	                int updateCount = statement.getUpdateCount();

	                return updateCount;
	            }
	        } catch (SQLException e) {
	            throw new RuntimeException(e);
	        }
	    }

	    public int modificar(Reserva reserva) {
	    	SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
	    	int updateCount;
	    	java.util.Date f1;
	    	java.sql.Date f1SQL;
	    	java.util.Date f2;
	    	java.sql.Date f2SQL;
	    	
	         
		        try {
		            final PreparedStatement statement = connection.prepareStatement(
		                    "UPDATE RESERVAS SET "
		                    + " FECHAENTRADA = ?,"
		                    + " FECHASALIDA = ?,"
		                    + " VALOR = ?,"
		                    + " FORMAPAGO = ?"
		                    + "WHERE ID = ?");
	
		            try (statement) {
		            	f1 = formato.parse(reserva.getFechaEntrada());
		            	long milisS1 = f1.getTime();
		            	f1SQL = new java.sql.Date(milisS1);
		            	
		            	
		            	f2 = formato.parse(reserva.getFechaSalida());
		            	long milisS2 = f2.getTime();
		            	f2SQL = new java.sql.Date(milisS2);
		            	
		            	Duration duration = Duration.between(f1.toInstant(), f2.toInstant());
						int valor = (int) (duration.toDays() * 2000);
		            	
		            	System.out.println(duration.toDays() );
		            	System.out.println(valor);
		            	
		                statement.setDate(1, f1SQL);
		                statement.setDate(2, f2SQL);
		                statement.setInt(3, valor);
		                statement.setString(4, reserva.getFormaPago());
		                statement.setString(5, reserva.getId());
						statement.execute();
	
		                updateCount = statement.getUpdateCount();
	
		                return updateCount;
		            } catch (ParseException e) {
		            	return updateCount = 0;
						
					}
	        } catch (SQLException e) {
	            throw new RuntimeException(e);
	        }
	        
	    }


	   
	    
	    
	    public List<Reserva> buscarId(int idABuscar) {
	        List<Reserva> resultado = new ArrayList<>();

	        try {
	            final PreparedStatement statement = connection
	                    .prepareStatement("SELECT ID, FECHAENTRADA, FECHASALIDA, VALOR, FORMAPAGO FROM RESERVAS WHERE ID = ?");
	         
	    
	            try (statement) {
	            	String id = Integer.toString(idABuscar);
	            	statement.setString(1, id);
	                statement.execute();
	    
	                final ResultSet resultSet = statement.getResultSet();
	                System.out.println("en buscarid " + resultSet);
	               // try (resultSet) {
	                    while (resultSet.next()) {
	                    //	if(id == resultSet.getString("ID")) {
	                    	
	                    	Reserva row = new Reserva(
	                        		resultSet.getString("ID"),
	                        		resultSet.getDate("FECHAENTRADA"),
	                        		resultSet.getDate("FECHASALIDA"),
	                        		resultSet.getInt("VALOR"),
	                        		resultSet.getString("FORMAPAGO"));
	                    	resultado.add(row);
	                    	}
	                     return resultado;
	                    }
	           
	              //  } 
	           // }
	        } catch (SQLException e) {
	            throw new RuntimeException(e);
	        }

	       
	    }

	   
	   
}


