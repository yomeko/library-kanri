<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログイン用</title>
</head>
<body>

<h1>ログイン</h1>

<% String error = (String) request.getAttribute("error"); %>
<% if (error != null) { %>
    <p style="color:red;"><%= error %></p>
<% } %>

<!-- Login.java に渡す -->
<form action="Login" method="post">
    ユーザ名：<input type="text" name="name" required><br><br>
    パスワード：<input type="password" name="pass" required><br><br>
    <input type="submit" value="ログイン">
</form>

<br>

<a href="index.jsp">戻る</a>

</body>
</html>