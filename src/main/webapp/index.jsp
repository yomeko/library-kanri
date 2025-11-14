<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>メニュー</title>

<style>
    body {
        font-family: sans-serif;
        background-color: #f5f5f5;
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100vh;
    }

    table {
        border-collapse: collapse;
        background: white;
        padding: 20px;
        border-radius: 6px;
        box-shadow: 0 0 8px rgba(0,0,0,0.1);
    }

    td {
        padding: 10px 20px;
    }

    input[type="submit"] {
        padding: 10px 20px;
        font-size: 16px;
        cursor: pointer;
        border-radius: 4px;
        border: 1px solid #ddd;
        background-color: #fafafa;
        transition: 0.2s;
    }

    input[type="submit"]:hover {
        background-color: #eee;
    }
</style>

</head>
<body>
    <table>
    	<tr>
			<td><form action = "Login" method = "post"><input type="submit" value="ログイン" name="Login"></form></td>
			<td><form><input type="submit" value="一覧" name="itiran"></form></td>
		</tr>
		<tr>
			<td><form><input type="submit" value="新規登録" name="newAcount"></form></td>
			<td><form><input type="submit" value="削除" name="deleteAcount"></form></td>
		</tr>
    </table>
</body>
</html>