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

    // DB 接続情報
    private static final String URL = "jdbc:mysql://localhost:3306/library-touroku?useSSL=false&serverTimezone=Asia/Tokyo";
    private static final String USER = "root";   // 必要に応じて変更
    private static final String PASS = "";       // 必要に応じて変更

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/Login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String name = request.getParameter("name");
        String pass = request.getParameter("pass");

        // JDBCドライバを明示的にロード
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new ServletException("MySQL JDBCドライバが見つかりません", e);
        }

        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = con.prepareStatement(
                 "SELECT id, name, pass FROM user WHERE name=? AND pass=?")) {

            ps.setString(1, name);
            ps.setString(2, pass);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String dbName = rs.getString("name");
                    String dbPass = rs.getString("pass");

                    User loginUser = new User(id, dbName, dbPass);

                    HttpSession session = request.getSession();
                    session.setAttribute("loginUser", loginUser);

                    response.sendRedirect(request.getContextPath() + "/Rental_servlet");

                } else {
                    request.setAttribute("error", "ユーザー名またはパスワードが違います");
                    request.getRequestDispatcher("/WEB-INF/jsp/Login.jsp").forward(request, response);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("DB接続エラー", e);
        }
    }
}