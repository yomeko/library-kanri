package admin;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class admin_book_servlet
 */
@WebServlet("/admin_book_servlet")
public class admin_book_servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public admin_book_servlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		RequestDispatcher dispatcher =
                request.getRequestDispatcher("WEB-INF/admin/admin_book.jsp");
        dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {

	    request.setCharacterEncoding("UTF-8");

	    // JSP の name と一致
	    String bookName = request.getParameter("bookName");
	    int number = Integer.parseInt(request.getParameter("number"));

	    // DB登録処理（例）
	    
	    adminDAO dao = new adminDAO();
	    dao.insert(bookName, number);
	    
	    // 完了画面へ遷移
	    request.setAttribute("message", "本を登録しました！");
	    request.getRequestDispatcher("WEB-INF/admin/admin_book_result.jsp").forward(request, response);
	}


}
