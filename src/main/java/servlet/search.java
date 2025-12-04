package servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Mutter;
import model_Logic.GetMutterListLogic;

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
     * → ログインチェック
     * → ログイン済みなら一覧画面を表示
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ログインチェック
        String user = (String) request.getSession().getAttribute("loginUser");
        if (user == null) {
            response.sendRedirect("index.jsp");
            return;
        }
        
        // ログイン中ユーザ名を表示したい場合の設定
        request.setAttribute("loginUser", user);

        // 図書一覧を取得
        GetMutterListLogic getMutterListLogic = new GetMutterListLogic();
        List<Mutter> mutterList = getMutterListLogic.execute();
        request.setAttribute("list", mutterList);

        RequestDispatcher dispatcher =
                request.getRequestDispatcher("WEB-INF/jsp/search.jsp");
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

        // 図書一覧を取得
        GetMutterListLogic getMutterListLogic = new GetMutterListLogic();
        List<Mutter> mutterList = getMutterListLogic.execute();
        request.setAttribute("list", mutterList);

        RequestDispatcher dispatcher =
                request.getRequestDispatcher("WEB-INF/jsp/search.jsp");
        dispatcher.forward(request, response);
    }
}