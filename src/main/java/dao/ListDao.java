package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Book;

/**
 * 書籍マスタ（listテーブル）参照用DAO
 *
 * 【責務】
 * ・書籍情報の取得のみを担当する
 * ・登録／更新／削除は行わない（参照専用DAO）
 *
 * 【前提】
 * ・list テーブルには以下のカラムが存在する
 *   - book   : 書籍名（主キー or 一意制約想定）
 *   - number : 在庫数
 *   - detail : 書籍詳細
 */
public class ListDao {

    // ===== DB接続情報 =====

    /**
     * JDBC接続URL
     * useSSL=false            : ローカル環境のSSL警告抑止
     * serverTimezone=Asia/Tokyo : MySQLタイムゾーン警告対策
     */
    private static final String URL =
        "jdbc:mysql://localhost:3306/library-touroku?useSSL=false&serverTimezone=Asia/Tokyo";

    // DB接続ユーザー
    private static final String USER = "root";

    // DB接続パスワード（未設定前提）
    private static final String PASS = "";

    /* =================================================
       書籍全件取得
       ================================================= */

    /**
     * 書籍マスタを全件取得する
     *
     * 【仕様】
     * ・在庫数が0の書籍も含めて取得する
     * ・書籍名（book）昇順で並び替える
     *
     * @return 書籍一覧（0件の場合は空のList）
     */
    public List<Book> findAll() {

        // 取得結果格納用リスト（nullは返さない）
        List<Book> list = new ArrayList<>();

        // 書籍名順で全件取得
        String sql = "SELECT book, number, detail FROM list ORDER BY book";

        // try-with-resources によりリソースは自動クローズされる
        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            // ResultSet → Book への詰め替え
            while (rs.next()) {
                Book b = new Book();
                b.setBook(rs.getString("book"));     // 書籍名
                b.setNumber(rs.getInt("number"));    // 在庫数
                b.setDetail(rs.getString("detail")); // 詳細情報
                list.add(b);
            }

        } catch (Exception e) {
            // 接続例外・SQL例外をまとめて捕捉
            // 本メソッドでは呼び出し元に例外を投げず、空リストを返却する設計
            e.printStackTrace();
        }

        return list;
    }

    /* =================================================
       書籍名キーワード検索
       ================================================= */

    /**
     * 書籍名による部分一致検索
     *
     * 【仕様】
     * ・book カラムに対して LIKE '%keyword%' 検索
     * ・大文字／小文字の区別はDBの照合順序に依存
     * ・在庫数0の書籍も検索対象に含む
     *
     * @param keyword 検索キーワード（nullは想定外）
     * @return 検索結果の書籍一覧（0件の場合は空のList）
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

            // 部分一致検索用ワイルドカード付与
            ps.setString(1, "%" + keyword + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Book b = new Book();
                b.setBook(rs.getString("book"));
                b.setNumber(rs.getInt("number"));
                b.setDetail(rs.getString("detail"));
                list.add(b);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}