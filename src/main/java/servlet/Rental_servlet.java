package servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.LendDao;
import dao.RentalDao;
import model.Book;
import model.User;

@WebServlet("/Rental_servlet")
public class Rental_servlet extends HttpServlet {

    // 書籍一覧・検索用DAO
    private RentalDao rentalDao = new RentalDao();
    // 貸出台帳管理用DAO
    private LendDao lendDao = new LendDao();

    /**
     * 一覧表示・検索処理
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 既存セッションを取得（なければnull）
        HttpSession session = request.getSession(false);

        // セッションからログインユーザー取得
        User loginUser = (session != null)
                ? (User) session.getAttribute("loginUser")
                : null;

        // JSPへログイン情報を渡す
        request.setAttribute("loginUser", loginUser);

        // ログイン済みの場合のみ検索処理を実行
        if (loginUser != null) {

            // 検索キーワード取得
            String keyword = request.getParameter("keyword");

            // キーワード有無で一覧 or 検索を切り替え
            List<Book> books = (keyword == null || keyword.isEmpty())
                    ? rentalDao.getAllBooks()
                    : rentalDao.searchBooks(keyword);

            // 書籍一覧をリクエストに格納
            request.setAttribute("books", books);

            // 貸出上限（3冊）から残数を計算
            int remain = 3 - lendDao.countLend(loginUser.getName());
            request.setAttribute("remainLend", remain);
        }

        // レンタル画面へフォワード
        request.getRequestDispatcher("/WEB-INF/jsp/rental.jsp")
               .forward(request, response);
    }

    /**
     * 貸出・返却処理
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // セッション取得
        HttpSession session = request.getSession(false);
        // ログインユーザー取得
        User loginUser = (session != null)
                ? (User) session.getAttribute("loginUser")
                : null;

        // 未ログイン時は処理中断
        if (loginUser == null) {
            request.setAttribute("popupMessage", "ログインしてください");
            doGet(request, response);
            return;
        }

        // 実行アクション取得
        String action = request.getParameter("action");
        // 対象書籍名取得
        String bookname = request.getParameter("bookname");
        // ログインユーザー名取得
        String userName = loginUser.getName();

        // 貸出処理
        if ("rent".equals(action)) {

            // すでに貸出中か判定
            if (lendDao.isAlreadyLent(userName, bookname)) {
                request.setAttribute("popupMessage", "すでに借りています");

            // 貸出上限チェック
            } else if (lendDao.countLend(userName) >= 3) {
                request.setAttribute("popupMessage", "3冊以上は借りられません");

            // 貸出登録
            } else {
                lendDao.lendBook(userName, bookname);
                request.setAttribute("popupMessage", "貸出完了");
            }

        // 返却処理
        } else if ("return".equals(action)) {

            // 未貸出の本か判定
            if (!lendDao.isAlreadyLent(userName, bookname)) {
                request.setAttribute("popupMessage", "この本は借りていません");

            // 返却実行
            } else {
                boolean result = lendDao.returnBook(userName, bookname);
                request.setAttribute("popupMessage",
                        result ? "返却完了" : "返却処理に失敗しました");
            }
        }

        // 処理後は一覧再表示
        doGet(request, response);
    }
}