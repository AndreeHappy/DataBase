package ventana;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import conexion.Conexion;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;

import vo.PersonaVo;
import dao.PersonaDao;

import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.SystemColor;


public class VentanaConsulta extends JFrame implements ActionListener {

		private JLabel labelTitulo;
		JTable miTabla1;
		JScrollPane mibarra1;
		private JButton botonAct;
		private JTextField txtCodigo;
		private JTextField txtNombre;
		private JTextField txtProfesion;
		private JTextField txtEdad;
		private JTextField txtTelefono;

		public VentanaConsulta() {
			getContentPane().setBackground(new Color(0, 0, 128));
			getContentPane().setForeground(SystemColor.menu);
			setSize(759, 435);
			setTitle("UNAM : Componentes JTable");
			setLocationRelativeTo(null);
			setResizable(false);
			
			inicializaComponentes();
			construirTabla();
		}

		private void construirTabla() {
			String titulos[]={ "Codigo", "Nombre", "Edad", "Profesión","Telefono" };
			String informacion[][]=obtenerMatriz();
			
			miTabla1=new JTable(informacion,titulos);
			miTabla1.setBackground(new Color(153, 204, 255));
			miTabla1.setFont(new Font("Times New Roman", Font.PLAIN, 13));
			miTabla1.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
	                Conexion con = new Conexion();
	                try {
	                    int fila = miTabla1.getSelectedRow();
	                    int id = Integer.parseInt(miTabla1.getValueAt(fila, 0).toString());
	                    ResultSet rs;
	                    Connection conexion = con.getConnection();
	                    PreparedStatement ps = ((Connection) conexion).prepareStatement("SELECT nombre,edad,profesion,telefono FROM persona WHERE id=?");
	                    ps.setInt(1, id);
	                    rs= ps.executeQuery();
	                    
	                    while (rs.next()) {
	                        txtCodigo.setText(String.valueOf(id));
	                        txtNombre.setText(rs.getString("nombre"));
	                        txtEdad.setText(rs.getString("edad"));
	                        txtProfesion.setText(rs.getString("profesion"));
	                        txtTelefono.setText(rs.getString("telefono"));
	                    }
	                } catch(SQLException e2) {
	                    JOptionPane.showMessageDialog(null, e.toString());
	                }
				}
			});
			mibarra1.setViewportView(miTabla1);
			
		}

		private String[][] obtenerMatriz() {
			
			PersonaDao miPersonaDao=new PersonaDao();
			ArrayList<PersonaVo>miLista=miPersonaDao.buscarUsuariosConMatriz();
			
			String matrizInfo[][]=new String[miLista.size()][5];
			
			for (int i = 0; i < miLista.size(); i++) {
				matrizInfo[i][0]=miLista.get(i).getIdPersona()+"";
				matrizInfo[i][1]=miLista.get(i).getNombrePersona()+"";
				matrizInfo[i][2]=miLista.get(i).getProfesionPersona()+"";
				matrizInfo[i][3]=miLista.get(i).getEdadPersona()+"";
				matrizInfo[i][4]=miLista.get(i).getTelefonoPersona()+"";
			}
				
			return matrizInfo;
		}

		private void inicializaComponentes() {
			getContentPane().setLayout(null);

			labelTitulo = new JLabel();
			labelTitulo.setForeground(new Color(255, 255, 255));
			labelTitulo.setBounds(10, 11, 144, 30);
			labelTitulo.setHorizontalAlignment(SwingConstants.CENTER);
			labelTitulo.setText("REGISTRO:");
			labelTitulo.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 25));
			getContentPane().add(labelTitulo);
			
			mibarra1=new JScrollPane();
			mibarra1.setBounds(299, 50,428,335);
			getContentPane().add(mibarra1);
			
			botonAct = new JButton("Actualizar");
			botonAct.setBackground(new Color(153, 204, 255));
			botonAct.setFont(new Font("Times New Roman", Font.PLAIN, 14));
			botonAct.setBounds(87, 344, 100, 30);
			getContentPane().add(botonAct);
			
			JButton botonBorrar = new JButton("Borrar");
			botonBorrar.setBackground(new Color(153, 204, 255));
			botonBorrar.setFont(new Font("Times New Roman", Font.PLAIN, 14));
			botonBorrar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
	                Conexion con = new Conexion();
	                int id = Integer.parseInt(txtCodigo.getText());
	                
	                try {
	                    Connection conexion = con.getConnection();
	                    PreparedStatement ps = ((Connection) conexion).prepareStatement("DELETE FROM persona WHERE id=?");
	                    ps.setInt(1, id);
	                    ps.executeUpdate();
	                    JOptionPane.showMessageDialog(null, "Registro Eliminado");
	                } catch(SQLException e1) {
	                    JOptionPane.showMessageDialog(null, e1.toString());
	                    
	                }
				}
			});
			botonBorrar.setBounds(152, 294, 105, 30);
			getContentPane().add(botonBorrar);
			
			JButton BotonIns = new JButton("Insertar");
			BotonIns.setBackground(new Color(153, 204, 255));
			BotonIns.setFont(new Font("Times New Roman", Font.PLAIN, 14));
			BotonIns.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Conexion con = new Conexion();
	                int id = Integer.parseInt(txtCodigo.getText());
	                String nombre = txtNombre.getText();
	                String profesion = txtProfesion.getText();
	                int edad = Integer.parseInt(txtEdad.getText());
	                int telefono = Integer.parseInt(txtTelefono.getText());
	                
	                try {
	                    Connection conexion = con.getConnection();
	                    PreparedStatement ps = ((Connection) conexion).prepareStatement("INSERT INTO persona (id,nombre,edad,profesion,telefono) VALUES (?,?,?,?,?)");
	                    ps.setInt(1, id);
	                    ps.setString(2, nombre);
	                    ps.setInt(3, edad);
	                    ps.setString(4, profesion);
	                    ps.setInt(5, telefono);
	                    ps.executeUpdate();
	                    JOptionPane.showMessageDialog(null, "Registro guardado");
	                } catch(SQLException e1) {
	                    JOptionPane.showMessageDialog(null, e1.toString());
	                    
	                }
				}
			});
			BotonIns.setBounds(24, 294, 100, 30);
			getContentPane().add(BotonIns);
			
			JLabel lblNewLabel = new JLabel("Codigo: ");
			lblNewLabel.setForeground(new Color(255, 255, 255));
			lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 15));
			lblNewLabel.setBounds(10, 50, 105, 17);
			getContentPane().add(lblNewLabel);
			
			txtCodigo = new JTextField();
			txtCodigo.setBackground(new Color(153, 204, 255));
			txtCodigo.setBounds(77, 52, 191, 20);
			getContentPane().add(txtCodigo);
			txtCodigo.setColumns(10);
			
			txtNombre = new JTextField();
			txtNombre.setBackground(new Color(153, 204, 255));
			txtNombre.setColumns(10);
			txtNombre.setBounds(77, 99, 191, 20);
			getContentPane().add(txtNombre);
			
			txtProfesion = new JTextField();
			txtProfesion.setBackground(new Color(153, 204, 255));
			txtProfesion.setColumns(10);
			txtProfesion.setBounds(77, 149, 191, 20);
			getContentPane().add(txtProfesion);
			
			txtEdad = new JTextField();
			txtEdad.setBackground(new Color(153, 204, 255));
			txtEdad.setColumns(10);
			txtEdad.setBounds(77, 199, 191, 20);
			getContentPane().add(txtEdad);
			
			txtTelefono = new JTextField();
			txtTelefono.setBackground(new Color(153, 204, 255));
			txtTelefono.setColumns(10);
			txtTelefono.setBounds(77, 250, 191, 20);
			getContentPane().add(txtTelefono);
			
			JLabel lblNombre = new JLabel("Nombre: ");
			lblNombre.setForeground(new Color(255, 255, 255));
			lblNombre.setFont(new Font("Times New Roman", Font.PLAIN, 15));
			lblNombre.setBounds(10, 100, 105, 17);
			getContentPane().add(lblNombre);
			
			JLabel lblProfesion = new JLabel("Profesion: ");
			lblProfesion.setForeground(new Color(255, 255, 255));
			lblProfesion.setFont(new Font("Times New Roman", Font.PLAIN, 15));
			lblProfesion.setBounds(10, 150, 105, 17);
			getContentPane().add(lblProfesion);
			
			JLabel lblEdad = new JLabel("Edad: ");
			lblEdad.setForeground(new Color(255, 255, 255));
			lblEdad.setFont(new Font("Times New Roman", Font.PLAIN, 15));
			lblEdad.setBounds(10, 200, 105, 17);
			getContentPane().add(lblEdad);
			
			JLabel lblTelefono = new JLabel("Telefono: ");
			lblTelefono.setForeground(new Color(255, 255, 255));
			lblTelefono.setFont(new Font("Times New Roman", Font.PLAIN, 15));
			lblTelefono.setBounds(10, 250, 105, 17);
			getContentPane().add(lblTelefono);
			botonAct.addActionListener(this);
			
			
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource()==botonAct) {
				construirTabla();
			}
		}
	
}
