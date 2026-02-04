package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Book;
import model.Lend;

public class RentalLendDao {

    private static final String URL =
        "jdbc:mysql://localhost:3306/library-touroku?useSSL=false&serverTimezone=Asia/Tokyo";
    private static final String USER = "root";
    private static final String PASS = "";

    private static final int MAX_LEND = 3;

    /* ======================
       蔵書一覧
       ====================== */

    public List<Book> getAllBooks() {
        List<Book> list = new ArrayList<>();

        // ★ 追加：detail を含める
        String sql = "SELECT book, number, detail FROM list";

        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Book b = new Book();
                b.setBook(rs.getString("book"));
                b.setNumber(rs.getInt("number"));

                // ★ 追加
                b.setDetail(rs.getString("detail"));

                list.add(b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /* ======================
       検索
       ====================== */

    public List<Book> searchBooks(String keyword) {
        List<Book> list = new ArrayList<>();

        // ★ 追加：detail を含める
        String sql = "SELECT book, number, detail FROM list WHERE book LIKE ?";

        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Book b = new Book();
                b.setBook(rs.getString("book"));
                b.setNumber(rs.getInt("number"));

                // ★ 追加
                b.setDetail(rs.getString("detail"));

                list.add(b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /* ======================
       貸出判定系
       ====================== */

    public int countLend(int userId) {
        String sql = """
            SELECT COUNT(*) FROM lend
            WHERE user_id=? AND CURDATE() <= DATE_ADD(lend_date, INTERVAL 7 DAY)
        """;

        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public boolean isAlreadyLent(int userId, String bookName) {
        String sql = """
            SELECT COUNT(*) FROM lend
            WHERE user_id=? AND bookname=?
              AND CURDATE() <= DATE_ADD(lend_date, INTERVAL 7 DAY)
        """;

        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setString(2, bookName);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }

    /* ======================
       貸出処理
       ====================== */

    public boolean lendBook(int userId, String userName, String bookName) {

        if (countLend(userId) >= MAX_LEND) return false;

        String updateBook =
            "UPDATE list SET number = number - 1 WHERE book=? AND number > 0";
        String insertLend =
            "INSERT INTO lend(user_id, name, bookname, lend_date) VALUES(?,?,?,CURRENT_DATE)";

        try (Connection con = DriverManager.getConnection(URL, USER, PASS)) {
            con.setAutoCommit(false);

            try (PreparedStatement psBook = con.prepareStatement(updateBook);
                 PreparedStatement psLend = con.prepareStatement(insertLend)) {

                psBook.setString(1, bookName);
                if (psBook.executeUpdate() == 0) {
                    con.rollback();
                    return false;
                }

                psLend.setInt(1, userId);
                psLend.setString(2, userName);
                psLend.setString(3, bookName);
                psLend.executeUpdate();

                con.commit();
                return true;

            } catch (Exception e) {
                con.rollback();
                e.printStackTrace();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /* ======================
       返却処理
       ====================== */

    public boolean returnBook(int userId, String bookName) {

        String deleteLend =
            "DELETE FROM lend WHERE user_id=? AND bookname=? " +
            "AND CURDATE() <= DATE_ADD(lend_date, INTERVAL 7 DAY)";
        String updateBook =
            "UPDATE list SET number = number + 1 WHERE book=?";

        try (Connection con = DriverManager.getConnection(URL, USER, PASS)) {
            con.setAutoCommit(false);

            try (PreparedStatement psDelete = con.prepareStatement(deleteLend);
                 PreparedStatement psBook = con.prepareStatement(updateBook)) {

                psDelete.setInt(1, userId);
                psDelete.setString(2, bookName);

                if (psDelete.executeUpdate() == 0) {
                    con.rollback();
                    return false;
                }

                psBook.setString(1, bookName);
                psBook.executeUpdate();

                con.commit();
                return true;

            } catch (Exception e) {
                con.rollback();
                e.printStackTrace();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /* ======================
       貸出中一覧
       ====================== */

    public List<Lend> findLendingBooksByUser(int userId) {

        List<Lend> list = new ArrayList<>();
        String sql = """
            SELECT name, bookname, lend_date
            FROM lend
            WHERE user_id=?
              AND CURDATE() <= DATE_ADD(lend_date, INTERVAL 7 DAY)
            ORDER BY lend_date
        """;

        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Lend l = new Lend();
                l.setName(rs.getString("name"));
                l.setBookname(rs.getString("bookname"));
                l.setLendDate(rs.getDate("lend_date"));
                list.add(l);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}