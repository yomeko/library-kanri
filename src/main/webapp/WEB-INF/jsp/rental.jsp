<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Book" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="<%= request.getContextPath() %>/CSS/common.css">
<title>レンタル画面</title>
</head>
<body>

<%
String loginUser = (String) session.getAttribute("loginUser");
%>

<p>
ログイン状態：
<%= (loginUser != null) ? loginUser : "未ログイン" %>
</p>

<h1>レンタル画面</h1>

<% if (loginUser != null) { %>
    <p>あと <strong><%= request.getAttribute("remainLend") %></strong> 冊借りられます</p>
<% } %>

<h2>書籍一覧</h2>

<form action="rental_servlet" method="get">
    <input type="text" name="keyword" placeholder="書籍名で検索">
    <button type="submit">検索</button>
</form>

<table border="1">
<tr>
    <th>書籍名</th>
    <th>在庫数</th>
    <% if (loginUser != null) { %>
        <th>貸出</th>
        <th>返却</th>
    <% } %>
</tr>

<%
List<Book> books = (List<Book>) request.getAttribute("books");
if (books != null) {
    for (Book book : books) {
%>
<tr>
    <td><%= book.getBook() %></td>
    <td><%= book.getNumber() %></td>

    <% if (loginUser != null) { %>
        <td>
            <form action="rental_servlet" method="post">
                <input type="hidden" name="action" value="lend">
                <input type="hidden" name="bookname" value="<%= book.getBook() %>">
                <button type="submit" <%= book.getNumber() <= 0 ? "disabled" : "" %>>
                    貸出
                </button>
            </form>
        </td>
        <td>
            <form action="rental_servlet" method="post">
                <input type="hidden" name="action" value="return">
                <input type="hidden" name="bookname" value="<%= book.getBook() %>">
                <button type="submit">返却</button>
            </form>
        </td>
    <% } %>
</tr>
<%
    }
}
%>
</table>

<% if (loginUser == null) { %>
    <h2>ログインしたら貸出・返却処理ができます</h2>
<% } %>

<%
String popupMessage = (String) request.getAttribute("popupMessage");
if (popupMessage != null) {
%>
<script>
    alert("<%= popupMessage %>");
</script>
<%
}
%>

</body>
</html>