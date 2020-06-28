import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class indicationpassword extends JFrame {

	private JPanel contentPane;
	private JTextField Nome;
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
					indicationpassword frame = new indicationpassword();
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
	public indicationpassword() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 307, 175);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		cnx = ConnectionMysql.Connection(); 
		
		JLabel lblNewLabel = new JLabel("Nom ");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblNewLabel.setBounds(38, 35, 61, 16);
		contentPane.add(lblNewLabel);
		
		Nome =  new JTextField();
		Nome.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String Nom = Nome.getText().toString();
				String sql="select password from utilisateur where nom=?";
				try {
					prepared  = cnx.prepareStatement(sql);
					prepared.setString(1, Nom);
					resultat = prepared.executeQuery();
					String pass =resultat.getString("password");
					String pass1=pass.substring(0,2);
				
				} catch (SQLException en) {
					// TODO Auto-generated catch block
					en.printStackTrace();}
			}
		});
		Nome.setBounds(124, 31, 130, 26);
		contentPane.add(Nome);
		Nome.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(28, 69, 256, 63);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
	}

}
