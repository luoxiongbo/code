<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2023-09-22
  Time: 10:47:20
  To change this template use File | Settings | File Templates.
--%>
<%--是用于在 JSP 页面中引入 Spring Framework 的表单标签库的指令。--%>
<%--引入这个表单标签库后，我们就可以在 JSP 页面中使用 Spring 的表单标签--%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<h3>添加妖怪~~</h3>
<!-- 这里的表单，我们使用 springMVC 的标签来完成
特别说明几点:
    1. SpringMVC 表单标签在显示之前必须在 request 中有一个 bean,
    该 bean 的属性和表单标签的字段要对应!
    request 中的 key 为: form 标签的 modelAttribute 属性值， 比如这里的 monsters
    2. SpringMVC 的 form:form 标签的 action 属性值中的 / 不代表 WEB 应用的根目录. -->

<%--注意这里是springmvc的标签--%>
<%--注意这里的save 作为 url--%>
<form:form action="save" method="POST" modelAttribute="monster">
    <%--  这里的 <form:errors 是springmvc底层自带的, 不用从request里面setAttribute() --%>
    妖怪名字: <form:input path="name"/> <form:errors path="name"/><br><br>
    妖怪年龄~: <form:input path="age"/> <form:errors path="age"/><br><br>
    妖怪生日: <form:input path="birthday"/> <form:errors path="birthday"/> 要求
    以"9999-11-11"的形式<br><br>
    妖 怪 工 资 : <form:input path="salary"/> <form:errors path="salary"/> 要 求 以
    "123,890.12"的形式<br><br>
    电子邮件: <form:input path="email"/><br><br>
    <input type="submit" value="添加妖怪"/>
</form:form>
</body>
</html>
