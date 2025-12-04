<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.User"%>
<%
//セッションスコープからユーザー情報を取得
User loginUser = (User)session.getAttribute("loginUser");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>図書館管理システム</title>
<link rel="stylesheet" href="CSS/index.css">
</head>
<body>
<h1>新規登録結果</h1>
<% 
String error = (String) request.getAttribute("error");
if (loginUser != null){ 
%>
	<p style="color:green;">新規登録に成功しました</p>
	<a href="index.jsp">トップへ</a>
<% } else { %>
	<% if (error != null) { %>
		<p style="color:red;"><%= error %></p>
	<% } else { %>
		<p style="color:red;">新規登録に失敗しました</p>
	<% } %>
	<a href="newAcount">新規登録画面に戻る</a><br>
	<a href="index.jsp">トップへ</a>		
<% } %>

</body>
</html>