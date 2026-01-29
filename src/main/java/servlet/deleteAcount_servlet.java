package servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.LendDao;
import model_Logic.deleteAcount_Logic;

@WebServlet("/deleteAcount_servlet")
public class deleteAcount_servlet extends HttpServlet {

    // 削除画面表示
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // セッション取得
        HttpSession session = request.getSession(false);
        // セッションが存在すれば破棄
        if (session != null) {
            session.invalidate();
        }

        // アカウント削除画面へ遷移
        request.getRequestDispatcher("/WEB-INF/jsp/deleteAcount.jsp")
               .forward(request, response);
    }

    // アカウント削除処理
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 文字化け対策
        request.setCharacterEncoding("UTF-8");

        // 入力値取得
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // 未入力チェック
        if (username == null || password == null ||
            username.isEmpty() || password.isEmpty()) {

            request.setAttribute("error", "未入力の項目があります");
            request.getRequestDispatcher("/WEB-INF/jsp/deleteAcount.jsp")
                   .forward(request, response);
            return;
        }

        // 未返却書籍の有無を確認
        LendDao lendDao = new LendDao();
        if (lendDao.hasLendingBooks(username)) {
            request.setAttribute("error", "本を返却してからアカウント削除してください");
            request.getRequestDispatcher("/WEB-INF/jsp/deleteAcount.jsp")
                   .forward(request, response);
            return;
        }

        // アカウント削除ロジック実行
        deleteAcount_Logic logic = new deleteAcount_Logic();
        boolean result = logic.execute(username, password);

        // 結果に応じて画面分岐
        if (result) {
            request.getRequestDispatcher("/WEB-INF/jsp_Result/deleteAcount_Result.jsp")
                   .forward(request, response);
        } else {
            request.setAttribute("error", "ユーザー名またはパスワードが正しくありません");
            request.getRequestDispatcher("/WEB-INF/jsp/deleteAcount.jsp")
                   .forward(request, response);
        }
    }
}