<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.User" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="<%= request.getContextPath() %>/CSS/common.css">
<title>図書管理システム</title>
</head>
<body>

<h1>図書管理システム</h1>

<!-- ログイン状態表示 -->
<%
    User loginUser = null;

    if (session != null) {
        Object obj = session.getAttribute("loginUser");
        if (obj instanceof User) {
            loginUser = (User) obj;
        }
    }
%>

<% if (loginUser != null) { %>
    <p style="color: green; font-weight: bold;">
        ログイン中：<%= loginUser.getName() %> さん
    </p>
<% } else { %>
    <p style="color: red; font-weight: bold;">
        ログインしていません
    </p>
<% } %>

<table>
<tr>
    <th>
	<% if (loginUser == null) { %>
    	<form action="newAcount_servlet" method="get">
        	<input type="submit" value="新規登録">
    	</form>
	<% }else{ %>
		<p>新規登録できないよ！</p>
	<% } %>
    </th>
    <th>
        <form action="Login_servlet" method="get">
            <input type="submit" value="ログイン">
        </form>
    </th>
</tr>
<tr>
    <th>
        <form action="Rental_servlet" method="get">
            <input type="submit" value="レンタル">
        </form>
    </th>
    <th>
        <form action="Logout_servlet" method="get">
            <input type="submit" value="ログアウト">
        </form>
    </th>
</tr>
</table>

<%-- 未ログイン時のみ表示 --%>
<% if (loginUser == null) { %>
    <form action="deleteAcount_servlet" method="get" class="delete-btn">
        <input type="submit" value="アカウント削除">
    </form>
<% } else{%>
		
<%} %>
</body>
</html>