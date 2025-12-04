<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="CSS/admin.css">
    <meta charset="UTF-8">
    <title>管理画面</title>
</head>
<body>
    <% String error = (String) request.getAttribute("error"); %>
<% if (error != null) { %>
    <p style="color:red;"><%= error %></p>
<% } %>

<form action="admin" method="post">
    
    名前:<input type="text" name="book"><br>
    数量:<input type="number" name="number"><br>
    <input type="submit" value="登録">
</form>
</body>
</html>