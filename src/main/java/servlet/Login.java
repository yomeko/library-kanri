package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;
import model_Logic.LoginLogic;

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
     * → DB認証
     * → セッションにログイン情報を保存
     * → ログイン結果へ
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // フォームからパラメータを取得（Login.jspのname属性に合わせる）
        String name = request.getParameter("name");
        String pass = request.getParameter("pass");

        // 未入力チェック
        if (name == null || name.isEmpty() || pass == null || pass.isEmpty()) {
            request.setAttribute("error", "ユーザー名とパスワードを入力してください。");
            RequestDispatcher dispatcher =
                    request.getRequestDispatcher("WEB-INF/jsp/Login.jsp");
            dispatcher.forward(request, response);
            return;
        }

        // Userオブジェクトを作成
        User user = new User(name, pass);

        // DB認証を実行
        LoginLogic loginLogic = new LoginLogic();
        boolean isLoginSuccess = loginLogic.execute(user);

        if (isLoginSuccess) {
            // ログイン成功：セッションにユーザー名を保存
            HttpSession session = request.getSession();
            session.setAttribute("loginUser", name);

            // ログイン結果画面の表示用
            request.setAttribute("username", name);

            RequestDispatcher dispatcher =
                    request.getRequestDispatcher("WEB-INF/jsp_Result/loginResult.jsp");
            dispatcher.forward(request, response);
        } else {
            // ログイン失敗：エラーメッセージを表示
            request.setAttribute("error", "ユーザー名またはパスワードが正しくありません。");
            RequestDispatcher dispatcher =
                    request.getRequestDispatcher("WEB-INF/jsp/Login.jsp");
            dispatcher.forward(request, response);
        }
    }
}