package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.User;

public class UserDao {

    private final String JDBC_URL = "jdbc:mysql://localhost/library-touroku?useSSL=false&serverTimezone=Asia/Tokyo";
    private final String DB_USER = "root";
    private final String DB_PASS = "";

    // 新規アカウント作成
    public boolean create(User user) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("JDBCドライバの読み込み失敗", e);
        }

        String sql = "INSERT INTO user (name, pass) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getPass());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            // ★ UNIQUE制約違反（名前重複）
            if (e.getSQLState().startsWith("23")) {
                return false;
            }
            throw new RuntimeException(e);
        }
    }

    // 削除
    public boolean deleteByNameAndPass(String name, String pass) {

        String sql = "DELETE FROM user WHERE name = ? AND pass = ?";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, pass);

            return ps.executeUpdate() == 1;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // ログイン
    public boolean login(User user) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("JDBCドライバの読み込み失敗", e);
        }

        String sql = "SELECT name FROM user WHERE name = ? AND pass = ?";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getPass());

            return ps.executeQuery().next();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}