<%--
  Created by IntelliJ IDEA.
  User: Pauline
  Date: 16/11/3
  Time: 上午10:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<html>
<head>
    <meta charset="UTF-8">
    <title></title>
    <style>
        a {
            text-decoration: underline;
            cursor: pointer;
        }
        .pa-headbar-right {
            color: #9d9d9d;
            float: right;
            padding: 8px 1px;
            font-size: 18px;
            line-height: 20px
        }
        body{
            background-color:#222;
        }
    </style>
</head>
<body>
    <div>
        <span class="pa-headbar-right">
            <shiro:user>
                欢迎 <shiro:principal/>,
                <a class="logout"> 退出</a>
            </shiro:user>
        </span>
    </div>
<script src="assets/js/jquery.min.js"></script>
<script>
    $(".logout").on("click", function () {
        window.parent.location = "rest/user/logout";
    })
</script>
</body>
</html>
