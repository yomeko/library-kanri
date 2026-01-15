package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Book;

public class RentalDao {
    private final String URL = "jdbc:mysql://localhost:3306/library-touroku";
    private final String USER = "root";
    private final String PASS = ""; // 適宜変更

    // 貸出処理
    public boolean rentBook(int bookId) {
        String sql = "UPDATE list SET number = number - 1 WHERE id = ? AND number > 0";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, bookId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 一覧取得
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM list";
        //		System.out.println("RentalDao");
        
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
        	
            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setBook(rs.getString("book"));
                book.setNumber(rs.getInt("number"));
                books.add(book);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            
            System.out.println("RentalDao");
            
        }
        return books;
    }
    
 // 書籍名で部分一致検索
    public List<Book> searchBooks(String keyword) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM list WHERE book LIKE ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + keyword + "%"); // ← 部分一致の肝
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setBook(rs.getString("book"));
                book.setNumber(rs.getInt("number"));
                books.add(book);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }
    
 // 返却処理
    public boolean returnBook(int bookId) {
        String sql = "UPDATE list SET number = number + 1 WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, bookId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}