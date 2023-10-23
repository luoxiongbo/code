<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2023-09-22
  Time: 16:46:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>json 提交</title>
    <!-- 引入 jquery -->
    <%--  由于中文过滤器, 调试了半天 , 一个参数将该文件进行了过滤 --%>
    <script type="text/javascript" src="./script/jquery-3.6.0.min.js"></script>
    <!-- 编写 jquery 代码和请求 -->
    <script type="text/javascript">
        $(function () {
            // 这个可以运行
            //绑定超链接点击事件
            $("#getJson").click(function () {
                //href 是一个完整的请求地址
                var href = this.href;
                alert(href);
                var args = {"time": new Date()};//防止缓存, 这是对象的创建
                //发出一个 jquery 的 post 请求，请求返回 json
                $.post(href, args, function (data) {
                    alert(" name= " + data[0].name + " address= " + data[0].address);
                    alert(" name= " + data[1].name + " address= " + data[1].address);
                    //1. data 是 json 对象
                    //2. JSON.stringify(data) 是将 json 对象转成字符串
                    alert("返回数据 json=" + data[0] + ", " + data[1]);
                    alert("返回数据 json=" + JSON.stringify(data))
                }, "json");
                // 防止重复提交
                return false;
            })

            // 还是运行不了
            //绑定按钮点击事件, 提交 json 数据
            //springmvc 可以在后台将 json 封装成对象
            $("button[name='butt1']").click(function () {
                console.log("ok")  // 测试用的
                var userName = $("#userName").val();// 取出名字
                var age = $("#age").val();// 取出年龄
                // Ajax请求
                $.ajax({
                    url: "/springmvc/save2",
                    data: JSON.stringify({"userName": userName, "age": age}),
                    type: "POST",
                    success: function (data) {
                        alert("返回的信息=" + JSON.stringify(data));
                    },
                    contentType: "application/json;charset=utf-8"
                });
            })
        })
    </script>
</head>
<body>

<h1>请求一个 json 数据</h1>
<a href="getJson" id="getJson">点击获取 json 数据</a>

<h1>发出一个 json 数据</h1>
u:<input id="userName" type="text"><br/>
a:<input id="age" type="text"><br/>
<button name="butt1">添加用户</button>

<h1>下载文件的测试 </h1>
<a href="downFile">点击下载文件</a>
</body>
</html>
