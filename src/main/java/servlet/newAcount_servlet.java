package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.User;

@WebServlet("/newAcount_servlet")
public class newAcount_servlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // ===== DB接続情報 =====
    // MySQL（phpMyAdmin管理）への接続設定
    private static final String URL =
        "jdbc:mysql://localhost:3306/library-touroku?useSSL=false&serverTimezone=Asia/Tokyo";
    private static final String USER = "root";
    private static final String PASS = "";

    /**
     * 新規アカウント登録画面表示（GET）
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 登録画面へフォワード
        request.getRequestDispatcher("/WEB-INF/jsp/newAcount.jsp")
               .forward(request, response);
    }

    /**
     * 新規アカウント登録処理（POST）
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // リクエスト文字コード指定
        request.setCharacterEncoding("UTF-8");

        // ===== 入力パラメータ取得 =====
        String name = request.getParameter("name");
        String pass = request.getParameter("pass");

        // ===== null / 空文字チェック（最優先）=====
        // JSPからの未入力・不正送信対策
        if (name == null || pass == null ||
            name.isBlank() || pass.isBlank()) {

            request.setAttribute("errorMsg", "ユーザー名とパスワードは必須です");
            request.getRequestDispatcher("/WEB-INF/jsp/newAcount.jsp")
                   .forward(request, response);
            return;
        }

        // ===== 入力値の正規化 =====
        // 前後の全角・半角空白を除去
        name = normalize(name);
        pass = normalize(pass);

        // ===== 業務ルール：禁止語チェック =====
        // システム上問題のある文字列を排除
        if (isForbiddenWord(name)) {
            request.setAttribute("errorMsg", "使用できないユーザー名です");
            request.getRequestDispatcher("/WEB-INF/jsp/newAcount.jsp")
                   .forward(request, response);
            return;
        }

        // ===== DB登録処理 =====
        String sql = "INSERT INTO user(name, pass) VALUES(?, ?)";
        User user = null;

        try (
            // DB接続
            Connection con = DriverManager.getConnection(URL, USER, PASS);
            // 自動採番ID取得用 PreparedStatement
            PreparedStatement ps =
                con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {

            // SQLパラメータ設定
            ps.setString(1, name);
            ps.setString(2, pass);

            // INSERT実行
            int count = ps.executeUpdate();

            // 1件登録成功時のみID取得
            if (count == 1) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        int id = rs.getInt(1);

                        // Userモデル生成
                        user = new User(id, name, pass);

                        // ログイン状態としてセッションに保存
                        HttpSession session = request.getSession();
                        session.setAttribute("loginUser", user);
                    }
                }
            }

        } catch (SQLException e) {
            // UNIQUE制約違反（ユーザー名重複）など
            request.setAttribute(
                "errorMsg",
                "登録に失敗しました（ユーザー名が既に存在する可能性があります）"
            );
            request.getRequestDispatcher("/WEB-INF/jsp/newAcount.jsp")
                   .forward(request, response);
            return;
        }

        // ===== 正常終了時 =====
        request.setAttribute("user", user);
        request.setAttribute("isRegistered", true);

        // 登録完了画面へ遷移
        request.getRequestDispatcher("/WEB-INF/jsp_Result/newAcount_Result.jsp")
               .forward(request, response);
    }

    // ===== 入力値正規化 =====
    // 前後の半角・全角空白を除去
    private String normalize(String str) {
        if (str == null) return null;
        return str.replaceAll("^[\\s　]+|[\\s　]+$", "");
    }

    // ===== 禁止語判定 =====
    // システム的に問題となる文字列を排除
    private boolean isForbiddenWord(String str) {
        if (str == null) return true;

        switch (str.toLowerCase()) {
            case "null":
            case "true":
            case "false":
                return true;
            default:
                return false;
        }
    }
}