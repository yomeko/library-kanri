<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="<%= request.getContextPath() %>/CSS/common.css">
<title>ログアウト</title>
</head>
<body>

<h2><%= request.getAttribute("message") %></h2>

<a href="<%= request.getContextPath() %>/Login_servlet">
    ログイン画面へ
</a>

</body>
</html>