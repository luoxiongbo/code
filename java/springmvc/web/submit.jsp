<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2023-09-17
  Time: 15:38:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>提交</title>
</head>
<body>
<h3>电脑信息</h3>
<form action="computer/submit" method="post">
  品牌:<input name="brand" type="text"><br/>
  价格:<input name="price" type="text"><br/>
  数量:<input name="mount" type="text"><br/>
  <input type="submit" value="提交">
</form>
</body>
</html>
