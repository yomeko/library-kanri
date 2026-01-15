<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="<%= request.getContextPath() %>/CSS/common.css">
<title>ログイン成功</title>
</head>
<body>

<h1>ログイン成功</h1>

<p>
ようこそ、
<%= ((model.User)request.getAttribute("user")).getName() %>
さん
</p>

<!-- <a href="<%= request.getContextPath() %>/index.jsp">TOPへ</a> -->
<form action="rental_servlet" mthod = "get"><input type ="submit" value ="貸し出し">

</body>
</html>