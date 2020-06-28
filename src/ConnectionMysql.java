import javax.swing.*;
import java.sql.*;
public class ConnectionMysql {
	Connection conn = null;
	public static Connection Connection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/GestionCinema" ,"root","redaoupsilava");
			return conn;
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null,e);
			return null;
		}
	}
}