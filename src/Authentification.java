import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.BorderLayout;
import javax.swing.JTextField;



import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Authentification extends JFrame{

	private JFrame frame;
	private JTextField textField;
	Connection cnx = null;
	PreparedStatement prepared = null;
	ResultSet resultat =null;
	private JPasswordField passwordField;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Authentification window = new Authentification();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Authentification() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 517, 403);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		cnx = ConnectionMysql.Connection();
		frame.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(190, 30, 211, 34);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel Nom = new JLabel("Nom");
		Nom.setBounds(77, 43, 61, 16);
		Nom.setFont(new Font(".SF NS Text", Font.BOLD, 13));
		frame.getContentPane().add(Nom);
		
		JLabel Password = new JLabel("Password");
		Password.setBounds(77, 98, 81, 16);
		Password.setFont(new Font(".SF NS Text", Font.BOLD, 13));
		frame.getContentPane().add(Password);
		
		JButton btnNewButton = new JButton("cliquer ici");
		btnNewButton.setBounds(155, 161, 185, 52);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String Username = textField.getText().toString();
				String motdepasse= passwordField.getText().toString();
				String sql ="select nom, password from utilisateur";
				boolean var=false;
				try {
					prepared  = cnx.prepareStatement(sql);
					resultat = prepared.executeQuery();
					 if(Username.equals("")|| motdepasse.equals("")) {
						JOptionPane.showMessageDialog(null, "les champs sont vide ");}
					 else {
						
						while(resultat.next()) {
							String Username1 = resultat.getString("nom");
							String motdepasse1= resultat.getString("password");
							 if(Username1.equals(Username)&& motdepasse1.equals(motdepasse)) {
								 JOptionPane.showMessageDialog(null,"connection réussite");
								 var=true;
								 textField.setText("");
									passwordField.setText("");}}
						if(var==false) {
							JOptionPane.showMessageDialog(null, "vos données sont fausses");
							textField.setText("");
							passwordField.setText("");}
						
					 	 }
						
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		frame.getContentPane().add(btnNewButton);
		
		JLabel motdepasseoblié = new JLabel("Mot de passe oublié");
		motdepasseoblié.setBounds(333, 131, 92, 16);
		motdepasseoblié.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				indication n =new indication();
				n.setVisible(true);
				n.setLocationRelativeTo(null);
			}
		});
		motdepasseoblié.setFont(new Font("Lucida Grande", Font.PLAIN, 9));
		frame.getContentPane().add(motdepasseoblié);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(190, 77, 211, 42);
		frame.getContentPane().add(passwordField);
		
		JLabel lblNewLabel = new JLabel("s'inscrire");
		lblNewLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				GestionUser a =new GestionUser();
				a.setVisible(true);
				a.setLocationRelativeTo(null);
				
			}
		});
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 9));
		lblNewLabel.setBounds(200, 131, 61, 16);
		frame.getContentPane().add(lblNewLabel);
	}
}
