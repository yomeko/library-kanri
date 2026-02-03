package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jakarta.servlet.RequestDispatcher;
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
    private static final String URL = "jdbc:mysql://localhost:3306/library-touroku?useSSL=false&serverTimezone=Asia/Tokyo";
    private static final String USER = "root";
    private static final String PASS = "";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/newAcount.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String name = request.getParameter("name");
        String pass = request.getParameter("pass");

        if (isBlank(name) || isBlank(pass)) {
            request.setAttribute("errorMsg", "名前とパスワードは必須です");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/newAcount.jsp");
            dispatcher.forward(request, response);
            return;
        }

        // ===== DB に登録 =====
        User user = null;
        boolean isRegistered = false;

        String sql = "INSERT INTO user(name, pass) VALUES(?, ?)";

        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, name.trim());
            ps.setString(2, pass.trim());

            int count = ps.executeUpdate();
            if (count > 0) {
                // 自動生成された id を取得
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    user = new User(id, name.trim(), pass.trim());
                    isRegistered = true;

                    // ログイン状態としてセッションにセット
                    HttpSession session = request.getSession();
                    session.setAttribute("loginUser", user);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMsg", "登録に失敗しました（ユーザー名が重複している可能性があります）");
        }

        request.setAttribute("isRegistered", isRegistered);
        request.setAttribute("user", user);

        RequestDispatcher dispatcher =
                request.getRequestDispatcher("/WEB-INF/jsp_Result/newAcount_Result.jsp");
        dispatcher.forward(request, response);
    }

    // null / 空文字 / 半角スペース / 全角スペースのみ true
    private boolean isBlank(String str) {
        if (str == null) return true;
        String replaced = str.replace("　", " ").trim();
        return replaced.isEmpty();
    }
}