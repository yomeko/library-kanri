<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="CSS/index.css">
<title>本登録</title>
</head>
<body>
<h1>本の登録！！</h1>
	<form action="admin_book_servlet" method="post">
	<table>
    <tr>
        <th>本のタイトル :</th>
        <th><input type="text" name="bookName"></th>
    </tr>
	<tr>
        <th>数量 : </th>
        <th><input type="number" name="number" min="1"></th>
    </tr>
    </table>
	<input type="submit" value="新規登録">
<a href = "administrator.jsp">戻る</a>
<!-- 条件　本の名前　本の数　を出すこと　memberのコードをまねてでもいいので作って -->
</body>
</html>