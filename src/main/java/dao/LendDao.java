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
     * すでにその本を借りているか判定
     * ※ 返却期限（7日）を過ぎているものは「借りていない扱い」
     */
    public boolean isAlreadyLent(String name, String bookname) {

        String sql =
            "SELECT COUNT(*) FROM lend " +
            "WHERE name=? AND bookname=? " +
            "AND CURDATE() <= DATE_ADD(lend_date, INTERVAL 7 DAY)";

        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, bookname);

            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return true; // 安全側
        }
    }

    /**
     * 現在借りている冊数（期限内のみカウント）
     */
    public int countLend(String name) {

        String sql =
            "SELECT COUNT(*) FROM lend " +
            "WHERE name=? " +
            "AND CURDATE() <= DATE_ADD(lend_date, INTERVAL 7 DAY)";

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
     * 未返却の本を借りているか判定（アカウント削除用）
     */
    public boolean hasLendingBooks(String name) {
        return countLend(name) > 0;
    }

    /**
     * 貸出処理
     * ・lend にレコード追加
     * ・lend_date は CURRENT_DATE
     * ・在庫減少
     * ・user.lend 減少
     */
    public boolean lendBook(String name, String bookname) {

        String insertLend =
            "INSERT INTO lend(name, bookname, lend_date) " +
            "VALUES(?, ?, CURRENT_DATE)";
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
     * 返却処理
     * ・借りた日から7日以内のデータのみ削除
     * ・期限切れ or 未貸出の場合は false
     */
    public boolean returnBook(String name, String bookname) {

        String deleteLend =
            "DELETE FROM lend " +
            "WHERE name=? AND bookname=? " +
            "AND CURDATE() <= DATE_ADD(lend_date, INTERVAL 7 DAY)";
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

                // 実際に削除されたか（＝期限内に借りていたか）
                int deleted = ps1.executeUpdate();
                if (deleted == 0) {
                    con.rollback();
                    return false;
                }

                ps2.setString(1, bookname);
                ps3.setString(1, name);

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