package servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.RentalLendDao;
import model.Lend;
import model.User;

@WebServlet("/MyLibrary_servlet")
public class MyLibrary_servlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // 貸出情報操作用DAO（本Servlet内で共通利用）
    private final RentalLendDao dao = new RentalLendDao();

    /**
     * マイライブラリ画面表示（貸出中一覧）
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ===== セッション取得 =====
        // false指定：セッションが存在しない場合は新規作成しない
        HttpSession session = request.getSession(false);
        if (session == null) {
            // 未ログイン状態とみなしトップへリダイレクト
            response.sendRedirect("index.jsp");
            return;
        }

        // ===== ログインユーザー取得 =====
        Object obj = session.getAttribute("loginUser");

        // セッション改ざん・型不整合対策
        if (!(obj instanceof User)) {
            response.sendRedirect("index.jsp");
            return;
        }

        User loginUser = (User) obj;

        // ===== 貸出中書籍一覧取得 =====
        // ログインユーザーIDを条件にDB検索
        List<Lend> lendList =
            dao.findLendingBooksByUser(loginUser.getId());

        // JSP表示用にリクエストスコープへ設定
        request.setAttribute("lendList", lendList);

        // マイライブラリ画面へフォワード
        request.getRequestDispatcher("/WEB-INF/jsp/Mylibrary.jsp")
               .forward(request, response);
    }

    /**
     * POSTアクセス時も同一表示に統一
     * （URL直打ち・誤送信対策）
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // GET処理へ委譲
        doGet(request, response);
    }
}