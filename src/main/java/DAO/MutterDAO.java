package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Mutter;
import model.User;

public class MutterDAO {
    private final String JDBC_URL = "jdbc:mysql://localhost/dokotsubu";
    private final String DB_USER = "root";
    private final String DB_PASS = "";

    public List<Mutter> findAll() {
        List<Mutter> mutterList = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // ← 新しいドライバ名に変更（推奨）
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("JDBCドライバの読み込み失敗");
        }

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
            String sql = "SELECT ID, NAME, TEXT FROM MUTTERS ORDER BY ID DESC";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("ID");
                String userName = rs.getString("NAME");
                String text = rs.getString("TEXT");
                Mutter mutter = new Mutter(id, userName, text);
                mutterList.add(mutter);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return mutterList;
    }

    public boolean create(Mutter mutter) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("JDBCドライバの読み込み失敗");
        }

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
            String sql = "INSERT INTO MUTTERS(NAME, TEXT) VALUES(?, ?)";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, mutter.getUserName());
            pStmt.setString(2, mutter.getText());

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

    /**
     * name と pass が一致するユーザーが存在するか確認するメソッド
     * @param user Userオブジェクト（name, passを含む）
     * @return 一致するユーザーがあれば true、なければ false
     */
    public boolean checkUser(User user) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("JDBCドライバの読み込み失敗");
        }

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
            String sql = "SELECT name, pass FROM users WHERE name = ? AND pass = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getPass());

            ResultSet rs = pstmt.executeQuery();

            // 結果が1件でもあればユーザー存在
            if (rs.next()) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
