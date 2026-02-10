<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.User" %>
<%@ page import="model.Lend" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="<%= request.getContextPath() %>/CSS/common.css">
 <link rel="stylesheet" href="<%= request.getContextPath() %>/CSS/table.css"> 
 <link rel="stylesheet" href="<%= request.getContextPath() %>/CSS/form.css">
<title>図書管理システム</title>
</head>
<body>

<!-- 
<a href = "index.jsp">
<div class="hover-img"></div>
</a>
-->

<a href ="index.jsp"><img src ="images/library.png"></a>

<div class="container">


<%
User loginUser = null;
if (session != null) {
    Object obj = session.getAttribute("loginUser");
    if (obj instanceof User) {
        loginUser = (User) obj;
    }
}
%>

<% if (loginUser != null) { %>
    <p class="login ok">
        ログイン中：<%= loginUser.getName() %> さん
    </p>
<% } else { %>
    <p class="login ng">
        ログインしていません
    </p>
<% } %>

<hr>

<div class="menu">
	<table>
		<tr>
		<th>
   			 <% if (loginUser == null) { %>
        		<form action="newAcount_servlet" method="get">
            		<input type="submit" value="新規登録">
        		</form>
    		<% } else { %>
        		<form action="MyLibrary_servlet" method="get">
           			 <input type="submit" value="Myライブラリ">
       			 </form>
   			 <% } %>
    	</th>
    	</tr>
    	<tr>
		<th>
   		 	<% if (loginUser == null) { %>
        		<form action="Login_servlet" method="get">
            		<input type="submit" value="ログイン">
        		</form>
    		<% } else { %>
        		<form action="Logout_servlet" method="get">
            		<input type="submit" value="ログアウト">
        		</form>
    		<% } %>
    	</th>
		</tr>
		<tr>
		<th>
    		<form action="Rental_servlet" method="get">
        		<input type="submit" value="本 検索">
    		</form>
		</th>
		</tr>
	</table>
</div>
</div>
<hr>
</body>
</html>