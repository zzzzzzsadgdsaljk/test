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
    <link href="<%= basePath %>/plugins/easyui/themes/bootstrap/easyui.css" rel="stylesheet" />
    <link href="<%= basePath %>/plugins/easyui/themes/icon.css" rel="stylesheet" />

    <link href="<%= basePath %>/css/project.css" rel="stylesheet" />
    <script src="<%= basePath %>/script/common/jquery-1.8.2.min.js"></script>
    <script src="<%= basePath %>/plugins/easyui/jquery.easyui.min.js"></script>

    <script src="<%= basePath %>/script/common/public.js"></script>
    <script src="<%= basePath %>/script/common/project.js"></script>
</head>
<body>

</body>
</html>
