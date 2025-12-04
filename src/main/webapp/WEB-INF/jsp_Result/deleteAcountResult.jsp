<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
Boolean isDeleted = (Boolean) request.getAttribute("isDeleted");
String name = (String) request.getAttribute("name");
String error = (String) request.getAttribute("error");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>図書館管理システム</title>
<link rel="stylesheet" href="CSS/index.css">
</head>
<body>
<h1>アカウント削除結果</h1>
<% if (isDeleted != null && isDeleted) { %>
	<p style="color:green;">アカウント削除に成功しました</p>
	<% if (name != null) { %>
		<p>ユーザー「<%= name %>」のアカウントを削除しました。</p>
	<% } %>
	<a href="index.jsp">トップへ</a>
<% } else { %>
	<% if (error != null) { %>
		<p style="color:red;"><%= error %></p>
	<% } else { %>
		<p style="color:red;">アカウント削除に失敗しました</p>
	<% } %>
	<a href="deleteAcount">削除画面に戻る</a><br>
	<a href="index.jsp">トップへ</a>		
<% } %>
</body>
</html>