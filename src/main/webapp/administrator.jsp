<%@ page language="java" contentType="text/html; charset=UTF-8"

    pageEncoding="UTF-8"%>
    <%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>管理画面</title>
<link rel="stylesheet" href="CSS/admin.css">
</head>
<body>

<h1>図書館管理者画面</h1>

<div class="menu-container">

	<form action="admin_book_servlet" method="get">
		<button type="submit">本登録</button>
	</form>

    <form action="admin_menber_servlet" method="get">
        <button type="submit">会員情報判定</button>
    </form>

    <form action="admin_book_lend_servlet" method="get">
        <button type="submit">本の貸出判定</button>
    </form>
</div>
</body>
</html>