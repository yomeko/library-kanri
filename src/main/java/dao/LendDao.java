package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LendDao {

    private static final String URL =
        "jdbc:mysql://localhost:3306/library-touroku?useSSL=false&serverTimezone=Asia/Tokyo&characterEncoding=UTF-8";
    private static final String USER = "root";
    private static final String PASS = "";

    // 残り貸出数取得
    public int getRemainLend(String userName) {
        String sql = "SELECT lend FROM user WHERE name = ?";
        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, userName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("lend");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // すでに借りているか
    public boolean isAlreadyLent(String userName, int bookId) {
        String sql = "SELECT COUNT(*) FROM rental WHERE user_name = ? AND book_id = ?";
        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, userName);
            ps.setInt(2, bookId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 貸出処理（トランザクション）
    public boolean lendBook(String userName, int bookId) {
        Connection con = null;
        try {
            con = DriverManager.getConnection(URL, USER, PASS);
            con.setAutoCommit(false);

            // 在庫確認
            String stockSql = "SELECT number FROM list WHERE id = ? FOR UPDATE";
            try (PreparedStatement ps = con.prepareStatement(stockSql)) {
                ps.setInt(1, bookId);
                ResultSet rs = ps.executeQuery();
                if (!rs.next() || rs.getInt("number") <= 0) {
                    con.rollback();
                    return false;
                }
            }

            // rental 追加
            String insertRental =
                "INSERT INTO rental (user_name, book_id) VALUES (?, ?)";
            try (PreparedStatement ps = con.prepareStatement(insertRental)) {
                ps.setString(1, userName);
                ps.setInt(2, bookId);
                ps.executeUpdate();
            }

            // 在庫 -1
            String updateStock =
                "UPDATE list SET number = number - 1 WHERE id = ?";
            try (PreparedStatement ps = con.prepareStatement(updateStock)) {
                ps.setInt(1, bookId);
                ps.executeUpdate();
            }

            // lend -1
            String updateUser =
                "UPDATE user SET lend = lend - 1 WHERE name = ? AND lend > 0";
            try (PreparedStatement ps = con.prepareStatement(updateUser)) {
                ps.setString(1, userName);
                if (ps.executeUpdate() == 0) {
                    con.rollback();
                    return false;
                }
            }

            con.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            try {
                if (con != null) con.rollback();
            } catch (SQLException ex) {}
        } finally {
            try {
                if (con != null) con.setAutoCommit(true);
            } catch (SQLException e) {}
        }
        return false;
    }
    
    public boolean isAlreadyLent(String name, String bookname) {

        String sql =
            "SELECT COUNT(*) FROM lend WHERE name = ? AND bookname = ?";

        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, bookname);

            ResultSet rs = ps.executeQuery();
            rs.next();

            return rs.getInt(1) > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return true; // 安全側（借りさせない）
        }
    }
    
}