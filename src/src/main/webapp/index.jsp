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

	<table>
	<tr>
		<th>
			<form action="newAcount_servlet" method="get">
				<input type="submit" value="新規登録">
			</form>
		</th>
		<th>
			<form action="Login_servlet" method="get">
				<input type="submit" value="ログイン">
			</form>
		</th>
	</tr>
	<tr>
		<th>
			<form action="rental_servlet" method="get">
				<input type="submit" value="貸し出し">
			</form>
		</th>
		<th>
			<form action="Logout_servlet" method="get">
				<input type="submit" value="ログアウト">
			</form>
		</th>
	</tr>
	</table>

</table>

	<form action="deleteAcount_servlet" method="get" class="delete-btn">
    	<input type="submit" value="アカウント削除">
	</form>
</body>
</html>