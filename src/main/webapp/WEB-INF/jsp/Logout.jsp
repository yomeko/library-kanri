<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="<%= request.getContextPath() %>/CSS/common.css">
<link rel="stylesheet" href="<%= request.getContextPath() %>/CSS/table.css">
<link rel="stylesheet" href="<%= request.getContextPath() %>/CSS/form.css">
<link rel="stylesheet" href="<%= request.getContextPath() %>/CSS/search_form.css">
<title>ログアウト</title>
</head>
<body>

<!-- 
<a href = "index.jsp">
<div class="hover-img"></div>
</a>
-->

<a href ="index.jsp"><img src ="images/library.png"></a>
<div class="container">

    <h1><%= request.getAttribute("message") %></h1>

<form action="Login_servlet" method="get">
        <input type="submit" value="ログイン画面へ">
    </form>

    <form action="index.jsp" method="get">
        <input type="submit" value="トップに戻る">
    </form>

</div>

</body>
</html>