<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>アカウント削除</title>
</head>
<body>

<h2>アカウント削除</h2>

<p>本当にアカウントを削除しますか？</p>

<form action="deleteAcount_servlet" method="post">
    <label>ユーザー名</label><br>
    <input type="text" name="username" required><br>

    <label>パスワード</label><br>
    <input type="password" name="password" required><br><br>

    <button type="submit">アカウントを削除</button>
</form>

<c:if test="${not empty error}">
    <p style="color:red">${error}</p>
</c:if>

<a href="index.jsp">戻る</a>

</body>
</html>