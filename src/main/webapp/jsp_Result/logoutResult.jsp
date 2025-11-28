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
</head>
<body>
<h1>ログアウト画面</h1>
<% if (loginUser != null){ %>
	<p>ログアウトに成功しました</p>
	<a href="index.jsp">トップへ</a>
<% } else {%>
	<p>ログアウトに失敗しました</p>
	<a href="index.jsp">トップへ</a>		
<% } %>

</body>
</html>