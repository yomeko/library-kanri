<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="<%= request.getContextPath() %>/CSS/common.css">
<title>ログイン</title>
</head>
<body>

<h1>ログイン</h1>
<!-- ログイン失敗などのエラーメッセージ -->
<%
String error = (String) request.getAttribute("error");
if (error != null) {
%>
    <p style="color:red;"><%= error %></p>
<%
}
%>

<form action="Login_servlet" method="post">
    ユーザー名
    <input type="text" name="name"><br>
    パスワード
    <input type="password" name="pass"><br>
    <input type="submit" value="ログイン">
</form>

<a href="index.jsp">TOPへ</a>

</body>
</html>