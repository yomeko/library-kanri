<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="<%= request.getContextPath() %>/CSS/common.css">
<title>図書管理システム</title>
</head>
<body>

<h1>図書管理システム</h1>

<h2>アカウントを削除できました</h2>

<p>
    ご利用ありがとうございました。<br>
    アカウント情報は正常に削除されました。
</p>

<form action="index.jsp" method="get">
    <input type="submit" value="トップページへ戻る">
</form>

</body>
</html>