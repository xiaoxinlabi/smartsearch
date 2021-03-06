<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta http-equiv="Cache-Control" content="no-cache">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <!-- Bootstrap core CSS -->
    <link href="assets/css/bootstrap.css" rel="stylesheet">
    <link href="assets/css/dashboard.css" rel="stylesheet">
    <link href="css/admin.css" rel="stylesheet" >
    <link href="css/panel.css" rel="stylesheet" >
    <title>管理后台</title>
</head>

<body>

<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <!--<img src="custom/hsb/img/hsb4.png" style="margin-left: -15px" />-->
            <a class="navbar-brand" href="#">智能检索管理后台</a>
            <a class="navbar-brand" href="index.html" style="font-size: 15px;padding: 16px 13px;">返回首页</a>
        </div>
        <iframe src="admin-headbar.jsp" frameborder="0" scrolling="no" style="float:right;height: 50px;"></iframe>
    </div>
</div>

<div class="container-fluid">
    <div class="row">
        <div class="col-sm-3 col-md-2 sidebar">
            <ul class="nav nav-sidebar">
                <shiro:hasPermission name="file:manage">
                    <li id="ss-file" class="active"><a href="#">文件管理</a></li>
                </shiro:hasPermission>
                <shiro:hasPermission name="address:manage">
                    <li id="ss-address"><a href="#">通讯录管理</a></li>
                </shiro:hasPermission>
                <shiro:hasPermission name="website:manage">
                    <li id="ss-website"><a href="#">网址导航管理</a></li>
                </shiro:hasPermission>
                <shiro:hasPermission name="index:manage">
                    <li id="ss-index"><a href="#">索引管理</a></li>
                </shiro:hasPermission>
                <shiro:hasPermission name="cache:manage">
                    <li id="ss-cache"><a href="#">缓存管理</a></li>
                </shiro:hasPermission>
            </ul>
        </div>

        <shiro:hasPermission name="file:manage">
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main ss-panel" id="ss-file-panel">
            <!--<h1 class="page-header">文件上传</h1>-->
            <!--<h1>文件上传</h1>-->

            <ul id="fileTab" class="nav nav-tabs">
                <li class="active">
                    <a href="#uploadFile" data-toggle="tab">
                        文件上传
                    </a>
                </li>
                <li>
                    <a href="#manageFile" data-toggle="tab">
                        我上传的文件
                    </a>
                </li>
            </ul>
            <div id="fileTabContent" class="tab-content">
                <div class="tab-pane fade in active" id="uploadFile">

                    <form name="form" action="rest/file/upload" method="post" enctype="multipart/form-data"
                          class="navbar-form">
                        <div id="ss-file-upload">
                            <input type="file" name="file" class="form-control ss-file-upload-input" id="file" required="required">
                            <input type="submit" value="上传" class="form-control ss-file-upload-input" >
                            <input type="checkbox" name="group" value="public" class="ss-file-upload-input">共享
                        </div>
                    </form>

                </div>
                <div class="tab-pane fade" id="manageFile">

                    <div class="list-group" id="ss-result-list">



                    </div>

                    <div>
                        <ul id='ss-result-pagination'>
                        </ul>
                    </div>

                </div>
            </div>


        </div>
        </shiro:hasPermission>

        <shiro:hasPermission name="address:manage">
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main ss-panel" id="ss-address-panel"
             style="display: none;">
            <!--<h1 class="page-header">通讯录录入</h1>-->
            <!--<h1>通讯录录入</h1>-->

            <ul id="addressTab" class="nav nav-tabs">
                <li class="active">
                    <a href="#addAddress" data-toggle="tab">
                        通讯录新增
                    </a>
                </li>
                <li>
                    <a href="#uploadAddress" data-toggle="tab">
                        通讯录导入
                    </a>
                </li>
            </ul>
            <div id="addressTabContent" class="tab-content">
                <div class="tab-pane fade in active" id="addAddress">

                    <div class="panel p-none m-none panel-form" id="formSection">
                        <div class="panel-body-user">
                            <!-- 表格 -->
                            <form class="form-horizontal pt-none apply" id="form-address" enctype="multipart/form-data" action="rest/address/add"
                                  method="post">
                                <!--<div id="formAudit" class="p-none"></div>-->
                                <div id="formQuestion" class="form-question p-none">
                                    <div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12 m-none height"
                                         data-innertype="text">
                                        <label class="mt-none mb-xs p-none question-title">账号ID
                                            <span class="necessary text-danger">*</span>
                                        </label>
                                        <input class="form-control-user question-content sqt-input-normal" id="accountId" name="accountId"
                                               type="text">
                                        <!--required="required" type="text">-->

                                    </div>
                                    <div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12 m-none height"
                                         data-innertype="text">
                                        <label class="mt-none mb-xs p-none question-title">英文名</label>
                                        <input class="form-control-user question-content sqt-input-normal" name="englishName"
                                               type="text">
                                    </div>
                                    <div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12 m-none height"
                                         data-innertype="text">
                                        <label class="mt-none mb-xs p-none question-title">中文名</label>
                                        <input class="form-control-user question-content sqt-input-normal" name="chineseName"
                                               type="text">
                                    </div>
                                    <div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12 m-none height"
                                         data-innertype="phone">
                                        <label class="mt-none mb-xs p-none question-title">固定电话</label>
                                        <input class="form-control-user question-content sqt-input-normal number"
                                               name="fixedPhone"
                                               maxlength="20" minlength="20" type="text">
                                    </div>
                                    <div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12 m-none height"
                                         data-innertype="phone">
                                        <label class="mt-none mb-xs p-none question-title">手机号码</label>
                                        <input class="form-control-user question-content sqt-input-normal number"
                                               name="mobilePhone"
                                               maxlength="11" minlength="11" type="text">
                                    </div>
                                    <div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12 m-none height"
                                         data-innertype="phone">
                                        <label class="mt-none mb-xs p-none question-title">传真</label>
                                        <input class="form-control-user question-content sqt-input-normal number"
                                               name="fax"
                                               maxlength="20" minlength="20" type="text">
                                    </div>
                                    <div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12 m-none height"
                                         data-anstype="1" data-innertype="email">
                                        <label class="mt-none mb-xs p-none question-title">个人邮箱</label>
                                        <input class="form-control-user question-content sqt-input-normal email" name="email"
                                               email="true" autocomplete="off" type="email">
                                    </div>
                                    <div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12 m-none height"
                                         data-anstype="1" data-innertype="text">
                                        <label class="mt-none mb-xs p-none question-title">家庭地址</label>
                                        <input class="form-control-user question-content sqt-input-normal" name="address"
                                               type="text">
                                    </div>

                                    <div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12 m-none height"
                                         data-innertype="phone">
                                        <label class="mt-none mb-xs p-none question-title">qq</label>
                                        <input class="form-control-user question-content sqt-input-normal number" name="qq"
                                               maxlength="20" minlength="3" type="text">
                                    </div>

                                    <div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12 m-none height"
                                         data-innertype="text">
                                        <label class="mt-none mb-xs p-none question-title">机构</label>
                                        <input class="form-control-user question-content sqt-input-normal" name="organizetion"
                                               type="text">
                                    </div>

                                    <div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12 m-none height"
                                         data-innertype="text">
                                        <label class="mt-none mb-xs p-none question-title">部门</label>
                                        <input class="form-control-user question-content sqt-input-normal" name="department"
                                               type="text">
                                    </div>
                                    <div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12 m-none height"
                                         data-anstype="1" data-innertype="text">
                                        <label class="mt-none mb-xs p-none question-title">职位</label>
                                        <input class="form-control-user question-content sqt-input-normal" name="position"
                                               type="text">
                                    </div>
                                    <div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12 m-none"
                                         data-anstype="2" data-innertype="textArea">
                                        <label class="mt-none mb-xs p-none question-title">备注</label>
                                <textarea class="form-control-user question-content sqt-textarea-normal" cols="30"
                                          name="remark" rows="2"></textarea>
                                    </div>
                                </div>
                                <div class="inputs">
                                    <input type="button" value="提交" class="ss-submit" id="submit-address">
                                    <input type="reset" name="reset" value="重置" class="ss-submit">
                                </div>
                            </form>
                        </div>
                    </div>

                </div>
                <div class="tab-pane fade" id="uploadAddress">
                    <form name="form" action="rest/address/upload" method="post" enctype="multipart/form-data"
                          class="navbar-form">
                        <div id="ss-address-upload">
                            <input type="file" name="file" class="form-control ss-file-upload-input" required="required">
                            <input type="submit" value="上传" class="form-control ss-file-upload-input" >
                            &nbsp;&nbsp;<a href="resource/template/address.xlsx">下载模板</a>
                        </div>
                    </form>
                </div>
            </div>

        </div>
        </shiro:hasPermission>

        <shiro:hasPermission name="website:manage">
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main ss-panel" id="ss-website-panel"
             style="display: none">
            <!--<h1>网址导航添加</h1>-->

            <ul id="websiteTab" class="nav nav-tabs">
                <li class="active">
                    <a href="#addWebsite" data-toggle="tab">
                        网址导航添加
                    </a>
                </li>
            </ul>
            <div id="websiteTabContent" class="tab-content">
                <div class="tab-pane fade in active" id="addWebsite">

                    <div class="panel p-none m-none panel-form" id="formSection1">

                        <div class="panel-body-user">
                            <!-- 表格 -->
                            <form class="form-horizontal pt-none apply" id="form-website" enctype="multipart/form-data" action="rest/website/add"
                                  method="post">
                                <div class="p-none"></div>
                                <div class="form-question p-none">
                                    <div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12 m-none height"
                                         data-innertype="text">
                                        <label class="mt-none mb-xs p-none question-title">网站链接
                                            <span class="necessary text-danger">*</span>
                                        </label>
                                        <input class="form-control-user question-content sqt-input-normal" id="url" name="url"
                                               required="required" type="text">
                                    </div>
                                    <div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12 m-none height"
                                         data-innertype="text">
                                        <label class="mt-none mb-xs p-none question-title">标题</label>
                                        <input class="form-control-user question-content sqt-input-normal" name="title"
                                               type="text">
                                    </div>
                                    <div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12 m-none height"
                                         data-innertype="text">
                                        <label class="mt-none mb-xs p-none question-title">关键字</label>
                                        <input class="form-control-user question-content sqt-input-normal" name="keywords"
                                               type="text">
                                    </div>
                                    <div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12 m-none" data-anstype="2"
                                         data-innertype="textArea">
                                        <label class="mt-none mb-xs p-none question-title">描述</label>
                                <textarea class="form-control-user question-content sqt-textarea-normal" cols="30"
                                          name="description" rows="2"></textarea>
                                    </div>
                                    <div class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12 m-none"
                                         data-anstype="2" data-innertype="textArea">
                                        <label class="mt-none mb-xs p-none question-title">内容</label>
                                <textarea class="form-control-user question-content sqt-textarea-normal" cols="30"
                                          name="content" rows="2"></textarea>
                                    </div>
                                </div>
                                <div class="inputs">
                                    <input type="button" value="提交" class="ss-submit" id="submit-website">
                                    <input type="reset" name="reset" value="重置" class="ss-submit">
                                </div>
                            </form>
                        </div>
                    </div>

                </div>

            </div>

        </div>
        </shiro:hasPermission>

        <shiro:hasPermission name="index:manage">
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main ss-panel" id="ss-index-panel"
             style="display: none">
            <!--<h1>索引管理</h1>-->

            <div class="panel panel-default">
                        <div class="panel-heading">
                            <h3 class="panel-title">索引初始化</h3>
                        </div>
                        <div class="panel-body">
                            <p>（警告：这将删除所有相关类型索引）</p>
                            <p>
                            <div class="btn-group ss-index-init-list">
                                <button type="button" class="btn btn-default ss-index-init-btn" data-type="file">文件附件初始化</button>
                                <button type="button" class="btn btn-default ss-index-init-btn" data-type="address">通讯录初始化</button>
                                <button type="button" class="btn btn-default ss-index-init-btn" data-type="website">网址/页面初始化</button>
                                <button type="button" class="btn btn-default ss-index-init-btn" data-type="rdbms">内控合规初始化</button>
                            </div>
                            </p>
                        </div>
                    </div>

        </div>
        </shiro:hasPermission>

        <shiro:hasPermission name="cache:manage">
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main ss-panel" id="ss-cache-panel"
             style="display: none">
            <!--<h1>缓存管理</h1>-->
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">缓存清空</h3>
                </div>
                <div class="panel-body">
                    <p>（警告：这将影响首次下载/预览速度）</p>
                    <p>
                    <div class="btn-group ss-cache-clear-list">
                        <button type="button" class="btn btn-default ss-cache-clear-btn" data-type="ini">下载缓存清空</button>
                        <button type="button" class="btn btn-default ss-cache-clear-btn" data-type="pdf">预览缓存清空</button>
                    </div>
                    </p>
                </div>
            </div>

        </div>
        </shiro:hasPermission>

    </div>
</div>

<script src="assets/js/ie-emulation-modes-warning.js"></script>

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
<script src="assets/js/html5shiv.min.js"></script>
<script src="assets/js/respond.min.js"></script>
<![endif]-->
<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="assets/js/jquery.js"></script>
<script src="assets/js/bootstrap.js"></script>
<!--<script src="assets/js/modernizr.js"></script>-->
<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<script src="assets/js/ie10-viewport-bug-workaround.js"></script>
<script src="utils/js/bootstrap-paginator.min.js"></script>
<script src="js/common.js"></script>
<script src="js/admin.js"></script>
<script src="js/myfile.js"></script>
</body>
</html>