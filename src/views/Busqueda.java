package views;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import jdbc.controller.HuespedController;
import jdbc.controller.ReservasController;
import jdbc.modelo.Huesped;
import jdbc.modelo.Reserva;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;
import java.awt.Toolkit;

@SuppressWarnings("serial")
public class Busqueda<E> extends JFrame {

	private JPanel contentPane;
	private JTextField txtBuscar;
	private JTabbedPane panel;
	private JTable tbHuespedes;
	private JTable tbReservas;
	public String palabraABuscar;
	private HuespedController huespedController;
	private ReservasController reservasController;
	private DefaultTableModel modeloH;
	private DefaultTableModel modeloR;
	private int idABuscar;
	private String apellidoABUscar;
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Busqueda frame = new Busqueda();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Busqueda() {
		this.huespedController = new HuespedController();
		this.reservasController = new ReservasController();
		
		
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(Busqueda.class.getResource("/imagenes/lupa2.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 910, 516);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		/*-------------buscar-------------*/
		txtBuscar = new JTextField();
		txtBuscar.setBounds(647, 85, 158, 31);
		contentPane.add(txtBuscar);
		txtBuscar.setColumns(10);
		
		
		/*-------------Label indicaciones buscar-------------*/
	

		JLabel lblLabelBuscarPor = new JLabel("Para buscar por ID, ingresar solo números, de");
		lblLabelBuscarPor.setBounds(600, 35, 300, 14);
		lblLabelBuscarPor.setForeground(new Color(12, 138, 199));
		lblLabelBuscarPor.setFont(new Font("Arial", Font.BOLD, 12));
		contentPane.add(lblLabelBuscarPor);
		
		JLabel lblLabelBuscarPor2 = new JLabel("lo contrario puede buscar por Apellido");
		lblLabelBuscarPor2.setBounds(600, 50, 300, 14);
		lblLabelBuscarPor2.setForeground(new Color(12, 138, 199));
		lblLabelBuscarPor2.setFont(new Font("Arial", Font.BOLD, 12));
		contentPane.add(lblLabelBuscarPor2);
		
		/*-------------buscar-------------*/
		
		JButton btnBuscar = new JButton("");
		btnBuscar.setBackground(Color.WHITE);
		btnBuscar.setIcon(new ImageIcon(Busqueda.class.getResource("/imagenes/lupa2.png")));
		btnBuscar.setBounds(815, 75, 54, 41);
		contentPane.add(btnBuscar);
		
		btnBuscar.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				
				
				
				palabraABuscar = txtBuscar.getText();
				System.out.println(palabraABuscar);
				try {
					idABuscar = Integer.parseInt(palabraABuscar);
					limpiarTablas();
					cargarTablasBusquedaPorId(idABuscar);//requestFocusInWindow()
					panel.setSelectedIndex(1);
				
						System.out.println("id: " + idABuscar);
						//modificarTablaId(idABuscar);
					}
				 catch (Exception e2) {
					apellidoABUscar = palabraABuscar;
					 System.out.println("apellido: " + apellidoABUscar);
					 limpiarTablas();
					 cargarTablasBusquedaPorApe(apellidoABUscar);
					 panel.setSelectedIndex(0);
				}
				
			}
		});
		
		
		
		
		/*-------------btn editar-------------*/
		JButton btnEditar = new JButton("");
		btnEditar.setIcon(new ImageIcon(Busqueda.class.getResource("/imagenes/editar-texto.png")));
		btnEditar.setBackground(SystemColor.menu);
		btnEditar.setBounds(587, 416, 54, 41);
		contentPane.add(btnEditar);
		
		JLabel lblNewLabel_4 = new JLabel("Sistema de Búsqueda");
		lblNewLabel_4.setForeground(new Color(12, 138, 199));
		lblNewLabel_4.setFont(new Font("Arial", Font.BOLD, 24));
		lblNewLabel_4.setBounds(155, 42, 258, 42);
		contentPane.add(lblNewLabel_4);
		
		
		
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editar();
				
