package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.User;

@WebServlet("/Login_servlet")
public class Login_servlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // ===== DB接続情報 =====
    // MySQL（library-touroku）への接続設定
    private static final String URL =
        "jdbc:mysql://localhost:3306/library-touroku";
    private static final String USER = "root";   // 必要に応じて変更
    private static final String PASS = "";       // 必要に応じて変更

    /**
     * ログイン画面表示（GET）
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ログイン画面へフォワード
        request.getRequestDispatcher("/WEB-INF/jsp/Login.jsp")
               .forward(request, response);
    }

    /**
     * ログイン認証処理（POST）
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // リクエスト文字コード指定
        request.setCharacterEncoding("UTF-8");

        // ===== 入力値取得 =====
        String name = request.getParameter("name");
        String pass = request.getParameter("pass");

        // ===== JDBCドライバの明示的ロード =====
        // ※ 環境によっては不要だが、学習用途として明示
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new ServletException(
                "MySQL JDBCドライバが見つかりません",
                e
            );
        }

        // ===== DB認証処理 =====
        try (
            // DB接続
            Connection con = DriverManager.getConnection(URL, USER, PASS);
            // ユーザー認証用SQL
            PreparedStatement ps = con.prepareStatement(
                "SELECT id, name, pass FROM user WHERE name=? AND pass=?"
            )
        ) {

            // プレースホルダへ値設定
            ps.setString(1, name);
            ps.setString(2, pass);

            // SQL実行
            try (ResultSet rs = ps.executeQuery()) {

                // 認証成功（該当ユーザーあり）
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String dbName = rs.getString("name");
                    String dbPass = rs.getString("pass");

                    // Userモデル生成
                    User loginUser = new User(id, dbName, dbPass);

                    // セッション作成・ログインユーザー保存
                    HttpSession session = request.getSession();
                    session.setAttribute("loginUser", loginUser);

                    // 貸出一覧画面へリダイレクト
                    response.sendRedirect(
                        request.getContextPath() + "/Rental_servlet"
                    );

                } else {
                    // 認証失敗（ユーザー不一致）
                    request.setAttribute(
                        "error",
                        "ユーザー名またはパスワードが違います"
                    );
                    request.getRequestDispatcher("/WEB-INF/jsp/Login.jsp")
                           .forward(request, response);
                }
            }

        } catch (SQLException e) {
            // DB接続・SQL実行エラー
            e.printStackTrace();
            throw new ServletException("DB接続エラー", e);
        }
    }
}