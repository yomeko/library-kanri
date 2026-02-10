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

    // DB 接続情報
    private static final String URL =
        "jdbc:mysql://localhost:3306/library-touroku?useSSL=false&serverTimezone=Asia/Tokyo";
    private static final String USER = "root";
    private static final String PASS = "";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/WEB-INF/jsp/newAcount.jsp")
               .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // ===== パラメータ取得 =====
        String name = request.getParameter("name");
        String pass = request.getParameter("pass");

        // ===== null / 空白チェック（最優先）=====
        if (name == null || pass == null ||
            name.isBlank() || pass.isBlank()) {

            request.setAttribute("errorMsg", "ユーザー名とパスワードは必須です");
            request.getRequestDispatcher("/WEB-INF/jsp/newAcount.jsp")
                   .forward(request, response);
            return;
        }

        // ===== 正規化 =====
        name = normalize(name);
        pass = normalize(pass);

        // ===== 禁止文字列チェック（仕様）=====
        if (isForbiddenWord(name)) {
            request.setAttribute("errorMsg", "使用できないユーザー名です");
            request.getRequestDispatcher("/WEB-INF/jsp/newAcount.jsp")
                   .forward(request, response);
            return;
        }

        // ===== DB登録 =====
        String sql = "INSERT INTO user(name, pass) VALUES(?, ?)";
        User user = null;

        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps =
                 con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, name);
            ps.setString(2, pass);

            int count = ps.executeUpdate();
            if (count == 1) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        int id = rs.getInt(1);
                        user = new User(id, name, pass);

                        HttpSession session = request.getSession();
                        session.setAttribute("loginUser", user);
                    }
                }
            }

        } catch (SQLException e) {
            // UNIQUE制約違反など
            request.setAttribute(
                "errorMsg",
                "登録に失敗しました（ユーザー名が既に存在する可能性があります）"
            );
            request.getRequestDispatcher("/WEB-INF/jsp/newAcount.jsp")
                   .forward(request, response);
            return;
        }

        // ===== 正常終了 =====
        request.setAttribute("user", user);
        request.setAttribute("isRegistered", true);

        request.getRequestDispatcher("/WEB-INF/jsp_Result/newAcount_Result.jsp")
               .forward(request, response);
    }

    // ===== 正規化 =====
    // 前後の半角・全角空白を除去
    private String normalize(String str) {
        if (str == null) return null;
        return str.replaceAll("^[\\s　]+|[\\s　]+$", "");
    }

    // ===== 禁止語判定 =====
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