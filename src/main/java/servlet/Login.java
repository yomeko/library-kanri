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

    /**
     * index.jsp から「ログイン」ボタンが押されたとき
     * 最初に呼ばれるのは doGet()
     * → ログイン画面（Login.jsp）を表示する役割
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Login.jsp（ログイン入力画面）にフォワード
        RequestDispatcher dispatcher = 
                request.getRequestDispatcher("WEB-INF/jsp/Login.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * Login.jsp のフォームから送信されたとき呼ばれる
     * → ユーザー名・パスワードを受け取り、認証処理を実行
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // フォームデータを取得
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // 結果をリクエストスコープに保存
        request.setAttribute("username", username);

        // ログイン結果画面にフォワード
        RequestDispatcher dispatcher =
                request.getRequestDispatcher("WEB-INF/jsp_Result/loginResult.jsp");
        dispatcher.forward(request, response);
    }
}