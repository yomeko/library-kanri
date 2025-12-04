package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.User;

public class newAcountDAO {
    // 接続情報
<<<<<<< HEAD
    private final String JDBC_URL = "jdbc:mysql://localhost/library-touroku";
=======
    private final String JDBC_URL = "jdbc:mysql://localhost/library";
>>>>>>> a4e982cec2784693189eff73c62399b277e8aa49
    private final String DB_USER = "root";
    private final String DB_PASS = "";

    public boolean create(User user) {
        try {
            // JDBCドライバの読み込み
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("JDBCドライバの読み込み失敗");
        }

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
            String sql = "INSERT INTO users (name, pass) VALUES (?, ?)";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, user.getName());
            pStmt.setString(2, user.getPass());
            
            int result = pStmt.executeUpdate();
            if (result != 1) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
