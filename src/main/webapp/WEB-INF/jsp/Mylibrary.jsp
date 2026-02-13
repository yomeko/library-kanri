<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="model.Lend" %>
<%@ page import="model.User" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<!-- 共通CSS・テーブル用CSS・フォーム用CSS -->
<link rel="stylesheet" href="<%= request.getContextPath() %>/CSS/common.css">
<link rel="stylesheet" href="<%= request.getContextPath() %>/CSS/table.css">
<link rel="stylesheet" href="<%= request.getContextPath() %>/CSS/form.css">

<title>Myライブラリ</title>
</head>
<body>

<!-- トップページへのリンク（ロゴ） -->
<a href ="index.jsp">
    <img src ="images/library.png">
</a>

<div class="container">
    <h1>Myライブラリ</h1>

    <%
    // ===== ログインユーザー取得 =====
    // セッションが存在し、かつ User 型である場合のみログイン扱い
    User loginUser = null;
    if (session != null) {
        Object obj = session.getAttribute("loginUser");
        if (obj instanceof User) {
            loginUser = (User) obj;
        }
    }

    // ===== Servlet から渡された貸出中書籍一覧 =====
    // MyLibrary_servlet で request.setAttribute("lendList", ...) されたもの
    List<Lend> lendList =
        (List<Lend>) request.getAttribute("lendList");
    %>

    <%-- ===== ログイン済みの場合 ===== --%>
    <% if (loginUser != null) { %>

        <!-- ログインユーザー名表示 -->
        <p class="login ok">
            ログイン中：<%= loginUser.getName() %> さん
        </p>

        <h2>現在借りている本</h2>

        <%-- 貸出中の本が存在する場合 --%>
        <% if (lendList != null && !lendList.isEmpty()) { %>

        <table class="rental-table">
            <tr>
                <th>書籍名</th>
                <th>貸出日</th>
                <th>返却期限</th>
            </tr>

            <%-- 貸出中書籍を1件ずつ表示 --%>
            <% for (Lend lend : lendList) { %>
            <tr>
                <!-- 書籍名 -->
                <td><%= lend.getBookname() %></td>

                <!-- 貸出日 -->
                <td><%= lend.getLendDate() %></td>

                <!-- 返却期限（貸出日＋14日） -->
                <td>
                    <%= new java.sql.Date(
                        lend.getLendDate().getTime()
                        + 14L * 24 * 60 * 60 * 1000
                    ) %>
                </td>
            </tr>
            <% } %>
        </table>

        <%-- 貸出中書籍がない場合 --%>
        <% } else { %>
            <p>現在借りている本はありません。</p>
        <% } %>

        <!-- 業務ルール補足 -->
        <p>※レンタルは上限3冊までです。</p>

    <%-- ===== 未ログインの場合 ===== --%>
    <% } else { %>
        <p class="login ng">ログインが必要です。</p>
    <% } %>

    <hr>

    <!-- トップページへ戻る -->
    <form action="index.jsp" method="get">
        <input type="submit" value="トップに戻る">
    </form>

    <!-- 貸出・検索画面へ -->
    <form action="Rental_servlet" method="get">
        <input type="submit" value="貸出・検索">
    </form>

</div>

</body>
</html>