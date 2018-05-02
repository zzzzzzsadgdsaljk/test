<%--
  Created by IntelliJ IDEA.
  User: wph-pc
  Date: 2017/8/1
  Time: 14:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<html>
<head>
    <title>江西联微软件技术有限公司统一开发平台</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport" content="width=device-width; initial-scale=1.0">
    <link href="<%= basePath %>/css/bootstrap.min.css" rel="stylesheet" />
    <script src="<%= basePath %>/script/common/jquery-1.10.2.min.js"></script>
    <script src="<%= basePath %>/plugins/easyui/jquery.easyui.min.js"></script>
    <script src="<%= basePath %>/plugins/bootstrap/bootstrap.min.js"></script>
    <script src="<%= basePath %>/script/common/public.js"></script>
    <script src="<%= basePath %>/script/common/project.js"></script>
</head>
<body>

</body>
</html>
