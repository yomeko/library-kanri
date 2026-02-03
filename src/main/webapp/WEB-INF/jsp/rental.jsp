<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Book" %>
<%@ page import="model.Lend" %>
<%@ page import="model.User" %>

<%
User loginUser = (User) request.getAttribute("loginUser");
Integer remainLend = (Integer) request.getAttribute("remainLend");
String popupMessage = (String) request.getAttribute("popupMessage");
List<Book> books = (List<Book>) request.getAttribute("books");
List<Lend> lendList = (List<Lend>) request.getAttribute("lendList");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="<%= request.getContextPath() %>/CSS/common.css">
<link rel="stylesheet" href="<%= request.getContextPath() %>/CSS/search_form.css">
<title>レンタル画面</title>
<style>
    table { border-collapse: collapse; width: 80%; margin-top: 20px; }
    th, td { padding: 8px; text-align: center; border: 1px solid #ccc; }
    .right-area { text-align: right; margin-bottom: 10px; }
</style>
</head>
<body>

<h1>レンタル画面</h1>

<div class="right-area">
    <a href="index.jsp">TOPへ</a>
</div>




    <!-- 検索フォーム -->
    <form action="Rental_servlet" method="get" class="search-form" style="display:inline;">
        <input type="text" name="keyword" placeholder="書籍名で検索">
        <button type="submit">検索</button>
    </form>

    <form action="Rental_servlet" method="get" class="search-form" style="display:inline;">
        <button type="submit">一覧に戻る</button>
    </form>

    <!-- 書籍一覧 -->
    <table>
        <tr>
            <th>書籍名</th>
            <th>在庫数</th>
            <th>貸出</th>
        </tr>

        <% if (books != null) {
               for (Book book : books) { %>
        <tr>
            <td><%= book.getBook() %></td>
            <td><%= book.getNumber() %></td>
            <td>
                <form action="Rental_servlet" method="post">
                    <input type="hidden" name="action" value="rent">
                    <input type="hidden" name="bookname" value="<%= book.getBook() %>">
                    <button type="submit"
                        <%= remainLend <= 0 || book.getNumber() <= 0 ? "disabled" : "" %>>
                        貸出
                    </button>
                </form>
            </td>

        </tr>
        <%     }
           } %>
    </table>

<!-- ポップアップ表示 -->
<% if (popupMessage != null) { %>
<script>
    alert("<%= popupMessage.replace("\"", "\\\"") %>");
</script>
<% } %>

</body>
</html>