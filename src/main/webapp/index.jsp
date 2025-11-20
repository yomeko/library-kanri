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

<h1>図書管理システム！</h1>

<table>
    <tr>
        <td>
            <form action="Login" method="post">
            	<input type="submit" value="ログイン" name="Login">
            </form>
        </td>

        <td>
            <form action="Main" method="post">
            	<input type="submit" value="一覧" name="itiran">
            </form>
        </td>
    </tr>
    
    <tr>
        <td>
            <form action="newAcount" method="post">
            	<input type="submit" value="新規登録" name="newAcount">
            </form>
        </td>

        <td>
            <form action="deleteAcount" method="post">
            	<input type="submit" value="削除" name="deleteAcount">
            </form>
        </td>
    </tr>
</table>

</body>
</html>