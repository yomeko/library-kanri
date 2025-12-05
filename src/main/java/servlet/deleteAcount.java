package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/deleteAcount")
public class deleteAcount extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public deleteAcount() {
        super();
    }

    // 削除確認画面を表示
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher dispatcher =
                request.getRequestDispatcher("/WEB-INF/jsp/deleteAcount.jsp");
        dispatcher.forward(request, response);
    }

    // 削除フォームを受け取って結果画面へ
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String name = request.getParameter("name"); // 削除するユーザー名

        // 実際に削除するなら DAO をここで呼ぶ
        // UserDAO dao = new UserDAO();
        // dao.delete(name);

        request.setAttribute("name", name);

        RequestDispatcher dispatcher =
                request.getRequestDispatcher("/WEB-INF/jsp/deleteAcountResult.jsp");
        dispatcher.forward(request, response);
    }
}