				System.out.println(palabraABuscar);
				
			}
		});
		
		
		
		
		
		
		JButton btnSalir = new JButton("");
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MenuUsuario usuario = new MenuUsuario();
				usuario.setVisible(true);
				dispose();
			}
		});
		btnSalir.setIcon(new ImageIcon(Busqueda.class.getResource("/imagenes/cerrar-sesion 32-px.png")));
		btnSalir.setForeground(Color.WHITE);
		btnSalir.setBackground(Color.WHITE);
		btnSalir.setBounds(815, 416, 54, 41);
		contentPane.add(btnSalir);
		
		panel = new JTabbedPane(JTabbedPane.TOP);
		panel.setBounds(10, 127, 874, 265);
		contentPane.add(panel);
		
		/*-------------tabla huespedes-------------*/
		tbHuespedes = new JTable();
			modeloH = (DefaultTableModel) tbHuespedes.getModel();
			modeloH.addColumn("Id");
	        modeloH.addColumn("Nombre");
	        modeloH.addColumn("Apellido");
	        modeloH.addColumn("Fecha de nacimiento");
	        modeloH.addColumn("Nacionalidad");
	        modeloH.addColumn("telefono");
	        modeloH.addColumn("Id de reserva");
		tbHuespedes.setFont(new Font("Arial", Font.PLAIN, 14));
		panel.addTab("Huéspedes", new ImageIcon(Busqueda.class.getResource("/imagenes/persona.png")), tbHuespedes, null);
		cargarTablaH();
		
		/*-------------tabla reservas-------------*/
		tbReservas = new JTable();
			modeloR = (DefaultTableModel) tbReservas.getModel();
			modeloR.addColumn("Id");
	        modeloR.addColumn("Fecha de Entrada");
	        modeloR.addColumn("Fecha de Salida");
	        modeloR.addColumn("valor");
	        modeloR.addColumn("Forma de Pago");
		tbReservas.setFont(new Font("Arial", Font.PLAIN, 14));
		panel.addTab("Reservas", new ImageIcon(Busqueda.class.getResource("/imagenes/calendario.png")), tbReservas, null);
		cargarTablaReservas();
		
		
		/*-------------btn eliminar-------------*/
		JButton btnEliminar = new JButton("");
		btnEliminar.setIcon(new ImageIcon(Busqueda.class.getResource("/imagenes/deletar.png")));
		btnEliminar.setBackground(SystemColor.menu);
		btnEliminar.setBounds(651, 416, 54, 41);
		contentPane.add(btnEliminar);
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {				
				boolean tablaReservasElegida = false;
				boolean tablaHuespedesElegida = false;
				
				try {
					if (tbReservas.getSelectedRowCount() > 0) {
						tablaReservasElegida = true;
					}					
					
				} catch (Exception e2) {
					throw new NullPointerException();
				}
				
				try {
					if (tbHuespedes.getSelectedRowCount() > 0) {
					tablaHuespedesElegida = true;
					}
				} catch (Exception e2) {
					throw new NullPointerException();
				}
					
					if(tablaReservasElegida) {
						System.out.println("comenzando el borrado en reservas...");
						eliminar();
						limpiarTabla();
						limpiarTablaHuesped();
						cargarTablaH();
						cargarTablaReservas();
					}else if(tablaHuespedesElegida) {
						System.out.println("comenzando el borrado de huesped...");
						eliminarH();
						limpiarTabla();
						limpiarTablaHuesped();
						cargarTablaH();
						cargarTablaReservas();
					} else {
						System.out.println("algo salio mal: ");;
					}
				}
				
				
				//System.out.println(palabraABuscar);
				
			
		});
		
		
		
		
		
		JButton btnCancelar = new JButton("");
		btnCancelar.setIcon(new ImageIcon(Busqueda.class.getResource("/imagenes/cancelar.png")));
		btnCancelar.setBackground(SystemColor.menu);
		btnCancelar.setBounds(713, 416, 54, 41);
		contentPane.add(btnCancelar);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(Busqueda.class.getResource("/imagenes/Ha-100px.png")));
		lblNewLabel_2.setBounds(25, 10, 104, 107);
		contentPane.add(lblNewLabel_2);
		setResizable(false);
	}
	
	
	/*----------------funciones reserva------------------*/
	
	 private void limpiarTabla() {
		modeloR.getDataVector().clear();
	    }
	 
	private boolean tieneFilaElegida() {
		
	        return tbReservas.getSelectedRowCount() == 0 || tbReservas.getSelectedColumnCount() == 0;
	    }
	
	private void eliminar() {
        if (tieneFilaElegida()) {
            JOptionPane.showMessageDialog(this, "Por favor, elije un item");
            return;
        }
        
        Optional.ofNullable(modeloR.getValueAt(tbReservas.getSelectedRow(), tbReservas.getSelectedColumn()))
                .ifPresentOrElse(fila -> {
                    String id = modeloR.getValueAt(tbReservas.getSelectedRow(), 0).toString();

                    var filasModificadas = this.reservasController.eliminar(id);

                    modeloR.removeRow(tbReservas.getSelectedRow());

                    JOptionPane.showMessageDialog(this,
                            String.format("%d item eliminado con éxito!", filasModificadas));
                }, () -> JOptionPane.showMessageDialog(this, "Por favor, elije un item"));
    }
	
	
	
	 
	  
	private void cargarTablaReservas() {
	        var reservas = this.reservasController.listar();
	        System.out.println("cargando" + reservas);
	        reservas.forEach(reserva -> modeloR.addRow(	        		
	                new Object[] {
	                		reserva.getId(),
	                		reserva.getFechaEntrada(),
	                		reserva.getFechaSalida(),
	                		reserva.getValor(),
	                		reserva.getFormaPago()
	                        }));
	    }
	
	
	private void cargarTablasBusquedaPorId(int idABuscar) {
        
		
    
		var reservas = this.reservasController.buscarId(idABuscar);
		reservas.forEach(reserva ->  modeloR.addRow(new Object[] {
				reserva.getId(),
				reserva.getFechaEntrada(),
				reserva.getFechaSalida(),
				reserva.getValor(),
				reserva.getFormaPago()
                }));
	
                        
    }
	
	
	
	
	
	  
	  
	  /*----------------funciones huesped------------------*/
		
	private void cargarTablasBusquedaPorApe(String apellidoABUscar) {
		System.out.println("por aca");
		
		var huesped = this.huespedController.buscarApellido(apellidoABUscar);
		huesped.forEach(reserva ->  modeloH.addRow(new Object[] {
				reserva.getId(),
				reserva.getNombre(),
				reserva.getApellido(),
				reserva.getFechaDeNacimiento(),
				reserva.getNacionalidad(),
				reserva.getTelefono(),
				reserva.getIdReserva()
                }));
		
	}
	
	
	
		 private void limpiarTablaHuesped() {
			modeloH.getDataVector().clear();
		    }
		 
		 
		 
		private boolean tieneFilaElegidaH() {
			
		        return tbHuespedes.getSelectedRowCount() == 0 || tbHuespedes.getSelectedColumnCount() == 0;
		    }
		
		private void eliminarH() {
	        if (tieneFilaElegidaH()) {
	            JOptionPane.showMessageDialog(this, "Por favor, elije un item");
	            return;
	        }
	        
	        Optional.ofNullable(modeloH.getValueAt(tbHuespedes.getSelectedRow(), tbHuespedes.getSelectedColumn()))
	                .ifPresentOrElse(fila -> {
	                    Integer id = Integer.valueOf(modeloH.getValueAt(tbHuespedes.getSelectedRow(), 0).toString());

	                    var filasModificadas = this.huespedController.eliminar(id);

	                    modeloH.removeRow(tbHuespedes.getSelectedRow());

	                    JOptionPane.showMessageDialog(this,
	                            String.format("%d item eliminado con éxito!", filasModificadas));
	                }, () -> JOptionPane.showMessageDialog(this, "Por favor, elije un item"));
	    }
		
		
		
		  private void cargarTablaH() {
		        var huespedes = this.huespedController.listar();
		        System.out.println("cargando" + huespedes);
		        huespedes.forEach(huesped -> modeloH.addRow(	        		
		                new Object[] {
		                		huesped.getId(),
		                		huesped.getNombre(),
		                		huesped.getApellido(),
		                		huesped.getFechaDeNacimiento(),
		                		huesped.getNacionalidad(),
		                		huesped.getTelefono(),
		                		huesped.getIdReserva()
		                        }));
		    }
		  
		  
		

			public void crearNombresColumnasHuesped() {
				// CREA LOS NOMBRES DE LAS COLUMNAS
				String[] columnas = new String[modeloR.getColumnCount()];
				for (int i = 0; i < modeloR.getColumnCount(); i++) {
					columnas[i] = modeloR.getColumnName(i);
				}
				modeloR.addRow(columnas);
			}

			public void crearNombresColumnasReservas() {
				// CREA LOS NOMBRES DE LAS COLUMNAS
				String[] columnas = new String[modeloH.getColumnCount()];
				for (int i = 0; i < modeloH.getColumnCount(); i++) {
					columnas[i] = modeloH.getColumnName(i);
				}
				modeloH.addRow(columnas);
			}

			private void limpiarTablas() {
//				modeloHuesped.getDataVector().clear();
//				modeloReserva.getDataVector().clear();
				modeloH.setRowCount(0);
				modeloR.setRowCount(0);
			}

		  
		  
	
		  
		  
		  
		  /*------------------Editar------------------*/
		  
		  
			private void editar() {
				if (tbHuespedes.isVisible()) {
					if (tieneFilaElegidaH()) {
						JOptionPane.showMessageDialog(this, "Por favor, elija un item en el huesped para modificarlo");
						tbHuespedes.clearSelection();
						tbReservas.clearSelection();
						return;
					} else {
						int fila = tbHuespedes.getSelectedRow();
						Integer id = Integer.valueOf(tbHuespedes.getValueAt(fila, 0).toString());
						String nombre = tbHuespedes.getValueAt(fila, 1).toString();
						String apellido = tbHuespedes.getValueAt(fila, 2).toString();
						
						Date nacimiento;
						try {
							nacimiento = Date.valueOf(tbHuespedes.getValueAt(fila, 3).toString());
						} catch (DateTimeParseException e) {
							JOptionPane.showMessageDialog(this, "Por favor, asegúrese que el formato de la fecha es el correcto");
							return;
						}
						String nacionalidad = tbHuespedes.getValueAt(fila, 4).toString();
						int telefono = Integer.valueOf(tbHuespedes.getValueAt(fila, 5).toString());
						//String id_reserva = tbHuespedes.getValueAt(fila, 6).toString();
						Huesped huespedModificacion = new Huesped(id, nombre, apellido, nacimiento, nacionalidad, telefono);
						huespedController.modificar(huespedModificacion);
						limpiarTablas();
						cargarTablaH();
						cargarTablaReservas();
						JOptionPane.showMessageDialog(this, "Se modificó con éxito");
					}
				}

				if (tbReservas.isVisible()) {

					if (tieneFilaElegida()) {
						JOptionPane.showMessageDialog(this, "Por favor, elija un item en la reserva para modificar");
						tbHuespedes.clearSelection();
						tbReservas.clearSelection();
						return;
					} else {
						int fila = tbReservas.getSelectedRow();

						String id = tbReservas.getValueAt(fila, 0).toString();
						Date fIngreso;
						Date fEgreso;
						try {
							fIngreso = Date.valueOf(tbReservas.getValueAt(fila, 1).toString());
							
							fEgreso = Date.valueOf(tbReservas.getValueAt(fila, 2).toString());
						} catch (DateTimeParseException e) {
							JOptionPane.showMessageDialog(this, "Por favor, asegúrese que el formato de la fecha es el correcto");
							return;
						}

						int valor = Integer.valueOf(tbReservas.getValueAt(fila, 3).toString());
						String fPago = tbReservas.getValueAt(fila, 4).toString();
						Reserva reserva = new Reserva(id, fIngreso, fEgreso, valor, fPago);
						reservasController.modificar(reserva);
						limpiarTablas();
						cargarTablaReservas();
						cargarTablaH();
						JOptionPane.showMessageDialog(this, "Se modificó con éxito");
						
					}
				}

			}

		  
}
