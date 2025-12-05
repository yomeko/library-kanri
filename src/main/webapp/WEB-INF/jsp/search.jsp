<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>図書管理システム</title>

<!-- 外部CSSを読み込む -->
<link rel="stylesheet" href="CSS/search.css">
</head>
<body>

<!-- 左半分：検索フォーム -->
<div class="left">
    <h1>検索</h1>
	<p>ログイン中：${loginUser}</p>
    <!-- 検索フォーム（IDと数量で検索） -->
    <form action="search" method="post">
        
        <!-- ID入力欄 -->
        <div class="form-group">
            <label>ID：</label><br>
            <input type="text" name="id">
        </div>

        <!-- 数量入力欄 -->
        <div class="form-group">
            <label>数量：</label><br>
            <input type="text" name="number">
        </div>

        <!-- 検索ボタン -->
        <div class="form-group">
            <button type="submit">検索</button>
        </div>
    </form>
    <br>
    <from action = "Logout" method = "get">
    	<button type="submit">ログアウト画面へ遷移</button>
    </from>
</div>

<!-- 右半分：DB のデータ一覧を表示 -->
<div class="right">
    <h1>DB一覧</h1>

    <!-- DBのテーブルを表示する領域 -->
    <table>
        <tr>
            <th>ID</th>
            <th>数量</th>
            <th>書籍名</th>
        </tr>
        
        <%-- サーブレットでセットされた List<Mutter> を表示する処理。 --%>
        
        <%-- 
        
        List<Mutter> list = (List<Mutter>) request.getAttribute("list");
            for (Mutter m : list) {
        --%>
        
        <%-- 実際のループ例（コメント解除して使用） --%>
        
        <%--
        List<Mutter> list = (List<Mutter>) request.getAttribute("list");
        if(list != null){
            for (Mutter m : list) {
                <tr>
                    <td><%= m.getId() %></td>
                    <td><%= m.getNumber() %></td>
                    <td><%= m.getBook() %></td>
                </tr>
            }
        }
        --%>
    </table>

</div>

</body>
</html>