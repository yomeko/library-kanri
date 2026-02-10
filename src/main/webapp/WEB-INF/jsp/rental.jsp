<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Book" %>
<%@ page import="model.Lend" %>
<%@ page import="model.User" %>

<%
/*
 * ログイン状態（確定仕様）
 */
User loginUser = (User) session.getAttribute("loginUser");

/*
 * Rental_servlet から渡される想定（未ログイン時は null 可）
 */
Integer remainLend = (Integer) request.getAttribute("remainLend");
String popupMessage = (String) request.getAttribute("popupMessage");
List<Book> books = (List<Book>) request.getAttribute("books");
List<Lend> lendList = (List<Lend>) request.getAttribute("lendList");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>レンタル画面</title>

<link rel="stylesheet" href="<%= request.getContextPath() %>/CSS/common.css">
<link rel="stylesheet" href="<%= request.getContextPath() %>/CSS/search_form.css">
<link rel="stylesheet" href="<%= request.getContextPath() %>/CSS/table.css">
<link rel="stylesheet" href="<%= request.getContextPath() %>/CSS/form.css">
</head>

<body>

<!-- 
<a href = "index.jsp">
<div class="hover-img"></div>
</a>
-->

<a href ="index.jsp"><img src ="images/library.png"></a>
<div class="container">
<h1>レンタル画面</h1>

    <form action="index.jsp" method="get">
        <input type="submit" value="トップに戻る">
    </form>

    <% if (loginUser != null) { %>
        <p class="login ok">
            ログイン中：<strong><%= loginUser.getName() %></strong>
        </p>
        <p>
            あと <strong><%= remainLend %></strong> 冊借りられます
        </p>
    <% } else { %>
        <p class="login ng">※ 書籍の貸出にはログインが必要です</p>
    <% } %>
    <!-- 検索フォーム -->
    <table>
        <tr>
            <th>
                <form action="Rental_servlet" method="get" class="search-form">
                    <input type="text" name="keyword" placeholder="書籍名で検索">
                    <button type="submit">検索</button>
                </form>
            </th>
            <th>
                <form action="Rental_servlet" method="get" class="search-form">
                    <button type="submit">一覧に戻る</button>
                </form>
            </th>
            <% if (loginUser != null) { %>
            <th>
                <form action="MyLibrary_servlet" method="get" class="search-form">
                    <button type="submit">Myライブラリ</button>
                </form>
            </th>
            <% } %>
            <% if (loginUser == null) { %>
            <th>
            	<form action="Login_servlet" method="get">
            		<input type="submit" value="ログイン">
        		</form>
            </th>
            <% } %>
        </tr>
    </table>

<br>
<br>
    <!-- 書籍一覧 -->
    <table class="rental-table">
        <tr>
            <th>書籍名</th>
            <th>在庫数</th>
            <th>詳細</th>
            <% if (loginUser != null) { %>
                <th>貸出</th>
            <% } %>
        </tr>

        <% if (books != null && !books.isEmpty()) {
               for (Book book : books) { %>
        <tr>
            <td class="book-title"><%= book.getBook() %></td>
            <td class="stock"><%= book.getNumber() %></td>
            <td class="detail"><%= book.getDetail() %></td>

            <% if (loginUser != null) { %>
            <td class="action">
                <form action="Rental_servlet" method="post">
                    <input type="hidden" name="action" value="rent">
                    <input type="hidden" name="bookname" value="<%= book.getBook() %>">
                    <button type="submit"
                        class="<%= book.isAlreadyLent() ? "lent" : "available" %>"
                        <%= book.isAlreadyLent()
                           || (remainLend != null && remainLend <= 0)
                           || book.getNumber() <= 0
                           ? "disabled" : "" %>>
                        <%= book.isAlreadyLent() ? "貸出中" : "貸出" %>
                    </button>
                </form>
            </td>
            <% } %>
        </tr>
        <%     }
           } else { %>
        <tr>
            <td colspan="<%= loginUser != null ? 4 : 3 %>">
                書籍がありません
            </td>
        </tr>
        <% } %>
    </table>

</div>

<!-- ポップアップ -->
<% if (popupMessage != null) { %>
<script>
    alert("<%= popupMessage.replace("\"", "\\\"") %>");
</script>
<% } %>

</body>
</html>