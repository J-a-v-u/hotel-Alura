package views;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.SystemColor;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.JTextField;
import com.toedter.calendar.JDateChooser;

import jdbc.controller.ReservasController;
import jdbc.modelo.Reserva;

import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import java.awt.Window;
import java.time.temporal.ChronoUnit;
import java.time.LocalDate;  
import java.time.format.DateTimeFormatter;
import java.time.Duration;
import java.beans.PropertyChangeSupport;
import java.text.Format;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

@SuppressWarnings("serial")
public class Reservas extends JFrame {
	
	private JPanel contentPane;
	private JTextField txtValor;
	
	private JDateChooser txtFechaE;
	private JDateChooser txtFechaS;
	private JComboBox<Format> txtFormaPago;
	private ReservasController reservasController;
	private String totalAPagar;
	public static String idReserva;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Reservas frame = new Reservas();
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
	public Reservas() {
		this.reservasController = new ReservasController();
		setIconImage(Toolkit.getDefaultToolkit().getImage(Reservas.class.getResource("/imagenes/calendario.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 910, 540);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.control);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(245,245,245));
		panel.setBounds(0, 0, 900, 502);
		contentPane.add(panel);
		panel.setLayout(null);
		/*-----------ENTRADA-------------------*/
		txtFechaE = new JDateChooser();
		txtFechaE.setDateFormatString("yyyy-MM-dd");
		txtFechaE.setBounds(88, 166, 235, 33);
		panel.add(txtFechaE);
		
		JLabel lblNewLabel_1 = new JLabel("Fecha de Check In");
		lblNewLabel_1.setBounds(88, 142, 133, 14);
		lblNewLabel_1.setFont(new Font("Arial", Font.PLAIN, 14));
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Fecha de Check Out");
		lblNewLabel_1_1.setBounds(88, 210, 133, 14);
		lblNewLabel_1_1.setFont(new Font("Arial", Font.PLAIN, 14));
		panel.add(lblNewLabel_1_1);
		
		/*-----------------SALIDA------------------*/
		txtFechaS = new JDateChooser();
		txtFechaS.setDateFormatString("yyyy-MM-dd");
		txtFechaS.setBounds(88, 234, 235, 33);
		txtFechaS.getCalendarButton().setBackground(Color.WHITE);
		panel.add(txtFechaS);
		
		/*--------------listener---------*/
		// Listen for property changes
		
		txtFechaS.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
		    public void propertyChange(java.beans.PropertyChangeEvent evt) {
		
			
		    Date fechaEntrada = txtFechaE.getDate();
			Date fechaSalida = txtFechaS.getDate();
			
			try {
				Duration duration = Duration.between(fechaEntrada.toInstant(), fechaSalida.toInstant());
				long precio = duration.toDays() * 2000;
				totalAPagar = Long.toString(precio);
				txtValor.setText(totalAPagar + " AR$");
				//txtValor.setEnabled(true);
			System.out.println(totalAPagar);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			
		    
		    
		    
		    };
		});
		//
		/*------------valor--------------*/
		
		txtValor = new JTextField();
		txtValor.setBounds(88, 303, 235, 33);
		txtValor.setEnabled(false);
		panel.add(txtValor);
		txtValor.setColumns(10);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Valor de la Reserva");
		lblNewLabel_1_1_1.setBounds(88, 278, 133, 14);
		lblNewLabel_1_1_1.setFont(new Font("Arial", Font.PLAIN, 14));
		panel.add(lblNewLabel_1_1_1);
		
		/*------------forma de pago-------------*/
		
		txtFormaPago = new JComboBox();
		txtFormaPago.setBounds(88, 373, 235, 33);
		txtFormaPago.setFont(new Font("Arial", Font.PLAIN, 14));
		txtFormaPago.setModel(new DefaultComboBoxModel(new String[] {"Tarjeta de Crédito", "Tarjeta de Débito", "Dinero en efectivo"}));
		panel.add(txtFormaPago);
		
		JLabel lblNewLabel_1_1_1_1 = new JLabel("Forma de pago");
		lblNewLabel_1_1_1_1.setBounds(88, 347, 133, 24);
		lblNewLabel_1_1_1_1.setFont(new Font("Arial", Font.PLAIN, 14));
		panel.add(lblNewLabel_1_1_1_1);
		
		JLabel lblNewLabel_4 = new JLabel("Sistema de Reservas");
		lblNewLabel_4.setBounds(108, 93, 199, 42);
		lblNewLabel_4.setForeground(new Color(65, 105, 225));
		lblNewLabel_4.setFont(new Font("Arial", Font.BOLD, 20));
		panel.add(lblNewLabel_4);
		
		/*----------------boton reservar---------------*/
		JButton btnReservar = new JButton("Continuar");
		btnReservar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				guardar();
				/*
				Date fechaEntrada = txtFechaE.getDate();
				Date fechaSalida = txtFechaS.getDate();
				
				//
				//	int diferencia = ChronoUnit.DAYS.between(fechaEntrada.parse, fechaSalida)
				Duration duration = Duration.between(fechaEntrada.toInstant(), fechaSalida.toInstant());
				
				double precio = duration.toDays() * 2000;
				System.out.println(precio);
				*/
				
				/*CODIGO IR A REGISTRO HUESPED
				RegistroHuesped huesped = new RegistroHuesped();
				huesped.setVisible(true);
				dispose();
				*/
			}
		});
		btnReservar.setForeground(Color.WHITE);
		btnReservar.setBounds(183, 436, 140, 33);
		btnReservar.setIcon(new ImageIcon(Reservas.class.getResource("/imagenes/calendario.png")));
		btnReservar.setBackground(new Color(65,105,225));
		btnReservar.setFont(new Font("Arial", Font.PLAIN, 14));
		panel.add(btnReservar);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel_1.setBounds(399, 0, 491, 502);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(0, -16, 500, 539);
		panel_1.add(lblNewLabel);
		lblNewLabel.setBackground(Color.WHITE);
		lblNewLabel.setIcon(new ImageIcon(Reservas.class.getResource("/imagenes/reservas-img-2.png")));
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(Reservas.class.getResource("/imagenes/Ha-100px.png")));
		lblNewLabel_2.setBounds(15, 6, 104, 107);
		panel.add(lblNewLabel_2);
	}
	/*-------------f------------------*/
	
	private void guardar() {
		int valorNumerico = Integer.valueOf(totalAPagar);
		if (txtFechaE.getDate() != null && txtFechaS.getDate() != null) {
			String fechaE = ((JTextField)txtFechaE.getDateEditor().getUiComponent()).getText();
			String fechaS = ((JTextField)txtFechaS.getDateEditor().getUiComponent()).getText();
			Reserva reserva = new Reserva(java.sql.Date.valueOf(fechaE), java.sql.Date.valueOf(fechaS), valorNumerico, txtFormaPago.getSelectedItem().toString());
			this.reservasController.guardar(reserva);
			JOptionPane.showMessageDialog(this, "Registro guardado exitosamente! Por favor ahora ingrese sus datos.");
			
			idReserva = reserva.id;
			System.out.println(idReserva);
			
			limpiar();
			RegistroHuesped huesped = new RegistroHuesped();
			huesped.setVisible(true);
			dispose();
		} else {
			JOptionPane.showMessageDialog(this, "Completa las fechas de entrada y/o salida por favor.");
		}
	}
	
	private void limpiar() {
		this.txtValor.setText("");
		this.txtFormaPago.setSelectedIndex(0);
		this.txtFechaE.setCalendar(null);
		this.txtFechaS.setCalendar(null);		
	}
	
	
	
	/*-------------f------------------*/
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
