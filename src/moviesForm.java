import java.awt.EventQueue;


import javax.swing.JFrame;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import com.toedter.calendar.JDateChooser;
import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class moviesForm extends JFrame{

	private JFrame frame;
	private JTextField movieNameText;
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
					moviesForm window = new moviesForm();
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
	public moviesForm() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		cnx = ConnectionMysql.Connection();
		
		JLabel lblMovieInfos = new JLabel("Movie Infos");
		lblMovieInfos.setFont(new Font("Monaco", Font.PLAIN, 14));
		lblMovieInfos.setBounds(35, 23, 122, 16);
		frame.getContentPane().add(lblMovieInfos);
		
		movieNameText = new JTextField();
		movieNameText.setBounds(139, 61, 149, 26);
		frame.getContentPane().add(movieNameText);
		movieNameText.setColumns(10);
		
		
		JLabel movieNameLabel = new JLabel("Movie Name");
		movieNameLabel.setBounds(35, 66, 92, 16);
		frame.getContentPane().add(movieNameLabel);
		
		JLabel releaseDateLabel = new JLabel("Release Date");
		releaseDateLabel.setBounds(35, 132, 92, 16);
		frame.getContentPane().add(releaseDateLabel);
		
		JButton saveButton = new JButton("Save");
		
		saveButton.setBounds(139, 227, 75, 29);
		frame.getContentPane().add(saveButton);
		
		JButton clearButton = new JButton("Clear");
		
		clearButton.setBounds(213, 227, 75, 29);
		frame.getContentPane().add(clearButton);
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(139, 132, 149, 26);
		frame.getContentPane().add(dateChooser);
		saveButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				String sql = "insert into movies (movieName,releaseDate) values(?,?)";

				try {
					prepared  = cnx.prepareStatement(sql);
					prepared.setString(1,movieNameText.getText().toString());
					SimpleDateFormat sdf = new SimpleDateFormat();
					String date = sdf.format(dateChooser.getDate());
					prepared.setString(2,date);

					prepared.execute();
					JOptionPane.showMessageDialog(null, "Movie added succesfully");
					movieNameText.setText("");
					((JTextField)dateChooser.getDateEditor().getUiComponent()).setText("");					
					
					
				}catch(Exception ex)
				{
					JOptionPane.showMessageDialog(null , ex);
				}
				
			}
		});
		
clearButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				movieNameText.setText("");
				((JTextField)dateChooser.getDateEditor().getUiComponent()).setText("");	
			}
		});
	}
}
