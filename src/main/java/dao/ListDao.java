package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Book;

/**
 * 書籍一覧（listテーブル）に対するDAOクラス
 * ・全件取得
 * ・キーワード検索
 */
public class ListDao {

    // データベース接続URL
    // useSSL=false : SSL未使用
    // serverTimezone=Asia/Tokyo : タイムゾーン指定（MySQLの警告対策）
    private static final String URL =
        "jdbc:mysql://localhost:3306/library-touroku?useSSL=false&serverTimezone=Asia/Tokyo";

    // DB接続ユーザー
    private static final String USER = "root";

    // DB接続パスワード（未設定）
    private static final String PASS = "";

    /**
     * 書籍を全件取得する
     * @return 書籍一覧（BookのList）
     */
    public List<Book> findAll() {

        // 取得結果を格納するリスト
        List<Book> list = new ArrayList<>();

        // 書籍名順で全件取得するSQL
        String sql = "SELECT book, number, detail FROM list ORDER BY book";

        // try-with-resources により Connection / PreparedStatement / ResultSet を自動クローズ
        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            // 検索結果を1件ずつBookオブジェクトに詰める
            while (rs.next()) {
                Book b = new Book();
                b.setBook(rs.getString("book"));   // 書籍名
                b.setNumber(rs.getInt("number"));  // 在庫数
                b.setDetail(rs.getString("detail"));//詳細情報
                list.add(b);
            }
        } catch (Exception e) {
            // SQL例外・接続例外をまとめて捕捉
            e.printStackTrace();
        }

        // 取得結果を返却
        return list;
    }

    /**
     * 書籍名をキーワード検索する
     * @param keyword 検索文字列
     * @return 検索結果の書籍一覧
     */
    public List<Book> findByKeyword(String keyword) {

        List<Book> list = new ArrayList<>();

        String sql = """
            SELECT book, number, detail
            FROM list
            WHERE book LIKE ?
            ORDER BY book
        """;

        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + keyword + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Book b = new Book();
                b.setBook(rs.getString("book"));
                b.setNumber(rs.getInt("number"));
                b.setDetail(rs.getString("detail")); // ★ 追加
                list.add(b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}