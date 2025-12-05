package admin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.Mutter;

public class adminDAO {

    private final String JDBC_URL = "jdbc:mysql://localhost/library-touroku";
    private final String DB_USER = "root";
    private final String DB_PASS = "";

    // INSERT処理
    public boolean insert(Mutter mutter) {
    	String sql = "INSERT INTO list (book, number) VALUES (?, ?)";
        try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		}catch(ClassNotFoundException e) {
			throw new IllegalStateException(
				"JDBCドライバを読み込めませんでしたm9(^Д^)ﾌﾟｷﾞｬｰ2");
		}
        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
        	
        	
        	
            
            pstmt.setString(1, mutter.getBook());
            pstmt.setInt(2, mutter.getNumber());

            int result = pstmt.executeUpdate();
            return result == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}