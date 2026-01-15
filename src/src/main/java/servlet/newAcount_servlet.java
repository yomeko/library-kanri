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
import model_Logic.newAcount_Logic;

@WebServlet("/newAcount_servlet")
public class newAcount_servlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // GETはいじらない

    /**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	//新規登録画面に遷移
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 RequestDispatcher dispatcher =
	                request.getRequestDispatcher("/WEB-INF/jsp/newAcount.jsp");
	        dispatcher.forward(request, response);
		
	}
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String pass = request.getParameter("pass");

        User user = new User(name, pass);

        newAcount_Logic logic = new newAcount_Logic();
        boolean isRegistered = logic.execute(user);

        if (isRegistered) {
            HttpSession session = request.getSession();
            session.setAttribute("loginUser", user);
        }

        request.setAttribute("isRegistered", isRegistered);
        request.setAttribute("user", user);

        RequestDispatcher dispatcher =
                request.getRequestDispatcher("/WEB-INF/jsp_Result/newAcount_Result.jsp");
        dispatcher.forward(request, response);
    }
}