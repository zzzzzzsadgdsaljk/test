<%--
  Created by IntelliJ IDEA.
  User: wph-pc
  Date: 2017/10/4
  Time: 15:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="header/init_bootstrap.jsp"%>
<html>
<head>
    <title>江西联微软件技术有限公司统一开发平台</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">

</head>
<body>
    <nav class="navbar navbar-default navbar-fixed-top">
        <div class="container-fluid">
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="#">江西联微软件有限公司统一开发平台</a>
            </div>

            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav">
                    <li class="active"><a href="#">我的任务 <span class="sr-only">(current)</span></a></li>
                    <li><a href="#">消息</a></li>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">辅助系统 <span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="#">数据词典管理</a></li>
                            <li role="separator" class="divider"></li>
                            <li><a href="#">角色类型管理</a></li>
                            <li><a href="#">角色管理</a></li>
                            <li role="separator" class="divider"></li>
                            <li><a href="#">用户管理</a></li>
                            <li><a href="#">用户在线管理</a></li>
                            <li role="separator" class="divider"></li>
                            <li><a href="#">系统菜单管理</a></li>
                            <li role="separator" class="divider"></li>
                            <li><a href="#">组织机构管理</a></li>
                            <li><a href="#">员工管理</a></li>
                        </ul>
                    </li>
                </ul>
                <form class="navbar-form navbar-left">
                    <div class="form-group">
                        <input type="text" class="form-control" placeholder="Search">
                    </div>
                    <button type="submit" class="btn btn-default">Submit</button>
                </form>
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="#">超级用户：admin<span class="badge">14</span></a></li>
                    <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">个人中心 <span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li><a href="#">个人信息</a></li>
                            <li><a href="#">系统权限</a></li>
                            <li><a href="#">密码修改</a></li>
                            <li role="separator" class="divider"></li>
                            <li><a href="#">系统退出</a></li>
                        </ul>
                    </li>
                </ul>
            </div><!-- /.navbar-collapse -->
        </div><!-- /.container-fluid -->
    </nav>
</body>
</html>
