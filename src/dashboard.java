import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Window;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.CardLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JSplitPane;
import javax.swing.UIManager;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import com.toedter.calendar.JDateChooser;

import net.proteanit.sql.DbUtils;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import java.awt.GridLayout;

public class dashboard extends JFrame{

	private JPanel contentPane;
	protected Window moviesContent;
	protected Window exitContent;
	protected Window managementContent;
	private JTextField movieNameText;
	Connection cnx = null;
	PreparedStatement prepared = null;
	ResultSet resultat =null;
	private JTextField movieImageText;
	private JTable table;
	static int totalMovies = 0;
	int rows;
	int cols;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					dashboard frame = new dashboard();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @return 
	 */
	
	
	
	public int getTotal(){
		int totalMovies =0;
		cnx = ConnectionMysql.Connection();
		String sql = "select COUNT(*) from movies";

		try {
			prepared  = cnx.prepareStatement(sql);
			ResultSet rs = prepared.executeQuery();
			
			while(rs.next()){
		        totalMovies = rs.getInt("count(*)");
		    }
		}catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null , ex);
		}
		return totalMovies;
	}
	public void LoadData() {
		cnx = ConnectionMysql.Connection();
		String sql = "select id , movieName , releaseDate from movies";

		try {
			prepared  = cnx.prepareStatement(sql);
			ResultSet rs = prepared.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(rs));

		}catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null , ex);
		}	
	}
	public dashboard() {
		getTotal();
		CardLayout c1 = new CardLayout();
		cnx = ConnectionMysql.Connection();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 880);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel sidePanel = new JPanel();
		sidePanel.setBackground(Color.DARK_GRAY);
		sidePanel.setBounds(6, 6, 205, 761);
		contentPane.add(sidePanel);
		sidePanel.setLayout(null);
		
		JButton moviesButton = new JButton("Movies");
		moviesButton.setBounds(43, 102, 117, 29); 
		sidePanel.add(moviesButton);
		
		JButton manageButton = new JButton("Management");
		manageButton.setBounds(43, 201, 117, 29); 
		sidePanel.add(manageButton);
		
		JButton exitButton = new JButton("Exit");

		exitButton.setBounds(43, 301, 117, 29);
		sidePanel.add(exitButton);
		
		JPanel pnlCards = new JPanel();
		pnlCards.setBackground(Color.LIGHT_GRAY);
		pnlCards.setBounds(211, 6, 754, 750);
		contentPane.add(pnlCards);
		pnlCards.setLayout(c1);
		
		rows = (getTotal() / 4) + 1;
		cols = (getTotal() /2 ) +1;
		
		JPanel moviesPanel = new JPanel();
		pnlCards.add(moviesPanel, "Movies");
		moviesPanel.setBackground(Color.LIGHT_GRAY);
		moviesPanel.setLayout(null);
		
		JLabel movieNameLabel = new JLabel("Movie Name");
		movieNameLabel.setBounds(30, 99, 100, 16);
		moviesPanel.add(movieNameLabel);
		
		JLabel moviesInfoLabel = new JLabel("Movies Info");
		moviesInfoLabel.setFont(new Font("Monaco", Font.PLAIN, 14));
		moviesInfoLabel.setBounds(269, 5, 88, 19);
		
		
		moviesPanel.add(moviesInfoLabel);
		
		movieNameText = new JTextField();
		movieNameText.setBounds(131, 94, 130, 26);
		moviesPanel.add(movieNameText);
		movieNameText.setColumns(10);
		
		
		
		JLabel releaseDateLabel = new JLabel("Release Date");
		releaseDateLabel.setBounds(30, 169, 100, 16);
		moviesPanel.add(releaseDateLabel);
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(138, 169, 123, 26);
		moviesPanel.add(dateChooser);
		
		JButton saveButton = new JButton("Save");
		saveButton.setBounds(131, 367, 75, 29);
		moviesPanel.add(saveButton);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setBounds(310, 367, 117, 29);
		moviesPanel.add(btnDelete);
		JButton btnUpdate = new JButton("Update");
		btnUpdate.setBounds(453, 367, 117, 29);
		moviesPanel.add(btnUpdate);
		
		JButton clearButton = new JButton("Clear");
		clearButton.setBounds(219, 367, 75, 29);
		moviesPanel.add(clearButton);
		
		JLabel movieImageLabel = new JLabel("Movie Image");
		movieImageLabel.setBounds(30, 256, 100, 16);
		moviesPanel.add(movieImageLabel);
		
		movieImageText = new JTextField();
		movieImageText.setColumns(10);
		movieImageText.setBounds(131, 251, 130, 26);
		moviesPanel.add(movieImageText);
		
		JButton btnImport = new JButton("import...");
		
				btnImport.setBounds(269, 251, 117, 29);
				moviesPanel.add(btnImport);
				
				JLabel imageLabel = new JLabel("");
				imageLabel.setBounds(279, 46, 148, 200);
				moviesPanel.add(imageLabel);
				
				JScrollPane scrollPane = new JScrollPane();
				scrollPane.setBounds(30, 434, 489, 214);
				moviesPanel.add(scrollPane);
				
				table = new JTable();
				scrollPane.setViewportView(table);
				

				JPanel listPanel = new JPanel();
				pnlCards.add(listPanel, "List");
				listPanel.setBackground(Color.LIGHT_GRAY);
				listPanel.setLayout(null);
				
				JButton getMovies = new JButton("getMovies");
				getMovies.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						cnx = ConnectionMysql.Connection();
						String sql = "select * from movies";
						
						try {
							prepared  = cnx.prepareStatement(sql);
							ResultSet rs = prepared.executeQuery();
							while(rs.next()){
								JButton jb = new JButton(rs.getString("movieName"));
								jb.setBounds(30, 99, 100, 16);
								listPanel.add(jb);
						    }
							
						}
						catch (SQLException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
					}
				});
				getMovies.setBounds(38, 18, 117, 29);
				listPanel.add(getMovies);
				clearButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						movieNameText.setText("");
						movieImageText.setText("");
						imageLabel.setIcon(null);
						((JTextField)dateChooser.getDateEditor().getUiComponent()).setText("");
					}
				});
				btnImport.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						JFileChooser chooser = new JFileChooser();
						chooser.showOpenDialog(null);
						File file = chooser.getSelectedFile();
						String filename = file.getAbsolutePath();
						movieImageText.setText(filename);
						Image getAbsolutePath = null;
						ImageIcon icon = new ImageIcon(filename);
						Image image = icon.getImage().getScaledInstance(imageLabel.getWidth(),imageLabel.getHeight(),Image.SCALE_SMOOTH);
						imageLabel.setIcon(icon);
						
					}
				});
				table.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						int line = table.getSelectedRow(); 
						String movieName = table.getModel().getValueAt(line, 1).toString();
						Date release = (Date) table.getModel().getValueAt(line, 2);
						movieNameText.setText(movieName);
						dateChooser.setDate(release);
						
					}
				});
				
				
				// CRUD Oerations
				btnUpdate.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String Name = movieNameText.getText().toString();
						int line = table.getSelectedRow();
						int ID =(int) table.getModel().getValueAt(line, 0);
						String sql="update movies set movieName = ? , releaseDate=? where id = ?";
						try {
							prepared  = cnx.prepareStatement(sql);
							prepared.setString(1,movieNameText.getText().toString());
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
							String date = sdf.format(dateChooser.getDate());
							prepared.setString(2,date);
							prepared.setInt(3,ID);
							prepared.execute();
							JOptionPane.showMessageDialog(null, "Movie info modified");
							movieNameText.setText("");
							movieImageText.setText("");
							imageLabel.setIcon(null);
							((JTextField)dateChooser.getDateEditor().getUiComponent()).setText("");
							LoadData();
							
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
						
					}
				});
				
				
				saveButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						String sql = "insert into movies (movieName,releaseDate,movieImage) values(?,?,?)";

						try {
							prepared  = cnx.prepareStatement(sql);
							prepared.setString(1,movieNameText.getText().toString());
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
							String date = sdf.format(dateChooser.getDate());
							prepared.setString(2,date);
							String image = movieImageText.getText();
							image = image.replace("\\","\\\\");
							prepared.setString(3, image);
							prepared.execute();
							JOptionPane.showMessageDialog(null, "Movie added succesfully");
							totalMovies++;
							movieNameText.setText("");
							movieImageText.setText("");
							imageLabel.setIcon(null);
							((JTextField)dateChooser.getDateEditor().getUiComponent()).setText("");		
							LoadData();
							
							
						}catch(Exception ex)
						{
							JOptionPane.showMessageDialog(null , ex);
						}
					}
				});
				
				
				btnDelete.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						int option = JOptionPane.showConfirmDialog(null,"Are you sure you wanna delete?","Delete",JOptionPane.YES_NO_OPTION);
						if(option == 0) { 
							int line = table.getSelectedRow(); 
							int ID =(int) table.getModel().getValueAt(line, 0);
							String sql="delete from movies where id = ? ";
							try {
								
								prepared  = cnx.prepareStatement(sql);
								prepared.setInt(1,ID);
								prepared.execute();
								totalMovies--;
								JOptionPane.showMessageDialog(null, "Movie deleted");
								LoadData();
								
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
						
						
					}
				});
		moviesButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				c1.show(pnlCards,"List");
			}
		});
		manageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoadData();
				c1.show(pnlCards,"Movies");
			}
		});
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 System.exit(0);
			}
		});
		// End of CRUD Operations
		
	}

}
