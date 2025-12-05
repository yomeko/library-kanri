package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 図書検索メニューへ遷移するサーブレット
 * index.jsp → /search → 検索画面
 */
@WebServlet("/search")
public class search extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public search() {
        super();
    }

    /**
     * 直接 GET でアクセスされた場合の処理
     * 結果画面にフォワード（必要なら変更可）
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestDispatcher dispatcher =
                request.getRequestDispatcher("jsp_Result/searchResult.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * index.jsp の検索ボタン（POST）
     * → ログインチェック
     * → ログイン済なら検索画面へ
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ★ログインチェック
    	
        String user = (String) request.getSession().getAttribute("loginUser");
        if (user == null) {
            response.sendRedirect("index.jsp");
            return;
        }
		
        // ログイン中ユーザ名を表示したい場合の設定
        request.setAttribute("loginUser", user);

        RequestDispatcher dispatcher =
                request.getRequestDispatcher("WEB-INF/jsp/search.jsp");
        dispatcher.forward(request, response);
    }
}