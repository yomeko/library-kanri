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

    // ==============================
    // DB接続情報（完全版）
    // ==============================
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/library-touroku";

    private static final String DB_USER = "root";
    private static final String DB_PASS = "";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("JDBCドライバ読み込み失敗", e);
        }
    }

    // ==================================================
    // listテーブル：全件取得（id DESC）
    // ==================================================
    public List<Mutter> findAll() {
        List<Mutter> list = new ArrayList<>();

        String sql = "SELECT id, number, book FROM list ORDER BY id DESC";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Mutter(
                    rs.getInt("id"),
                    rs.getInt("number"),
                    rs.getString("book")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // ==================================================
    // listテーブル：条件検索（id / number）
    // ==================================================
    public List<Mutter> search(Integer id, Integer number) {
        List<Mutter> list = new ArrayList<>();

        StringBuilder sql = new StringBuilder(
            "SELECT id, number, book FROM list WHERE 1=1"
        );
        if (id != null) {
            sql.append(" AND id = ?");
        }
        if (number != null) {
            sql.append(" AND number = ?");
        }
        sql.append(" ORDER BY id DESC");

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            int idx = 1;
            if (id != null) {
                ps.setInt(idx++, id);
            }
            if (number != null) {
                ps.setInt(idx, number);
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Mutter(
                    rs.getInt("id"),
                    rs.getInt("number"),
                    rs.getString("book")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    // ==================================================
    // listテーブル：INSERT
    // ==================================================
    public boolean create(Mutter mutter) {
        String sql = "INSERT INTO list(number, book) VALUES(?, ?)";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, mutter.getNumber());
            ps.setString(2, mutter.getBook());

            return ps.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // ==================================================
    // userテーブル：ログイン認証
    // ==================================================
    public boolean checkUser(User user) {

        String sql = "SELECT id FROM user WHERE name = ? AND pass = ?";

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getPass());

            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}