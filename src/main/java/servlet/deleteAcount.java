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
import model_Logic.deleteAcountLogic;

@WebServlet("/deleteAcount")
public class deleteAcount extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public deleteAcount() {
        super();
    }

    // 削除確認画面を表示
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher dispatcher =
                request.getRequestDispatcher("/WEB-INF/jsp/deleteAcount.jsp");
        dispatcher.forward(request, response);
    }

    // 削除フォームを受け取って結果画面へ
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // フォームからパラメータを取得
        String name = request.getParameter("name");
        String pass = request.getParameter("pass");

        // 未入力チェック
        if (name == null || name.isEmpty() || pass == null || pass.isEmpty()) {
            request.setAttribute("error", "ユーザー名とパスワードを入力してください。");
            RequestDispatcher dispatcher =
                    request.getRequestDispatcher("/WEB-INF/jsp/deleteAcount.jsp");
            dispatcher.forward(request, response);
            return;
        }

        // Userオブジェクトを作成
        User user = new User(name, pass);

        // 削除処理を実行
        deleteAcountLogic deleteLogic = new deleteAcountLogic();
        boolean isDeleted = deleteLogic.deletem(user);

        if (isDeleted) {
            // 削除成功：セッションを無効化
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            request.setAttribute("isDeleted", true);
            request.setAttribute("name", name);
        } else {
            // 削除失敗：エラーメッセージを設定
            String errorMessage = deleteLogic.getErrorMessage();
            if (errorMessage != null) {
                request.setAttribute("error", errorMessage);
            } else {
                request.setAttribute("error", "アカウント削除に失敗しました。ユーザー名とパスワードを確認してください。");
            }
            request.setAttribute("isDeleted", false);
        }

        RequestDispatcher dispatcher =
                request.getRequestDispatcher("/WEB-INF/jsp_Result/deleteAcountResult.jsp");
        dispatcher.forward(request, response);
    }
}