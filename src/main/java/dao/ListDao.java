package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Book;

public class ListDao {

    private static final String URL =
        "jdbc:mysql://localhost:3306/library-touroku?useSSL=false&serverTimezone=Asia/Tokyo";
    private static final String USER = "root";
    private static final String PASS = "";

    public List<Book> findAll() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        List<Book> bookList = new ArrayList<>();

        // ★ 追加：detail カラムを取得
        String sql = "SELECT id, book, number, detail FROM list";

        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Book b = new Book();
                b.setId(rs.getInt("id"));
                b.setBook(rs.getString("book"));
                b.setNumber(rs.getInt("number"));

                // ★ 追加：図書詳細情報
                b.setDetail(rs.getString("detail"));

                bookList.add(b);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return bookList;
    }
}