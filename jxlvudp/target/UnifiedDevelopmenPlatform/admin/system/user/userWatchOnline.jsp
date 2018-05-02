<%--
  Created by IntelliJ IDEA.
  User: wph-pc
  Date: 2017/7/24
  Time: 15:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/header/init.jsp"%>
<html>
<head>
    <title>用户在线监控</title>
</head>
<body>
<table class="easyui-datagrid" title="用户在线动态管理" style="height:auto" id="dguser"
       data-options="rownumbers:true,pagination:true,collapsible:true,striped:true,fit:true,toolbar:'#tb'">
    <thead>
    <tr>
        <th data-options="field:'id',checkbox:true"></th>
        <th data-options="field:'loginUser',width:100,formatter:formatUserNumber">账号</th>
        <th data-options="field:'loginUser.nickName',width:200,formatter:formatUserName">昵称</th>
        <th data-options="field:'ipAddress',width:200">登录地址</th>
        <th data-options="field:'loginDate',width:100,formatter:dateFormat">登录日期</th>
        <th data-options="field:'sessionID',width:200">会话ID/token_key</th>
        <th data-options="field:'type',width:60,formatter:formatType">设备类型</th>
        <th data-options="field:'status',width:80">状态</th>
        <th data-options="field:'ids',formatter:format" width="120"
            align="center">操作</th>
    </tr>
    </thead>
</table>
<div id="tb" style="padding:2px 5px;">
    <input class="easyui-searchbox" id="txtName" data-options="prompt:'请输用户名称',searcher:doSearch" style="width:300px">
</div>
<script src="<%=basePath%>/script/business/system/userWatchOnline.js"></script>
</body>
</html>
