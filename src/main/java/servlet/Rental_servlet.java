package servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import dao.ListDao;
import dao.RentalLendDao;
import model.Book;
import model.Lend;
import model.User;

@WebServlet("/Rental_servlet")
public class Rental_servlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");

        ListDao listDao = new ListDao();
        RentalLendDao rentalDao = new RentalLendDao();

        String keyword = request.getParameter("keyword");

        List<Book> books;

        if (keyword != null && !keyword.isEmpty()) {
            books = rentalDao.searchBooks(keyword);
        } else {
            books = listDao.findAll();
        }

        // ★ 追加：ログイン中のみ貸出状態を判定
        if (loginUser != null) {
            for (Book b : books) {
                boolean lent =
                    rentalDao.isAlreadyLent(loginUser.getId(), b.getBook());
                b.setAlreadyLent(lent);
            }

            int remain = 3 - rentalDao.countLend(loginUser.getId());
            request.setAttribute("remainLend", remain);

            // ★ 追加：貸出中一覧（既存仕様）
            List<Lend> lendList =
                rentalDao.findLendingBooksByUser(loginUser.getId());
            request.setAttribute("lendList", lendList);
        }

        // ★ 既存：Book に detail / alreadyLent が入った状態で渡す
        request.setAttribute("books", books);

        RequestDispatcher rd =
            request.getRequestDispatcher("/WEB-INF/jsp/rental.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User loginUser = (User) session.getAttribute("loginUser");

        String action = request.getParameter("action");
        String bookName = request.getParameter("bookname");

        RentalLendDao rentalDao = new RentalLendDao();

        String message = null;

        if (loginUser != null && "rent".equals(action)) {

            boolean result = rentalDao.lendBook(
                loginUser.getId(),
                loginUser.getName(),
                bookName
            );

            // ★ 既存仕様
            message = result ? "貸出が完了しました" : "貸出できません";
        }

        request.setAttribute("popupMessage", message);

        // ★ 再表示
        doGet(request, response);
    }
}