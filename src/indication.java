import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class indication extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	
	Connection cnx = null;
	PreparedStatement prepared = null;
	ResultSet resultat =null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					indication frame = new indication();
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
	public indication() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 280, 143);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		cnx = ConnectionMysql.Connection(); 
		
		JLabel Nom = new JLabel("Nom");
		Nom.setBounds(39, 27, 61, 16);
		contentPane.add(Nom);
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			
			
				
			@Override
			public void keyReleased(KeyEvent e) {
				String username = textField.getText().toString();
				String sql="select password from utilisateur where nom = ?";
				try {
					prepared  = cnx.prepareStatement(sql);
					prepared.setString(1, username);
					resultat = prepared.executeQuery();
					if(resultat.next()) {
						String pass =resultat.getString("password");
						String pass1=pass.substring(0,2);
						textField_1.setText(" les 2 premier lettres de mot de passe est :" +pass1+ "******");
					}
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		textField.setBounds(127, 22, 130, 26);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(20, 69, 237, 34);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
	}

}
