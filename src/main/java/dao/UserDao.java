package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.User;

public class UserDao {
	// 接続情報
    private final String JDBC_URL = "jdbc:mysql://localhost/library-touroku";
    private final String DB_USER = "root";
    private final String DB_PASS = "";
    
    public boolean create(User user) {
    	//新規アカウント作成DAO
        try {
            // JDBCドライバの読み込み
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("JDBCドライバの読み込み失敗");
        }

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
            String sql = "INSERT INTO user (name, pass) VALUES (?, ?)";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, user.getName());
            pStmt.setString(2, user.getPass());
            
            System.out.println(user.getName() + " newAcountDAO.java");
            System.out.println(user.getPass() + " newAcountDAO.java");
            
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
    
    public boolean deleteByNameAndPass(String name, String pass) {

        String sql = "DELETE FROM user WHERE name = ? AND pass = ?";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, pass);

            int result = ps.executeUpdate();
            return result == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean login(User user) {
        // ログイン判定DAO
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("JDBCドライバの読み込み失敗");
        }

        String sql = "SELECT name FROM user WHERE name = ? AND pass = ?";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
             PreparedStatement pStmt = conn.prepareStatement(sql)) {

            pStmt.setString(1, user.getName());
            pStmt.setString(2, user.getPass());

            // SELECTなので executeQuery
            return pStmt.executeQuery().next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    
    }
}