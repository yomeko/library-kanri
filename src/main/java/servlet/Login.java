package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/Login")
public class Login extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public Login() {
        super();
    }

    /**
     * index.jsp のログインボタン → GET リクエスト
     * ログイン画面（Login.jsp）を表示
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher dispatcher =
                request.getRequestDispatcher("WEB-INF/jsp/Login.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Login.jsp からの POST
     * → ユーザー名とパスワードの受け取り
     * →（本来ならDB認証）
     * → セッションにログイン情報を保存
     * → ログイン結果へ
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // ★ログイン成功したことにする（本来はDBで認証）
        request.getSession().setAttribute("loginUser", username);

        // ログイン結果画面の表示用
        request.setAttribute("username", username);

        RequestDispatcher dispatcher =
                request.getRequestDispatcher("WEB-INF/jsp_Result/loginResult.jsp");
        dispatcher.forward(request, response);
    }
}