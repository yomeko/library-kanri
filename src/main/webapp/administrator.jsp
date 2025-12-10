<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" href="CSS/index.css">
    
    <meta charset="UTF-8">
    <title>管理画面</title>
</head>
<body>

<div class="menu-container">

	<form action="admin_book_servlet" method="get">
		<button type="submit">本登録</button>
	</form>

    <form action="admin_book_servlet" method="get">
        <button type="submit">会員情報判定</button>
    </form>

    <form action="admin_book_servlet" method="get">
        <button type="submit">本の貸出判定</button>
    </form>
</div>
</body>
</html>