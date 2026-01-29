package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/Login_servlet")
public class Login_servlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // ログイン画面表示
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ログイン画面へフォワード
        request.getRequestDispatcher("/WEB-INF/jsp/Login.jsp")
               .forward(request, response);
    }

    // ログイン処理
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 文字化け対策
        request.setCharacterEncoding("UTF-8");

        // 入力値取得
        String name = request.getParameter("name");
        String pass = request.getParameter("pass");

        // Userオブジェクト生成
        model.User user = new model.User(name, pass);
        // DAO生成
        dao.UserDao dao = new dao.UserDao();

        // ログイン判定
        boolean isLogin = dao.login(user);

        if (isLogin) {
            // セッション作成
            HttpSession session = request.getSession();
            // ログインユーザーをセッションに保存
            session.setAttribute("loginUser", user);

            // レンタル画面へリダイレクト
            response.sendRedirect(request.getContextPath() + "/Rental_servlet");

        } else {
            // ログイン失敗メッセージ設定
            request.setAttribute("error", "ユーザー名またはパスワードが違います");
            request.getRequestDispatcher("/WEB-INF/jsp/Login.jsp")
                   .forward(request, response);
        }
    }
}