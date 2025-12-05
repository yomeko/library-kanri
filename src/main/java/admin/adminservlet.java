package admin;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Mutter;

@WebServlet("/admin")
public class adminservlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public adminservlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // フォーム値取得
       
        String book = request.getParameter("book");
        String numberStr = request.getParameter("number");
        
        System.out.println(book);
        System.out.println(numberStr);
        // 未入力チェック
        if (
                 book == null || book.isEmpty()
                || numberStr == null || numberStr.isEmpty()) {

            request.setAttribute("error", "未入力の項目があります。すべて入力してください。");
            RequestDispatcher dispatcher =
                    request.getRequestDispatcher("WEB-INF/jsp_Result/administrator_result.jsp");
            dispatcher.forward(request, response);
            return;
        }

        // 数値変換
        //int id = Integer.parseInt(idStr);
        int number = Integer.parseInt(numberStr);
        Mutter aaa = new Mutter(number, book);
        // DAO実行
        adminDAO dao = new adminDAO();
        
        boolean success = dao.insert(aaa);

        // JSPへ結果を渡す
        HttpSession session = request.getSession();
        session.setAttribute("success", success);
        RequestDispatcher dispatcher =
                request.getRequestDispatcher("WEB-INF/jsp_Result/administrator_result.jsp");
        dispatcher.forward(request, response);
        
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher =
                request.getRequestDispatcher("/WEB-INF/jsp_Result/administrator.jsp");
        dispatcher.forward(request, response);
    }
}