<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>図書館管理システム</title>
<link rel="stylesheet" href="CSS/index.css">
</head>

<body>

<h1>図書館管理システム</h1>

<div class="menu-container">

    <form action="Login" method="get">
        <button type="submit">ログイン</button>
    </form>

    <form action="newAcount" method="get">
        <button type="submit">新規登録</button>
    </form>

    <form action="deleteAcount" method="get">
        <button type="submit">削除</button>
    </form>

</div>

</body>
</html>
