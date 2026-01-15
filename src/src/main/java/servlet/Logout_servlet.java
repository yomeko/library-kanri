package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/Logout_servlet")
public class Logout_servlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("loginUser") == null) {
            // 未ログイン状態
            request.setAttribute("message", "ログアウトできる状況ではありません");
        } else {
            // ログイン中 → ログアウト
            session.invalidate();
            request.setAttribute("message", "ログアウトしました");
        }

        RequestDispatcher dispatcher =
                request.getRequestDispatcher("/WEB-INF/jsp/Logout.jsp");
        dispatcher.forward(request, response);
    }
}