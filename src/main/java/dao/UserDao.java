package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.User;

public class UserDao {

    // JDBC接続URL
    private final String JDBC_URL =
            "jdbc:mysql://localhost/library-touroku?useSSL=false&serverTimezone=Asia/Tokyo";
    // DBユーザー名
    private final String DB_USER = "root";
    // DBパスワード
    private final String DB_PASS = "";

    // 新規ユーザー登録
    public boolean create(User user) {

        // JDBCドライバ読み込み
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("JDBCドライバの読み込み失敗", e);
        }

        String sql = "INSERT INTO user (name, pass) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // ユーザー名設定
            ps.setString(1, user.getName());
            // パスワード設定
            ps.setString(2, user.getPass());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            // ユーザー名重複（UNIQUE制約違反）
            if (e.getSQLState().startsWith("23")) {
                return false;
            }
            throw new RuntimeException(e);
        }
    }

    // ユーザー削除
    public boolean deleteByNameAndPass(String name, String pass) {

        String sql = "DELETE FROM user WHERE name = ? AND pass = ?";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // ユーザー名設定
            ps.setString(1, name);
            // パスワード設定
            ps.setString(2, pass);

            // 削除件数が1件なら成功
            return ps.executeUpdate() == 1;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // ログイン認証
    public boolean login(User user) {

        // JDBCドライバ読み込み
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("JDBCドライバの読み込み失敗", e);
        }

        String sql = "SELECT name FROM user WHERE name = ? AND pass = ?";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // ユーザー名設定
            ps.setString(1, user.getName());
            // パスワード設定
            ps.setString(2, user.getPass());

            // 該当レコードがあればログイン成功
            return ps.executeQuery().next();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}