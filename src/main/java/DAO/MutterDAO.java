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
    private final String JDBC_URL = "jdbc:mysql://localhost/library-touroku";
    private final String DB_USER = "root";
    private final String DB_PASS = "";

    // --------------------------------------------------
    // list テーブル全件取得（id DESC）
    // --------------------------------------------------
    public List<Mutter> findAll() {
        List<Mutter> mutterList = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("JDBCドライバ読み込み失敗");
        }

        String sql = "SELECT id, number, book FROM list ORDER BY id DESC";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
             PreparedStatement pStmt = conn.prepareStatement(sql);
             ResultSet rs = pStmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                int number = rs.getInt("number");
                String book = rs.getString("book");

                Mutter mutter = new Mutter(id, number, book);
                mutterList.add(mutter);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return mutterList;
    }

    // --------------------------------------------------
    // list テーブルへINSERT
    // --------------------------------------------------
    public boolean create(Mutter mutter) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("JDBCドライバ読み込み失敗");
        }

        String sql = "INSERT INTO list(number, book) VALUES(?, ?)";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
             PreparedStatement pStmt = conn.prepareStatement(sql)) {

            pStmt.setInt(1, mutter.getNumber());
            pStmt.setString(2, mutter.getBook());

            int result = pStmt.executeUpdate();
            return result == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // --------------------------------------------------
    // user テーブルのログイン認証
    // --------------------------------------------------
    public boolean checkUser(User user) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("JDBCドライバ読み込み失敗");
        }

        String sql = "SELECT id FROM user WHERE name = ? AND pass = ?";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getPass());

            ResultSet rs = pstmt.executeQuery();

            return rs.next(); // 一件でもあれば成功

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}