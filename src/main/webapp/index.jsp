<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.User" %>
<%@ page import="model.Lend" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="<%= request.getContextPath() %>/CSS/common.css">
<title>図書管理システム</title>
</head>
<body>

<h1>図書管理システム</h1>

<%
User loginUser = null;
if (session != null) {
    Object obj = session.getAttribute("loginUser");
    if (obj instanceof User) {
        loginUser = (User) obj;
    }
}
%>

<!-- ログイン状態 -->
<% if (loginUser != null) { %>
    <p style="color: green; font-weight: bold;">
        ログイン中：<%= loginUser.getName() %> さん
    </p>
<% } else { %>
    <p style="color: red; font-weight: bold;">
        ログインしていません
    </p>
<% } %>

<hr>

<!-- メニュー -->
<table>
<tr>
    <th>
        <% if (loginUser == null) { %>
            <form action="newAcount_servlet" method="get">
                <input type="submit" value="新規登録">
            </form>
        <% } else { %>
            <form action="MyLibrary_servlet" method="get">
                <input type="submit" value="Myライブラリ">
            </form>
        <% } %>
    </th>

    <th>
        <% if (loginUser == null) { %>
            <form action="Login_servlet" method="get">
                <input type="submit" value="ログイン">
            </form>
        <% } else { %>
            <p>ログイン中のためログイン不可</p>
        <% } %>
    </th>
</tr>
<tr>
	<th>
	
            <form action="Rental_servlet" method="get">
                <input type="submit" value="貸出・検索">
            </form>
	</th>
    <th>
        <% if (loginUser != null) { %>
            <form action="Logout_servlet" method="get">
                <input type="submit" value="ログアウト">
            </form>
        <% } else { %>
            <p>ログアウト不可</p>
        <% } %>
    </th>
</tr>
</table>

</body>
</html>