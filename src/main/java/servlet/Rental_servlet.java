package servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.ListDao;
import dao.RentalLendDao;
import model.Book;
import model.User;

@WebServlet("/Rental_servlet")
public class Rental_servlet extends HttpServlet {

    /**
     * 書籍一覧表示・検索・貸出状況表示用（GET）
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // セッション取得
        HttpSession session = request.getSession();

        // ログイン中ユーザー取得（未ログインの場合は null）
        User loginUser = (User) session.getAttribute("loginUser");

        // DAO生成
        ListDao listDao = new ListDao();
        RentalLendDao rentalDao = new RentalLendDao();

        // 検索キーワード取得
        String keyword = request.getParameter("keyword");

        // 書籍一覧取得（検索あり／なしで分岐）
        List<Book> books;
        if (keyword != null && !keyword.isBlank()) {
            // キーワード検索
            books = listDao.findByKeyword(keyword);
        } else {
            // 全件取得
            books = listDao.findAll();
        }

        // ログイン済みの場合のみ貸出関連情報を設定
        if (loginUser != null) {

            // 各書籍について「既に借りているか」を判定
            for (Book b : books) {
                b.setAlreadyLent(
                    rentalDao.isAlreadyLent(
                        loginUser.getId(),
                        b.getBook()
                    )
                );
            }

            // 残り貸出可能数を計算
            int remainLend =
                RentalLendDao.MAX_LEND
                - rentalDao.countValidLend(loginUser.getId());

            // 残り貸出可能数をリクエストスコープへ
            request.setAttribute("remainLend", remainLend);

            // 現在貸出中の書籍一覧を設定
            request.setAttribute(
                "lendList",
                rentalDao.findLendingBooksByUser(loginUser.getId())
            );
        }

        // PRGパターン用：ポップアップメッセージ取得
        String msg = (String) session.getAttribute("popupMessage");
        if (msg != null) {
            request.setAttribute("popupMessage", msg);
            session.removeAttribute("popupMessage"); // 再表示防止
        }

        // JSPへ渡す共通データ
        request.setAttribute("books", books);
        request.setAttribute("keyword", keyword);

        // 貸出画面へフォワード
        request.getRequestDispatcher("/WEB-INF/jsp/rental.jsp")
               .forward(request, response);
    }

    /**
     * 貸出処理用（POST）
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // セッション取得
        HttpSession session = request.getSession();

        // ログインユーザー取得
        User loginUser = (User) session.getAttribute("loginUser");

        // ログイン済み かつ action=rent の場合のみ貸出処理
        if (loginUser != null &&
            "rent".equals(request.getParameter("action"))) {

            RentalLendDao dao = new RentalLendDao();

            // 貸出処理実行
            boolean result = dao.lendBook(
                loginUser.getId(),
                loginUser.getName(),
                request.getParameter("bookname")
            );

            // 結果メッセージをセッションへ保存（PRG）
            session.setAttribute(
                "popupMessage",
                result ? "貸出が完了しました" : "これ以上借りられません"
            );
        }

        // リダイレクト（PRGパターン）
        response.sendRedirect(request.getContextPath() + "/Rental_servlet");
    }
}