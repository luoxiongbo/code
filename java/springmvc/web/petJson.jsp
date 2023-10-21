<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>提交</title>
</head>
<body>
<h3>宠物提交</h3>

<form action="computer/pet" method="post">
<%--    主人号:<input type="text" name="id"><br>--%>
<%--    主人名:<input type="text" name="name"><br>--%>
    宠物号:<input type="text" name="pet.id"><br>
    宠物名:<input type="text" name="pet.name"><br>
    <input type="submit" value="添加宠物">
</form>
</body>
</html>