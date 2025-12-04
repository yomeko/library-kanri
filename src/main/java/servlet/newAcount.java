package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;
import model_Logic.newAcountLogic;

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

    // 登録フォームを受け取り、結果画面へ
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        // ▼ フォーム値取得
        String name = request.getParameter("name");
        String pass = request.getParameter("pass");

        // ▼ User オブジェクト生成
        User user = new User(name, pass);

        // ▼ 新規登録ロジック実行
        newAcountLogic newacountLogic = new newAcountLogic();
        boolean isRegistered = newacountLogic.execute(user);

        // ▼ 登録成功ならセッションにユーザー情報を保存
        if (isRegistered) {
            HttpSession session = request.getSession();
            session.setAttribute("loginUser", user);
            
            System.out.println(user);
            System.out.println(name + pass);
         }

        // ▼ リクエストスコープに結果保存（JSPに渡す用）
        request.setAttribute("isRegistered", isRegistered);
        request.setAttribute("user", user);

        // ▼ 結果画面へフォワード
        RequestDispatcher dispatcher =
                request.getRequestDispatcher("/WEB-INF/jsp_Result/newAcountResult.jsp");
        dispatcher.forward(request, response);
    }
}
