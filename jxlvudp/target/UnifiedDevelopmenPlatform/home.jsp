<%--
  Created by IntelliJ IDEA.
  User: wph-pc
  Date: 2017/6/11
  Time: 15:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/header/init_bootstrap.jsp"%>
<html>
<head>
    <title>首页</title>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>/css/bootstrap.min.css">
</head>
<body style="padding: 20px;">
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="jumbotron">
                <div class="page-header">
                    <h3>江西联微软件技术有限公司统一开发平台<small>Beta V1.0.0.0</small></h3>
                </div>
                <p>
                    江西联微软件技术有限公司统一开发平台集成了Spring MVC、Spring IOC、Mybatis、Shiro和Redis等
                    框架技术，采用了Maven进行jar包统一管理，实现了Shiro+Redis进行用户安全及权限管理，实现了用户
                    的单点登录功能，Web前端提供了EasyUI、LayUI和BootStrap框架，采用了Ajax数据交换技术......
                </p>
                <p><a class="btn btn-primary btn-lg" href="#" role="button">了解更多</a></p>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-6">
            <div class="thumbnail">
                <img src="<%=basePath%>/images/business.png" width="150" height="150" alt="...">
                <div class="caption">
                    <h3>专注业务</h3>
                    <h5>Focus On Business</h5>
                    <p>系统管理涉及的内容较多，特别是系统的权限与安全管理尤为重要，这往往需要花费开发者大量的时间，
                    统一开发平台已经将系统管理方面的功能全部开发完毕，包括角色管理、用户管理、角色授权、权限使用、组织机构管理、
                    ......等；开发者只要专注客户的业务系统。</p>
                    <p><a href="#" class="btn btn-primary" role="button">详情</a></p>
                </div>
            </div>
        </div>
        <div class="col-md-6">
            <div class="thumbnail">
                <img src="<%=basePath%>/images/code.png" width="150" height="150" alt="...">
                <div class="caption">
                    <h3>代码复用</h3>
                    <h5>Code Reuse</h5>
                    <p>平台提倡代码复用机制，在类的设计中，尽可能的考虑到代码的重复性问题，采用了大量的继承机制实现代码复用；在封装
                    代码的过程中充分考虑到功能个性需求，提供了大量开放的接口，可以继承，也可以重写或重载这些功能；让开发者用
                    极少的代码完成更多的功能，享受开发的快乐</p>
                    <p><a href="#" class="btn btn-primary" role="button">详情</a></p>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-6">
            <div class="thumbnail">
                <img src="<%=basePath%>/images/power.png" width="150" height="150" alt="...">
                <div class="caption">
                    <h3>权限管理</h3>
                    <h5>Power Management</h5>
                    <p>统一平台在解决系统功能权限管理时采用了Shiro框架，将系统中每个原子操作都通过授权的形式进行统一的管理
                    ；另外，平台还提供了数据权限开放接口。</p>
                    <p><a href="#" class="btn btn-primary" role="button">详细</a></p>
                </div>
            </div>
        </div>
        <div class="col-md-6">
            <div class="thumbnail">
                <img src="<%=basePath%>/images/security.png" width="150" height="150" alt="...">
                <div class="caption">
                    <h3>安全管理</h3>
                    <h5>Security Management</h5>
                    <p>在安全管理方面，平台采用了Redis+Shiro的方式实现，实现了单点登录、用户在线监控、密码采用了Shiro盐值方式加密</p>
                    <p><a href="#" class="btn btn-primary" role="button">详细</a></p>
                </div>
            </div>
        </div>
    </div>
</div>
</body>

</html>
