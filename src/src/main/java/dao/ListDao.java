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
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
    	
        List<Book> bookList = new ArrayList<>();
        String sql = "SELECT id, book, number FROM list";

        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Book b = new Book();
                b.setId(rs.getInt("id"));          // 画面には出さない
                b.setBook(rs.getString("book"));  // 本の名前
                b.setNumber(rs.getInt("number")); // 数量
                bookList.add(b);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return bookList;
    }
}