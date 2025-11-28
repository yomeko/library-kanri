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

<h1 style="text-align:center;">図書館管理システム</h1>

<table align="center" border="1" cellpadding="20">
    <tr>
        <!-- 1行目 左: ログイン -->
        <td align="center">
            <form action="Login" method="get">
                <button type="submit">ログイン</button>
            </form>
        </td>

        <!-- 1行目 右: 一覧（Mainサーブレット） -->
        <td align="center">
            <form action=" search" method="post">
                <button type="submit">一覧</button>
            </form>
        </td>
    </tr>

    <tr>
        <!-- 2行目 左: 新規登録 -->
        <td align="center">
            <form action="newAcount" method="post">
                <button type="submit">新規登録</button>
            </form>
        </td>

        <!-- 2行目 右: 削除 -->
        <td align="center">
            <form action="deleteAcount" method="post">
                <button type="submit">削除</button>
            </form>
        </td>
    </tr>
</table>

</body>
</html>