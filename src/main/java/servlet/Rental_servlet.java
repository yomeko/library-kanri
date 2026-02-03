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
import model.Book;
import model.Lend;
import model.User;

@WebServlet("/Rental_servlet")
public class Rental_servlet extends HttpServlet {

    private final RentalLendDao dao = new RentalLendDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User loginUser =
            (session != null) ? (User) session.getAttribute("loginUser") : null;

        request.setAttribute("loginUser", loginUser);

        if (loginUser != null) {

            // 書籍検索
            String keyword = request.getParameter("keyword");
            List<Book> books =
                (keyword == null || keyword.isEmpty())
                    ? dao.getAllBooks()
                    : dao.searchBooks(keyword);

            request.setAttribute("books", books);

            // 残り貸出可能数
            int remainLend = 3 - dao.countLend(loginUser.getId());
            request.setAttribute("remainLend", remainLend);

            // 貸出中一覧
            List<Lend> lendList =
                dao.findLendingBooksByUser(loginUser.getId());
            request.setAttribute("lendList", lendList);
        }

        request.getRequestDispatcher("/WEB-INF/jsp/rental.jsp")
               .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        User loginUser =
            (session != null) ? (User) session.getAttribute("loginUser") : null;

        if (loginUser == null) {
            request.setAttribute("popupMessage", "ログインしてください");
            doGet(request, response);
            return;
        }

        String action   = request.getParameter("action");
        String bookName = request.getParameter("bookname");

        int userId   = loginUser.getId();
        String name  = loginUser.getName();

        if ("rent".equals(action)) {

            if (dao.isAlreadyLent(userId, bookName)) {
                request.setAttribute("popupMessage", "すでに借りています");

            } else if (dao.countLend(userId) >= 3) {
                request.setAttribute("popupMessage", "3冊以上は借りられません");

            } else if (dao.lendBook(userId, name, bookName)) {
                request.setAttribute("popupMessage", "貸出完了");

            } else {
                request.setAttribute("popupMessage", "在庫がありません");
            }

        } else if ("return".equals(action)) {

            boolean result = dao.returnBook(userId, bookName);
            request.setAttribute(
                "popupMessage",
                result ? "返却完了" : "返却できません（期限切れ or 未貸出）"
            );
        }

        doGet(request, response);
    }
}