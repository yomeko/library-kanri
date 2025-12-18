<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="model.User" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>図書館管理システム</title>
<link rel="stylesheet" href="CSS/index.css">
</head>

<body>

<h1>図書館管理システム</h1>

<!-- ログインユーザー表示 -->
<p>
ログイン中：
<%
    User user = (User) session.getAttribute("loginUser");
    if (user != null) {
        out.print(user.getName()); // 名前だけ表示
    } else {
        out.print("未ログイン");
    }
%>
</p>

<div class="menu-container">

	<!-- ログイン画面(Login.jsp)に移動 -->
    <form action="Login" method="get">
        <button type="submit">ログイン</button>
    </form>
    
	<!-- 本の一覧画面(search.jsp)に移動 -->
    <form action="search" method="get">
        <button type="submit">一覧</button>
    </form>

	<!-- 新規登録画面(newAcount.jsp)に移動 -->
    <form action="newAcount" method="get">
        <button type="submit">新規登録</button>
    </form>

	<!-- ログアウト画面(Logout.jsp)に移動 -->
>>>>>>> 0a9f040dd9b0d7b9d1783c9c149aec4d05f1fb35
    <form action="Logout" method="post">
        <button type="submit">ログアウト</button>
    </form>

</div>

<form action="deleteAcount" method="get" class="delete-floating">
    <button type="submit">アカウント削除</button>
</form>

</body>
</html>