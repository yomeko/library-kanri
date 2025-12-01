package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/newAcount")
public class newAcount extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public newAcount() {
        super();
    }

    // 新規アカウント登録画面を表示
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher dispatcher =
                request.getRequestDispatcher("/WEB-INF/jsp/newAcount.jsp");
        dispatcher.forward(request, response);
    }

    // 登録フォームを受け取って結果画面へ
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String name = request.getParameter("name");
        String pass = request.getParameter("pass");

        // JSP に渡す
        request.setAttribute("name", name);
        request.setAttribute("pass", pass);

        RequestDispatcher dispatcher =
                request.getRequestDispatcher("/WEB-INF/jsp_Result/newAcountResult.jsp");
        dispatcher.forward(request, response);
    }
}