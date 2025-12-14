package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 図書検索を担当するサーブレット
 * URL : /search
 *
 * 【役割】
 * ・GET  ：検索画面の初期表示
 * ・POST ：検索条件を受け取り、同じ画面に検索結果を表示
 */
@WebServlet("/search")
public class search extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * 初期表示（検索結果なし）
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // search.jsp を表示
        RequestDispatcher dispatcher =
                request.getRequestDispatcher("WEB-INF/jsp/search.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * 検索ボタン押下時の処理
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 文字化け防止
        request.setCharacterEncoding("UTF-8");

        // フォームから入力された書籍名を取得
        String book = request.getParameter("book");

        // 同じ search.jsp に結果を表示
        RequestDispatcher dispatcher =
                request.getRequestDispatcher("WEB-INF/jsp/search.jsp");
        dispatcher.forward(request, response);
    }
}