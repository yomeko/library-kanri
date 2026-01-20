package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import dao.LendDao;

@WebServlet("/Rental_servlet")
public class Rental_servlet extends HttpServlet {

    private LendDao lendDao = new LendDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String loginUser = (String) request.getSession().getAttribute("loginUser");

        if (loginUser != null) {
            int remain = lendDao.getRemainLend(loginUser);
            request.setAttribute("remainLend", remain);
        }

        RequestDispatcher rd =
            request.getRequestDispatcher("/WEB-INF/jsp/rental.jsp");
        rd.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String loginUser = (String) request.getSession().getAttribute("loginUser");
        int bookId = Integer.parseInt(request.getParameter("bookId"));

        String message;

        if (lendDao.getRemainLend(loginUser) <= 0) {
            message = "これ以上借りられません";
        } else if (lendDao.isAlreadyLent(loginUser, bookId)) {
            message = "すでに借りています";
        } else if (lendDao.lendBook(loginUser, bookId)) {
            message = "貸出が完了しました";
        } else {
            message = "貸出に失敗しました";
        }

        request.setAttribute("message", message);
        doGet(request, response);
    }
}