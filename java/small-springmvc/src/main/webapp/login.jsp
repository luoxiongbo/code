<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2023-09-21
  Time: 21:36:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录</title>
</head>
<body>
<h1>妖怪登录</h1>
<form action="/monster/login" method="get">
    姓名 : <input type="text" name="name">
    <input type="submit" value="登录">
</form>
</body>
</html>
