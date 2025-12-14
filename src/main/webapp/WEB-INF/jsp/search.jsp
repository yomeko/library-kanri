<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.*" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>図書管理システム</title>
<link rel="stylesheet" href="CSS/search.css">
</head>
<body>

<!-- 左側 -->
<div class="left">
    <h1>検索</h1>

    <form action="search" method="post">
    <div class="form-group">
        <label>書籍名：</label><br>
        <input type="text" name="book">
    </div>

    <div class="form-group">
        <button type="submit">検索</button>
    </div>
</form>

    <br>
    <form action="Logout" method="get">
        <button type="submit">ログアウト画面へ遷移</button>
    </form>
    <a href="index.jsp">戻る</a>
</div>

<!-- 右側 -->
<div class="right">
    <h1>DB一覧</h1>

    <table>
        <tr>
            <th>数量</th>
            <th>書籍名</th>
        </tr>

<%
    // ===============================
    // DB接続情報
    // ===============================
    String url  = "jdbc:mysql://localhost:3306/library-touroku?serverTimezone=JST";
    String user = "root";
    String pass = "";

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        conn = DriverManager.getConnection(url, user, pass);

        String sql = "SELECT book, number FROM list";
        ps = conn.prepareStatement(sql);
        rs = ps.executeQuery();

        while (rs.next()) {
            String book = rs.getString("book");
            int number = rs.getInt("number");
%>
        <tr>
            <td><%= number %></td>
            <td><%= book %></td>
        </tr>
<%
        }
    } catch (Exception e) {
%>
        <tr>
            <td colspan="2" style="color:red;">
                エラー：<%= e.getMessage() %>
            </td>
        </tr>
<%
    } finally {
        if (rs != null)   try { rs.close(); }   catch (Exception e) {}
        if (ps != null)   try { ps.close(); }   catch (Exception e) {}
        if (conn != null) try { conn.close(); } catch (Exception e) {}
    }
%>
    </table>
</div>

</body>
</html>