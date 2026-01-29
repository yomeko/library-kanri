package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/Logout_servlet")
public class Logout_servlet extends HttpServlet {

    // ログアウト処理
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // セッション取得
        HttpSession session = request.getSession(false);

        // 未ログイン時の判定
        if (session == null || session.getAttribute("loginUser") == null) {
            request.setAttribute("message", "ログアウトできる状況ではありません");
        } else {
            // セッション破棄によるログアウト
            session.invalidate();
            request.setAttribute("message", "ログアウトしました");
        }

        // ログアウト結果画面へ遷移
        request.getRequestDispatcher("/WEB-INF/jsp/Logout.jsp")
               .forward(request, response);
    }
}