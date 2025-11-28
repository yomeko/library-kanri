<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>アカウント削除</title>
<link rel="stylesheet" href="CSS/index.css">
</head>
<body>
	
	<h1>アカウント削除</h1>
	
	<form action="deleteAcount" method="post">
	ユーザー名 : <input type="text" name="name"><br>
	パスワード : <input type="password" name="pass"><br>
	<input type="submit" value="削除">
	<a href = "index.jsp">戻る</a>
</body>
</html>