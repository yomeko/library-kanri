package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.User;

public class deleteAcountDAO {
	private final String JDBC_URL = "jdbc:mysql://localhost/library-touroku";
	private final String DB_USER ="root";
	private final String DB_PASS = "";
	public boolean deletem(User user) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException e) {
			throw new IllegalStateException("JDBCドライバが読み込めませんでした");
		}
		try (Connection conn = DriverManager.getConnection(JDBC_URL,DB_USER,DB_PASS);
			 PreparedStatement pStmt = conn.prepareStatement("DELETE FROM user WHERE name=? AND pass=?")){
			
			pStmt.setString(1, user.getName());
			pStmt.setString(2, user.getPass());
			
			int result = pStmt.executeUpdate();
			if(result != 1) {
				return false;
			}
		}catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}

