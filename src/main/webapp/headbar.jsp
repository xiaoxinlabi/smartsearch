<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title></title>

        <script src="assets/js/jquery.min.js" type="text/javascript"></script>

        <link href="css/headbar.css" rel="stylesheet" type="text/css" />
        <script src="js/common.js" type="text/javascript"></script>

        <style>
            a {
                text-decoration: underline;
                cursor: pointer;
            }
        </style>

        <script>
            $(document).ready(function(){

                var backurl = window.parent.location.href;

                $("a.index").on("click",function(){
                    window.parent.location="index.html";
                })

                $("a.login").on("click",function(){
                    window.parent.location="simple-login.html?backurl=" + backurl;
                })

                $("a.logout").on("click",function(){
                    window.parent.location="rest/user/logout?backurl=" + backurl;
                })

                $("a.sysmgr").on("click",function(){
                    window.parent.location="admin.html";
                })

                $("a.about").on("click",function(){
                    alert("谢谢。");
                })

            });

        </script>

    </head>
    <body>

    <div class="pa-headbar">

        <span class="pa-headbar-left">
            <a class="index">首页</a>
        </span>

        <span class="pa-headbar-right">

            <shiro:user>

                欢迎 <shiro:principal/>,

                <shiro:hasPermission name="admin:visit">
                    <a class="sysmgr"> 系统管理</a>
                </shiro:hasPermission>

                <shiro:hasPermission name="develop:visit">
                    <a class="about"> 关于</a>
                </shiro:hasPermission>

                <a class="logout"> 退出</a>

            </shiro:user>

            <shiro:guest>
                您未登录，请<a class="login">登录</a>后搜索更多结果。
            </shiro:guest>

        </span>

    </div>

</html>