package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LendDao {

    private static final String URL =
        "jdbc:mysql://localhost:3306/library-touroku?useSSL=false&serverTimezone=Asia/Tokyo";
    private static final String USER = "root";
    private static final String PASS = "";

    /**
     * すでに借りているか判定
     */
    public boolean isAlreadyLent(String name, String bookname) {
        String sql = "SELECT COUNT(*) FROM lend WHERE name=? AND bookname=?";

        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, bookname);

            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    /**
     * 現在借りている冊数
     */
    public int countLend(String name) {
        String sql = "SELECT COUNT(*) FROM lend WHERE name=?";

        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 貸出処理（insert + 在庫減少 + user.lend 減少）
     */
    public boolean lendBook(String name, String bookname) {

        String insertLend =
            "INSERT INTO lend(name, bookname) VALUES(?, ?)";
        String updateBook =
            "UPDATE list SET number = number - 1 WHERE book=? AND number > 0";
        String updateUser =
            "UPDATE user SET lend = lend - 1 WHERE name=? AND lend > 0";

        try (Connection con = DriverManager.getConnection(URL, USER, PASS)) {

            con.setAutoCommit(false);

            try (PreparedStatement ps1 = con.prepareStatement(insertLend);
                 PreparedStatement ps2 = con.prepareStatement(updateBook);
                 PreparedStatement ps3 = con.prepareStatement(updateUser)) {

                ps1.setString(1, name);
                ps1.setString(2, bookname);

                ps2.setString(1, bookname);
                ps3.setString(1, name);

                ps1.executeUpdate();
                ps2.executeUpdate();
                ps3.executeUpdate();

                con.commit();
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 返却処理（delete + 在庫増加 + user.lend 増加）
     */
    public boolean returnBook(String name, String bookname) {

        String deleteLend =
            "DELETE FROM lend WHERE name=? AND bookname=?";
        String updateBook =
            "UPDATE list SET number = number + 1 WHERE book=?";
        String updateUser =
            "UPDATE user SET lend = lend + 1 WHERE name=?";

        try (Connection con = DriverManager.getConnection(URL, USER, PASS)) {

            con.setAutoCommit(false);

            try (PreparedStatement ps1 = con.prepareStatement(deleteLend);
                 PreparedStatement ps2 = con.prepareStatement(updateBook);
                 PreparedStatement ps3 = con.prepareStatement(updateUser)) {

                ps1.setString(1, name);
                ps1.setString(2, bookname);

                ps2.setString(1, bookname);
                ps3.setString(1, name);

                ps1.executeUpdate();
                ps2.executeUpdate();
                ps3.executeUpdate();

                con.commit();
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}