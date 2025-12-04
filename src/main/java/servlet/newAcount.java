package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.User;

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

        System.out.println(name + "_niino");
        System.out.println(pass + "_niino");
        
     // リクエストパラメータ取得
        request.setCharacterEncoding("UTF-8");

        // Userインスタンス生成
        User user = new User(name,pass);

        // 登録処理
        model_Logic.newAcountLogic newacountLogic = new model_Logic.newAcountLogic();
        boolean isRegistered = newacountLogic.execute(user);

        // 登録結果をリクエストスコープに保存
        request.setAttribute("isRegistered", isRegistered);
        request.setAttribute("user", user);
        
        // JSP に渡す
        request.setAttribute("name", name);
        request.setAttribute("pass", pass);

        RequestDispatcher dispatcher =
                request.getRequestDispatcher("/WEB-INF/jsp_Result/newAcountResult.jsp");
        dispatcher.forward(request, response);
    }
}