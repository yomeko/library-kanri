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

import dao.LendDao;
import dao.RentalDao;
import model.Book;

@WebServlet("/Rental_servlet")
public class Rental_servlet extends HttpServlet {

    private RentalDao rentalDao = new RentalDao();
    private LendDao lendDao = new LendDao();

    /**
     * 一覧表示・検索
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String loginUser = (String) session.getAttribute("loginUser");

        request.setAttribute("loginUser", loginUser);

        // ログイン時のみ検索可能
        if (loginUser != null) {
            String keyword = request.getParameter("keyword");
            List<Book> books = (keyword == null || keyword.isEmpty())
                    ? rentalDao.getAllBooks()
                    : rentalDao.searchBooks(keyword);

            request.setAttribute("books", books);

            int remain = 3 - lendDao.countLend(loginUser);
            request.setAttribute("remainLend", remain);
        }

        RequestDispatcher rd =
            request.getRequestDispatcher("/WEB-INF/jsp/rental.jsp");
        rd.forward(request, response);
    }

    /**
     * 貸出・返却
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String loginUser = (String) session.getAttribute("loginUser");

        if (loginUser == null) {
            request.setAttribute("popupMessage", "ログインしてください");
            doGet(request, response);
            return;
        }

        String action = request.getParameter("action");
        String bookname = request.getParameter("bookname");

        if ("rent".equals(action)) {

            if (lendDao.isAlreadyLent(loginUser, bookname)) {
                request.setAttribute("popupMessage", "すでに借りています");
            } else if (lendDao.countLend(loginUser) >= 3) {
                request.setAttribute("popupMessage", "3冊以上は借りられません");
            } else {
                lendDao.lendBook(loginUser, bookname);
                request.setAttribute("popupMessage", "貸出完了");
            }

        } else if ("return".equals(action)) {
            lendDao.returnBook(loginUser, bookname);
            request.setAttribute("popupMessage", "返却完了");
        }

        doGet(request, response);
    }
}