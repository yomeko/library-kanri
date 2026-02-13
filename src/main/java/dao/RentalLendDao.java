package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import model.Lend;

public class RentalLendDao {

    // ===== DB接続情報 =====
    private static final String URL =
        "jdbc:mysql://localhost:3306/library-touroku?useSSL=false&serverTimezone=Asia/Tokyo";
    private static final String USER = "root";
    private static final String PASS = "";

    // ===== 業務ルール定数 =====

    // ユーザーあたりの最大貸出冊数
    public static final int MAX_LEND = 3;

    // 貸出有効期間（日）
    private static final int LEND_DAYS = 14;

    /* =================================================
       全貸出数取得（制御用・期限無視）
       ================================================= */
    public int countAllLend(int userId) {

        // 期限切れを含めた全貸出レコード数
        String sql = "SELECT COUNT(*) FROM lend WHERE user_id=?";

        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            rs.next();

            return rs.getInt(1);

        } catch (Exception e) {
            e.printStackTrace();

            // 異常時は安全側（上限到達扱い）で返却
            return MAX_LEND;
        }
    }

    /* =================================================
       有効貸出数取得（14日以内・表示/制御用）
       ================================================= */
    public int countValidLend(int userId) {

        // 貸出日＋14日以内のみを有効とみなす
        String sql = """
            SELECT COUNT(*)
            FROM lend
            WHERE user_id=?
              AND CURDATE() <= DATE_ADD(lend_date, INTERVAL ? DAY)
        """;

        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, LEND_DAYS);
            ResultSet rs = ps.executeQuery();
            rs.next();

            return rs.getInt(1);

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /* =================================================
       指定書籍を既に借りているか判定（14日以内）
       ================================================= */
    public boolean isAlreadyLent(int userId, String bookName) {

        // 同一ユーザー・同一書籍・有効期間内の存在チェック
        String sql = """
            SELECT COUNT(*)
            FROM lend
            WHERE user_id=?
              AND bookname=?
              AND CURDATE() <= DATE_ADD(lend_date, INTERVAL ? DAY)
        """;

        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setString(2, bookName);
            ps.setInt(3, LEND_DAYS);
            ResultSet rs = ps.executeQuery();
            rs.next();

            // 1件以上あれば既に借りている
            return rs.getInt(1) > 0;

        } catch (Exception e) {
            e.printStackTrace();

            // 異常時は二重貸出防止のため true を返す
            return true;
        }
    }

    /* =================================================
       貸出処理（在庫・上限・完全防御）
       ================================================= */
    public boolean lendBook(int userId, String userName, String bookName) {

        // ===== 貸出上限チェック（DB基準）=====
        if (countAllLend(userId) >= MAX_LEND) return false;

        // 在庫数を1減らす（0以下は不可）
        String updateBook =
            "UPDATE list SET number = number - 1 WHERE book=? AND number > 0";

        // 貸出情報登録
        String insertLend =
            "INSERT INTO lend(user_id, name, bookname, lend_date) " +
            "VALUES(?,?,?,CURRENT_DATE)";

        try (Connection con = DriverManager.getConnection(URL, USER, PASS)) {

            // トランザクション開始
            con.setAutoCommit(false);

            try (PreparedStatement psBook = con.prepareStatement(updateBook);
                 PreparedStatement psLend = con.prepareStatement(insertLend)) {

                // ===== 在庫更新 =====
                psBook.setString(1, bookName);

                // 更新件数0件＝在庫なし
                if (psBook.executeUpdate() == 0) {
                    con.rollback();
                    return false;
                }

                // ===== 貸出登録 =====
                psLend.setInt(1, userId);
                psLend.setString(2, userName);
                psLend.setString(3, bookName);
                psLend.executeUpdate();

                // 正常終了 → コミット
                con.commit();
                return true;

            } catch (Exception e) {
                // 処理途中エラー → ロールバック
                con.rollback();
                e.printStackTrace();
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /* =================================================
       貸出中書籍一覧取得（14日以内）
       ================================================= */
    public List<Lend> findLendingBooksByUser(int userId) {

        List<Lend> list = new ArrayList<>();

        // 有効期間内の貸出のみ取得
        String sql = """
            SELECT name, bookname, lend_date
            FROM lend
            WHERE user_id=?
              AND CURDATE() <= DATE_ADD(lend_date, INTERVAL ? DAY)
            ORDER BY lend_date
        """;

        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setInt(2, LEND_DAYS);
            ResultSet rs = ps.executeQuery();

            // 1件ずつ Lend モデルへ変換
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