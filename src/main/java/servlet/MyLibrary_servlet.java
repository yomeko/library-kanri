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

    private final RentalLendDao dao = new RentalLendDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        Object obj = session.getAttribute("loginUser");
        if (!(obj instanceof User)) {
            response.sendRedirect("index.jsp");
            return;
        }

        User loginUser = (User) obj;

        // 貸出中書籍一覧取得
        List<Lend> lendList = dao.findLendingBooksByUser(loginUser.getId());
        request.setAttribute("lendList", lendList);

        request.getRequestDispatcher("/WEB-INF/jsp/Mylibrary.jsp")
               .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}