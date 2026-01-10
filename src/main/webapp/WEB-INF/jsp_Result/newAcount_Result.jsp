<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="<%= request.getContextPath() %>/CSS/common.css">
<title>新規登録結果</title>
</head>
<body>

<%
Boolean isRegistered = (Boolean) request.getAttribute("isRegistered");
%>

<% if (isRegistered != null && isRegistered) { %>
    <h1>登録完了</h1>
    <p>アカウントの登録が完了しました。</p>
<% } else { %>
    <h1>登録失敗</h1>
    <p style="color:red;">そのユーザー名はすでに使われています。</p>
    <a href="newAcount_servlet">戻る</a>
<% } %>

<br>
<a href="index.jsp">TOPへ</a>

</body>
</html>