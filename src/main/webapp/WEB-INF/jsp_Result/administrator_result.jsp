<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.Mutter"%>
<%
//セッションスコープからユーザー情報を取得
Boolean success = (Boolean) session.getAttribute("success");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="css/admin.css">
<title>図書館管理システム</title>
</head>
<body>
<h1>管理画面</h1>
<% if (success != null){ %>
	<p>登録に成功しました</p>
	<a href="index.jsp">トップへ</a>
<% } else {%>
	<p>登録に失敗しました</p>
	<a href="index.jsp">トップへ</a>		
<% } %>
</body>
</html>