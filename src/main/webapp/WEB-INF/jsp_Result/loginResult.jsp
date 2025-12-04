<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
//セッションスコープからユーザー名を取得（String型）
String loginUser = (String) session.getAttribute("loginUser");
String username = (String) request.getAttribute("username");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>図書館管理システム</title>
<link rel="stylesheet" href="CSS/index.css">
</head>
<body>
<h1>ログイン結果</h1>
<% if (loginUser != null){ %>
	<p style="color:green;">ログインに成功しました</p>
	<% if (username != null) { %>
		<p>ようこそ、<%= username %>さん</p>
	<% } %>
	<a href="search">図書一覧へ</a><br>
	<a href="index.jsp">トップへ</a>
<% } else {%>
	<p style="color:red;">ログインに失敗しました</p>
	<a href="Login">ログイン画面に戻る</a><br>
	<a href="index.jsp">トップへ</a>		
<% } %>
</body>
</html>