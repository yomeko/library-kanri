package servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.RentalDao;
import model.Book;

@WebServlet("/rental_servlet")
public class Rental_servlet extends HttpServlet {
    private RentalDao dao = new RentalDao();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    	System.out.println("rental session id = " + request.getSession().getId());
    	
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            //		System.out.println("MySQL Driver OK");
        } catch (ClassNotFoundException e) {
            throw new ServletException(e);
        }

        String keyword = request.getParameter("keyword");
        List<Book> books;

        if (keyword != null && !keyword.isEmpty()) {
            books = dao.searchBooks(keyword);   // ← 追加
        } else {
            books = dao.getAllBooks();
        }

        request.setAttribute("books", books);

        RequestDispatcher dispatcher =
                request.getRequestDispatcher("/WEB-INF/jsp/rental.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        int bookId = Integer.parseInt(request.getParameter("bookId"));

        boolean success = false;

        if ("rent".equals(action)) {
            success = dao.rentBook(bookId);
            request.setAttribute("message", success ? "貸出成功" : "貸出不可");
        } else if ("return".equals(action)) {
            success = dao.returnBook(bookId);
            request.setAttribute("message", success ? "返却完了" : "返却失敗");
        }

        doGet(request, response); // 一覧を再表示
    }
}