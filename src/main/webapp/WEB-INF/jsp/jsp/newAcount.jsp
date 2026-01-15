<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="<%= request.getContextPath() %>/CSS/common.css">
<title>新規登録</title>
</head>
<body>
<h1>新規登録</h1>

	<form action = "newAcount_servlet" method = "post">
	ユーザー名<input type = "text" name = "name"><br>
	パスワード<input type="password" name="pass"><br>
	<input type = "submit" value = "登録"><br>
	</form>

<a href = "index.jsp">TOPへ</a>
</body>
</html>