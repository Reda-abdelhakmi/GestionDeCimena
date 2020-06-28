import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GestionUser extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
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
					GestionUser frame = new GestionUser();
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
	public GestionUser() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		cnx = ConnectionMysql.Connection();
		
		JLabel lblNewLabel = new JLabel("Nom");
		lblNewLabel.setBounds(58, 61, 61, 16);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				String username = textField.getText().toString();
				String sql="select * from utilisateur where nom = ?";
				try {
					prepared  = cnx.prepareStatement(sql);
					prepared.setString(1, username);
					resultat = prepared.executeQuery();
					if(resultat.next()) {
						String pass =resultat.getString("password");
					
						passwordField.setText( pass);
					}
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		});
		textField.setBounds(197, 56, 130, 26);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Password");
		lblNewLabel_1.setBounds(58, 118, 61, 16);
		contentPane.add(lblNewLabel_1);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(197, 113, 145, 26);
		contentPane.add(passwordField);
		
		JButton btnNewButton = new JButton("Ajouter");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sql="insert into utilisateur( nom,password) values (?,?)";
				try {
					prepared  = cnx.prepareStatement(sql);
					prepared.setString(1,textField.getText().toString());
					prepared.setString(2,passwordField.getText().toString());
					prepared.execute();
					JOptionPane.showMessageDialog(null, "utilisateur ajouter");
					textField.setText("");
					passwordField.setText("");
				}
				catch (SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(158, 166, 130, 41);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("suprimer");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = textField.getText().toString();
				String sql="delete from utilisateur where nom = ? ";
				try {
					prepared  = cnx.prepareStatement(sql);
					prepared.setString(1,textField.getText().toString());
					
					prepared.execute();
					JOptionPane.showMessageDialog(null, "utilisateur suprimé");
					textField.setText("");
					passwordField.setText("");
					
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
				
			
		});
		btnNewButton_1.setBounds(6, 166, 136, 41);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("modifier");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = textField.getText().toString();
				String sql="update utilisateur set password = ? where nom = ?";
				try {
					prepared  = cnx.prepareStatement(sql);
					prepared.setString(2,textField.getText().toString());
					prepared.setString(1,passwordField.getText().toString());
					prepared.execute();
					JOptionPane.showMessageDialog(null, "utilisateur modifié");
					textField.setText("");
					passwordField.setText("");
					
					
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
				
			
		});
		btnNewButton_2.setBounds(307, 166, 132, 39);
		contentPane.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("actualisé");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText("");
				passwordField.setText("");
			}
		});
		btnNewButton_3.setBounds(168, 226, 117, 29);
		contentPane.add(btnNewButton_3);
	}

}
