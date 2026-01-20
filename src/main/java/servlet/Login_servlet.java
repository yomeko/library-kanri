package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/Login_servlet")
public class Login_servlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    	RequestDispatcher dispatcher =
                request.getRequestDispatcher("/WEB-INF/jsp/Login.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String name = request.getParameter("name");
        String pass = request.getParameter("pass");

        model.User user = new model.User(name, pass);
        dao.UserDao dao = new dao.UserDao();

        boolean isLogin = dao.login(user);

        if (isLogin) {
            // ★ここが最重要
            HttpSession session = request.getSession();
            session.setAttribute("loginUser", user.getName());

            System.out.println("login user = " + user.getName());
            System.out.println("session id = " + request.getSession().getId());
            
            // レンタル画面へ
            response.sendRedirect(request.getContextPath() + "/Rental_servlet");

        } else {
            request.setAttribute("error", "ユーザー名またはパスワードが違います");
            request.getRequestDispatcher("/WEB-INF/jsp/Login.jsp")
                   .forward(request, response);
        }
    }
}